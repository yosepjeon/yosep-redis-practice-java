package yosep.com.redis.data.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yosep.com.redis.data.entity.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
    public User findByUserId(String userId);
}
