package com.example.authorizationserver.security.filters;

import com.example.authorizationserver.model.JWTAuthModel;
import com.example.authorizationserver.security.config.SecurityConstants;
import com.example.authorizationserver.service.redis.TokensRedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
public class JWTAuthFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    private ObjectMapper mapper = new ObjectMapper();

    private final TokensRedisService tokensRedisService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            JWTAuthModel authModel = mapper.readValue(request.getInputStream(), JWTAuthModel.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(authModel.getEmail(), authModel.getPassword());
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
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
                                                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.UTC)))
                                                        .signWith(SignatureAlgorithm.ES256, SecurityConstants.KEY)
                                                                .compact();
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
