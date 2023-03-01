package com.server.ecomm.authorizationserver.ultil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityConstants {
    public static String HEADER;

    public static String ISSUER;

    public static String KEY;

    public static String PREFIX;

    @Value("${security.jwt.header}")
    public void setHEADER(String header){
        HEADER = header;
    }

    @Value("${security.jwt.issuer}")
    public void setISSUER(String issuer){
        ISSUER = issuer;
    }

    @Value("${security.jwt.key}")
    public void setKEY(String key){
        KEY = key;
    }

    @Value("${security.jwt.prefix}")
    public void setPREFIX(String prefix){
        PREFIX = prefix;
    }

    public SecurityConstants(){}
}
