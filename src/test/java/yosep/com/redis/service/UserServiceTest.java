package yosep.com.redis.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import yosep.com.redis.data.code.UserRole;
import yosep.com.redis.data.entity.User;
import yosep.com.redis.data.repository.mysql.UserJpaRepository;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class UserServiceTest {
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private UserService userService;

    @BeforeEach
    public void 테스트_데이터_생성() {
        for(int i=0;i<5;i++) {
            User user = User.builder()
                    .userId("test" + i)
                    .password("test" + i)
                    .name("test" + i)
                    .role(UserRole.USER)
                    .build();

            userJpaRepository.save(user);
        }
    }

    @AfterEach
    public void 테스트_데이터_삭제() {
        for(int i=0;i<5;i++) {
            userJpaRepository.deleteByUserId("test" + i);
        }
    }

    @Test
    @DisplayName("")
    public void 유저_생성_성공_테스트() {

    }
}
