package blog.com.Blog.Application.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class RedisTestRunner implements CommandLineRunner {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        redisTemplate.opsForValue().set("test-key", "Hello Redis");
        String value = redisTemplate.opsForValue().get("test-key");
        System.out.println("Redis Test Key Value: " + value);
    }
}
