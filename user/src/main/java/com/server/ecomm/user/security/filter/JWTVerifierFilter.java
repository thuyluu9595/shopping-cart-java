package com.server.ecomm.user.security.filter;

import com.server.ecomm.user.ultil.SecurityConstants;
import com.server.ecomm.user.ultil.Ultilities;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class JWTVerifierFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith(SecurityConstants.PREFIX)){
            filterChain.doFilter(request, response);
            return;
        }

        logHeader(request);

        String email = request.getHeader("email");
        String authString = request.getHeader("authorities");
        if (email == null || authString == null){
            filterChain.doFilter(request, response);
            return;
        }

//        List<Map<String, String>> authorities = new ArrayList<>();
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();


        simpleGrantedAuthorities = Arrays.stream(authString.split(",")).distinct().filter(Ultilities::validString)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, simpleGrantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);

    }

    private void logHeader(HttpServletRequest request){
        log.info(String.format("Email: %s <-----> Authorities: %s", request.getHeader("email"), request.getHeader("authorities")));
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while(headerNames.hasMoreElements()){
//            String header = headerNames.nextElement();
//            log.info(String.format("Header: %s ----- Value: %s", header, request.getHeader(header)));
//        }
    }
}
