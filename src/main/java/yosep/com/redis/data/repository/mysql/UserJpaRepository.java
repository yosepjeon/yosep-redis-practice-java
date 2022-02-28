package yosep.com.redis.data.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import yosep.com.redis.data.entity.User;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    void deleteByUserId(String userId);
}
