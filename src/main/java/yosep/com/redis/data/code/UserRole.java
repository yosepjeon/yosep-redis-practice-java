package yosep.com.redis.data.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    GUEST,
    USER,
    ADMIN,
    TEMP_USER
}
