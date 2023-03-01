package com.server.ecomm.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Autowired
    private RedisService redisService;
}
