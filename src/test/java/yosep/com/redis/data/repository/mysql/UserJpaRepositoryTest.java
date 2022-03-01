package yosep.com.redis.data.repository.mysql;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import yosep.com.redis.data.code.UserRole;
import yosep.com.redis.data.entity.User;

import java.util.NoSuchElementException;

@DataJpaTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class UserJpaRepositoryTest {
    @Autowired
    private UserJpaRepository userJpaRepository;

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
    @DisplayName("[UserJpaRepository] 유저 한명 userId로 검색 성공")
    public void 유저_한명_검색_성공() {
        // Given
        String userId = "test1";

        // When
        User user = userJpaRepository.findByUserId(userId).orElseThrow();

        // Then
        Assertions.assertEquals(userId, user.getUserId());
    }

    @Test
    @DisplayName("[UserJpaRepository] 유저 한명 userId로 검색 실패(값이 없을 경우)")
    public void 유저_한명_검색_실패() {
        // Given
        String userId = "empty";

        // When & Then
        Assertions.assertThrows(NoSuchElementException.class, () -> userJpaRepository.findByUserId("empty").orElseThrow());
    }
}
