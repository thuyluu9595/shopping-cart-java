package com.example.authorizationserver.security.filters;

import com.example.authorizationserver.entity.redis.TokenEntity;
import com.example.authorizationserver.model.ConnValidationResponse;
import com.example.authorizationserver.model.JWTAuthModel;
import com.example.authorizationserver.service.redis.TokensRedisService;
import com.example.authorizationserver.ultil.SecurityConstants;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@RequiredArgsConstructor
@Slf4j
public class JWTAuthFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    private final ObjectMapper mapper = new ObjectMapper();

    private final TokensRedisService tokensRedisService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            System.out.println("AttemptAuth ");
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
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setIssuer(SecurityConstants.ISSUER)
                .setExpiration(Date.from(LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.UTC)))
                .signWith(SignatureAlgorithm.HS256, SecurityConstants.KEY.getBytes())
                .compact();
        System.out.println(token);
        TokenEntity tokenEntity = TokenEntity.builder()
                .email(authResult.getName())
                .authToken(token)
                .createdBy("SYSTEM").createdOn(LocalDateTime.now())
                .modifiedBy("SYSTEM").modifiedOn(LocalDateTime.now())
                .build();

        tokensRedisService.save(tokenEntity);
        response.addHeader(SecurityConstants.HEADER, String.format("Bearer %s", token));
        response.addHeader("Expiration", String.valueOf(30*60));

        ConnValidationResponse responseModel = ConnValidationResponse.builder().status(HttpStatus.OK.name()).token(String.format("Bearer %s", token)).methodType(HttpMethod.GET.name()).isAuthenticated(true).build();
        response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().write(mapper.writeValueAsBytes(responseModel));
    }
}
