package com.example.authorizationserver.repository.redis;

import com.example.authorizationserver.entity.redis.TokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RedisRepository extends CrudRepository<TokenEntity,String> {
    Optional<TokenEntity> findByEmail(String email);
}
