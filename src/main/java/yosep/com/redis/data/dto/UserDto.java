package yosep.com.redis.data.dto;

import lombok.Data;
import yosep.com.redis.data.code.UserRole;

@Data
public class UserDto {
    private String userId;
    private String password;
    private String name;
    private UserRole role;
}
