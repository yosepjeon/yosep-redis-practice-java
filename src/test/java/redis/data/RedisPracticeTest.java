package redis.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisServer;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
//import yosep.com.redis.config.EmbeddedRedisConfig;
import yosep.com.redis.config.RedisRepositoryConfig;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.greaterThan;

@Import(RedisRepositoryConfig.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisPracticeTest.class)
public class RedisPracticeTest {
    private Logger log = LoggerFactory.getLogger(this.getClass());

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

    /*
    * 문자 데이터 구조 처리
     */
    @Test
    public void opsValue() {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        Collection<String> cacheKeys = new ArrayList<>();
        String cacheKey = "value_";

        for(int i=0;i<10;i++) {
            cacheKeys.add(cacheKey + i);
            valueOps.set(cacheKey + i, String.valueOf(i),60, TimeUnit.SECONDS);
        }

        List<String> values = valueOps.multiGet(cacheKeys);
        Assert.assertNotNull(values);
        Assert.assertEquals(10, values.size());
        log.info("##### opsValue #####");
        log.info("{}", values);
    }

    /*
    * List 데이터 구조 처리 - 순서 있음. value 중복 허용
     */
    @Test
    public void opsList() {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        String cacheKey = "valueList";

        for(int i=0;i<10;i++) {
            listOps.leftPush(cacheKey,String.valueOf(i));
        }

        Assert.assertSame(DataType.LIST, redisTemplate.type(cacheKey));
        Assert.assertSame(10L, listOps.size(cacheKey));
        log.info("##### opsList #####");
        log.info("{}", listOps.range(cacheKey,0,10));
        Assert.assertEquals("0", listOps.rightPop(cacheKey));
        Assert.assertEquals("9", listOps.leftPop(cacheKey));
        Assert.assertEquals(true, redisTemplate.delete(cacheKey));
        log.info("{}", listOps.range(cacheKey,0,10));
    }

    /*
    * Hash 데이터 구조 처리 - 순서 없음. key 중복허용 안함, value 중복 허용
     */
    @Test
    public void opsHash() {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        String cacheKey = "valueHash";

        for(int i = 0; i< 10;i++) {
            hashOps.put(cacheKey, "key_" + i,"value_" + i);
        }

        Assert.assertSame(DataType.HASH, redisTemplate.type(cacheKey));
        Assert.assertSame(10L, hashOps.size(cacheKey));
        log.info("##### opsHash #####");
        Set<String> hKeys = hashOps.keys(cacheKey);
        for(String hKey : hKeys) {
            log.info("{} / {}", hKey, hashOps.get(cacheKey, hKey));
        }

        Assert.assertEquals("value_5", hashOps.get(cacheKey, "key_5"));
        Assert.assertEquals(Optional.of(1L).get(), hashOps.delete(cacheKey, "key_5"));
        Assert.assertEquals(null, hashOps.get(cacheKey,"key_5"));
    }
}
