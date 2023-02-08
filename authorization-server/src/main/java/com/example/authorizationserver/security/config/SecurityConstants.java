package com.example.authorizationserver.security.config;

import org.springframework.beans.factory.annotation.Value;

public class SecurityConstants {
    @Value("$security.jwt.header")
    public static String HEADER;

    @Value("$security.jwt.issuer")
    public static String ISSUER;

    @Value("$security.jwt.key")
    public static String KEY;

    private SecurityConstants(){}
}
