package com.example.authorizationserver.service.redis;

import com.example.authorizationserver.entity.redis.RedisEntity;
import com.example.authorizationserver.repository.redis.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokensRedisService {
    private final RedisRepository redisRepository;

    public TokensRedisService(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    public RedisEntity save(RedisEntity entity){
        return redisRepository.save(entity);
    }

    public Optional<RedisEntity> findById(String id){
        return redisRepository.findById(id);
    }

    public Iterable<RedisEntity> findAll(){
        return redisRepository.findAll();
    }
}
