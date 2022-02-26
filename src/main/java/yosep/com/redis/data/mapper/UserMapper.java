package yosep.com.redis.data.mapper;

import org.mapstruct.Mapper;
import yosep.com.redis.data.dto.UserDto;
import yosep.com.redis.data.entity.User;

@Mapper
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
