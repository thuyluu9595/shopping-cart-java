package com.example.authorizationserver.service.redis;

import com.example.authorizationserver.entity.redis.TokenEntity;
import com.example.authorizationserver.repository.redis.TokenRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokensRedisService {
    @Autowired
    private TokenRedisRepository tokenRedisRepository;

//    public TokensRedisService(RedisRepository redisRepository) {
//        this.redisRepository = redisRepository;
//    }

    public TokenEntity save(TokenEntity entity){
        return tokenRedisRepository.save(entity);
    }

//    public Optional<TokenEntity> findById(String id){
//        return redisRepository.findById(id);
//    }

    public Optional<TokenEntity> findByEmail(String email){
        return tokenRedisRepository.findByEmail(email);
    }
    public Iterable<TokenEntity> findAll(){
        return tokenRedisRepository.findAll();
    }
}
