package yosep.com.redis;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import yosep.com.redis.service.UserService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class AppInitDataRunner implements ApplicationRunner {
    private final UserService userService;

    public AppInitDataRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }

    @PostConstruct
    public void initData() {
        userService.initTestData();
    }

    @PreDestroy
    public void deleteData() {
        userService.deleteTestData();
    }
}
