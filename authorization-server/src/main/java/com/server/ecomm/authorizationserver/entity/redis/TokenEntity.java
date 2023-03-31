package com.server.ecomm.authorizationserver.entity.redis;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Data
@RedisHash(value= "Tokens", timeToLive = 86400)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenEntity {


    private String id;

    private String email;
    private String authToken;
    private String modifiedBy;
    private LocalDateTime modifiedOn;
    private String createdBy;
    private LocalDateTime createdOn;
}
