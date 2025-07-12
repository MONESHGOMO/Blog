package blog.com.Blog.Application.service.userService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class RedisTestRunner implements CommandLineRunner {

    @Value("${REDIS_URL}")
    private String redisUrl;

    @Override
    public void run(String... args) {
        try (Jedis jedis = new Jedis(redisUrl)) {
            jedis.set("foo", "bar");
            String value = jedis.get("foo");
            System.out.println("✅ Value from Redis: " + value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}