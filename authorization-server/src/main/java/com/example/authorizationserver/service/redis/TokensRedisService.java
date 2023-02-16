package com.example.authorizationserver.service.redis;

import com.example.authorizationserver.entity.redis.TokenEntity;
import com.example.authorizationserver.repository.redis.RedisRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokensRedisService {
    private final RedisRepository redisRepository;

    public TokensRedisService(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    public TokenEntity save(TokenEntity entity){
        return redisRepository.save(entity);
    }

//    public Optional<TokenEntity> findById(String id){
//        return redisRepository.findById(id);
//    }

    public Optional<TokenEntity> findByEmail(String email){
        return redisRepository.findByEmail(email);
    }
    public Iterable<TokenEntity> findAll(){
        return redisRepository.findAll();
    }
}
