package com.server.ecomm.authorizationserver.security.filters;

import com.server.ecomm.authorizationserver.service.redis.TokensRedisService;
import com.server.ecomm.authorizationserver.ultils.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JWTVerifierFilter extends OncePerRequestFilter {

    private final TokensRedisService tokensRedisService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith(SecurityConstants.PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authToken = header.substring(7);
        Jws<Claims> claims = Jwts.parser().setSigningKey(SecurityConstants.KEY.getBytes()).requireIssuer(SecurityConstants.ISSUER).parseClaimsJws(authToken);
        String id = claims.getBody().getId();
        String email = claims.getBody().getSubject();

        if (tokensRedisService.findById(id).isEmpty()){
            filterChain.doFilter(request, response);
            return;
        }
        if (!tokensRedisService.findById(id).get().getAuthToken().equals(authToken)){
            filterChain.doFilter(request, response);
            return;
        }

        List<Map<String, String>> authorities = (List<Map<String, String>>) claims.getBody().get("authorities");
        List<GrantedAuthority> grantedAuthorities = authorities.stream().map(map -> new SimpleGrantedAuthority(map.get("authority"))).collect(Collectors.toList());
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        request.setAttribute("email", email);
        request.setAttribute("authorities", grantedAuthorities);
        request.setAttribute("token", authToken);

        filterChain.doFilter(request, response);
    }
}
