package yosep.com.redis.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yosep.com.redis.common.config.cachekey.UserCacheKey;
import yosep.com.redis.data.dto.UserDto;
import yosep.com.redis.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Cacheable(value = UserCacheKey.USER, key = "#userId", unless = "#result == null")
    @GetMapping("/{id}")
    public ResponseEntity findUserByUserId(@PathVariable String userId) {
        UserDto userDto = userService.findUserByUserId(userId);

        return ResponseEntity.ok(userDto);
    }
}
