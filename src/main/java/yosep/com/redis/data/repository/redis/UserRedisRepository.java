package yosep.com.redis.data.repository.redis;

import org.springframework.data.repository.CrudRepository;
import yosep.com.redis.data.entity.User;

public interface UserRedisRepository extends CrudRepository<User, Long> {
}
