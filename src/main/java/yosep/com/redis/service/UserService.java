package yosep.com.redis.service;

import org.springframework.stereotype.Service;
import yosep.com.redis.common.mapper.YosepMapper;
import yosep.com.redis.data.dto.UserDto;
import yosep.com.redis.data.entity.User;
import yosep.com.redis.data.repository.mysql.UserJpaRepository;

@Service
public class UserService {
    private final UserJpaRepository userJpaRepository;

    public UserService(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    public UserDto findUserById(long id) {
        User user = userJpaRepository.findById(id).orElseThrow();

        return YosepMapper.userMapper.toDto(user);
    }

    public UserDto findUserByUserId(String userId) {
        User user = userJpaRepository.findByUserId(userId);

        return YosepMapper.userMapper.toDto(user);
    }
}
