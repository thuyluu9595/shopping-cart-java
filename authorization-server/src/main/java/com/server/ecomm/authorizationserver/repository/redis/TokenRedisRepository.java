package com.server.ecomm.authorizationserver.repository.redis;

import com.server.ecomm.authorizationserver.entity.redis.TokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("TokenRedisRepository")
public interface TokenRedisRepository extends CrudRepository<TokenEntity,String> {
    Optional<TokenEntity> findByEmail(String email);
}
