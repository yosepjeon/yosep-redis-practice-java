package redis.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import yosep.com.redis.common.config.RedisRepositoryConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@Slf4j
@Import(RedisRepositoryConfig.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReactiveRedisPracticeTest.class)
public class ReactiveRedisPracticeTest {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    /*
    * 문자열 데이터 구조 처리
     */
    @Test
    public void opsValue() {
        ReactiveValueOperations<String, String> reactiveValueOps = reactiveRedisTemplate.opsForValue();
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        Set<String> cacheKeys = new HashSet<>();

        // async process
        log.info("Step-1");
        for(int i=0;i<5000;i++) {
            String key = "value_" + i;
            cacheKeys.add(key);
            valueOps.set(key, String.valueOf(i));
//            reactiveValueOps.set(key, String.valueOf(i));
        }
        List<String> list = valueOps.multiGet(cacheKeys);
        System.out.println(list.get(0));

        log.info("Step-2");
        Mono<List<String>> values = reactiveValueOps.multiGet(cacheKeys).doOnNext(e -> System.out.println(e));
//        System.out.println(values.toFuture());
        log.info("Step-3");
        StepVerifier.create(values)
                .expectNextMatches(x -> x.size() == 5000).verifyComplete();
        log.info("Step-4");

        System.out.println(values.and(e -> System.out.println(e)));

    }
}
