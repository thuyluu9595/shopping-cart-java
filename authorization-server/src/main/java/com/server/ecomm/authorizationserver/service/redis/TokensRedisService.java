package com.server.ecomm.authorizationserver.service.redis;

import com.server.ecomm.authorizationserver.entity.redis.TokenEntity;
import com.server.ecomm.authorizationserver.repository.redis.TokenRedisRepository;
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

    public Optional<TokenEntity> findById(String id) {
        return tokenRedisRepository.findById(id);
    }
}
