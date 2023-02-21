package com.example.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    @Autowired
    private CartRepository cartRepository;


//    public RedisService(RedisRepository redisRepository) {
//        this.redisRepository = redisRepository;
//    }
}
