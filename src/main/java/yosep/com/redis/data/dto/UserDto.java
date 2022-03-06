package yosep.com.redis.data.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import yosep.com.redis.data.code.UserRole;

@Getter
@Builder
@RedisHash("user")
public class UserDto {
    private String userId;
    private String password;
    private String name;
    private UserRole role;
}
