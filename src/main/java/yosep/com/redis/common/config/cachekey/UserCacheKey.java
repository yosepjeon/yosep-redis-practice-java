package yosep.com.redis.common.config.cachekey;

public class UserCacheKey {
    private UserCacheKey() {}

    public static final int DEFAULT_EXPIRE_SEC = 60;
    public static final String USER = "user";
    public static final int USER_EXPIRE_SEC = 180;
}
