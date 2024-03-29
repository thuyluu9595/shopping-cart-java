package com.server.ecomm.authorizationserver.security.filters;

import com.server.ecomm.authorizationserver.entity.redis.TokenEntity;
import com.server.ecomm.authorizationserver.model.ConnValidationResponse;
import com.server.ecomm.authorizationserver.model.JWTAuthModel;
import com.server.ecomm.authorizationserver.service.redis.TokensRedisService;
import com.server.ecomm.authorizationserver.ultils.SecurityConstants;
import com.server.ecomm.authorizationserver.ultils.Utilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JWTAuthFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    private final ObjectMapper mapper = new ObjectMapper();

    private final TokensRedisService tokensRedisService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            JWTAuthModel authModel = mapper.readValue(request.getInputStream(), JWTAuthModel.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(authModel.getEmail(), authModel.getPassword());
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            log.error("Error message:", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String id = Utilities.generateUuid();
        String token = Jwts.builder()
                .setId(id)
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setIssuer(SecurityConstants.ISSUER)
                .setExpiration(Date.from(LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.UTC)))
                .signWith(SignatureAlgorithm.HS256, SecurityConstants.KEY.getBytes())
                .compact();
        log.info(token);
        TokenEntity tokenEntity = TokenEntity.builder().id(id)
                .email(authResult.getName())
                .authToken(token)
                .createdBy("SYSTEM").createdOn(LocalDateTime.now())
                .modifiedBy("SYSTEM").modifiedOn(LocalDateTime.now())
                .build();

        tokensRedisService.save(tokenEntity);
//        System.out.println(tokensRedisService.findById(tokenEntity.getId()).get().getEmail());
        response.addHeader(SecurityConstants.HEADER, String.format("Bearer %s", token));
        response.addHeader("Expiration", String.valueOf(30*60));

        ConnValidationResponse responseModel = ConnValidationResponse.builder()
                .status(HttpStatus.OK.name())
                .token(String.format("Bearer %s", token))
                .methodType(HttpMethod.GET.name())
                .isAuthenticated(true)
                .email(authResult.getName())
                .authorities((List<GrantedAuthority>) authResult.getAuthorities())
                .build();
        response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().write(mapper.writeValueAsBytes(responseModel));
    }
}
