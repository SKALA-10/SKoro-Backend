package skala.skoro.domain.auth.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash(value = "accessToken", timeToLive = 3600)
public class AccessTokenEntry implements Serializable {

    @Id
    private String token;
    private String empNo;

}
