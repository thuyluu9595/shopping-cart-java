package com.example.cart;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "Cart")
public class Cart {
    @Id
    private String id;
    private String name;
}
