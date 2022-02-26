package yosep.com.redis.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yosep.com.redis.config.cachekey.UserCacheKey;

@RestController
@RequestMapping("/user")
public class UserController {
    @Cacheable(value = UserCacheKey.USER, key = "#id", unless = "#result == null")
    @GetMapping("/{id}")
    public ResponseEntity findUser(@PathVariable long id) {


        return ResponseEntity.ok().build();
    }

}
