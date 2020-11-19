package redis.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisServer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
//import yosep.com.redis.config.EmbeddedRedisConfig;
import yosep.com.redis.config.RedisRepositoryConfig;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.greaterThan;

@Import(RedisRepositoryConfig.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisPracticeTest.class)
public class RedisPracticeTest {
    @Autowired
    private RedisTemplate<String ,String> redisTemplate;

    @Test
    public void commonCommand() {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        valueOps.set("key1", "key1Value");
        valueOps.set("key2", "key2Value");

        // Key 타입 조회.
        Assert.assertEquals(DataType.STRING, redisTemplate.type("key1"));

        // 존재하는 Key의 개수를 반환
        Assert.assertSame(2L,redisTemplate.countExistingKeys(Arrays.asList("key1","key2","key3")));

        // Key가 존재하는지 확인
        Assert.assertTrue(redisTemplate.hasKey("key1"));

        // Key 만료 날짜 세팅
//        Assert.assertTrue(redisTemplate.expireAt("key", Date.from()));

        // Key 만료 시간 세팅
        Assert.assertTrue(redisTemplate.expire("key1", 60, TimeUnit.SECONDS));

        // Key 만료 시간 조회
        Assert.assertThat(redisTemplate.getExpire("key1"), greaterThan(0L));

        // Key 만료 시간 해제
        Assert.assertTrue(redisTemplate.persist("key1"));

        // Key 만료시간이 세팅 안되어있는경우 -1 반환
        Assert.assertSame(-1L, redisTemplate.getExpire("key1"));

        // Key 삭제
        Assert.assertTrue(redisTemplate.delete("key1"));

        // Key 일괄 삭제
        Assert.assertThat(redisTemplate.delete(Arrays.asList("key1","key2","key3")), greaterThan(0L));
    }
}
