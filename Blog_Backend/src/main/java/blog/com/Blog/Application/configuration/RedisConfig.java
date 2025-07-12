package blog.com.Blog.Application.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.util.Objects;

@Configuration
public class RedisConfig {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(Environment env) {
        // Create Redis config using env variables
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(Objects.requireNonNull(env.getProperty("REDIS_HOST")));
        redisConfig.setPort(Integer.parseInt(Objects.requireNonNull(env.getProperty("REDIS_PORT"))));
        redisConfig.setPassword(RedisPassword.of(env.getProperty("REDIS_PASSWORD")));

        // Enable SSL for Upstash
        JedisClientConfiguration clientConfig = JedisClientConfiguration.builder()
                .useSsl()
                .build();

        return new JedisConnectionFactory(redisConfig, clientConfig);
    }
}
