package com.example.authorizationserver.repository.redis;

import com.example.authorizationserver.entity.redis.RedisEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends CrudRepository<RedisEntity,String> {
}
