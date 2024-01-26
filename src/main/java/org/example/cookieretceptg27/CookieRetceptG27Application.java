package org.example.cookieretceptg27;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
@RequiredArgsConstructor
public class CookieRetceptG27Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CookieRetceptG27Application.class, args);
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory()
    {
        RedisStandaloneConfiguration localhost = new RedisStandaloneConfiguration( "localhost", 6379 );
        return new JedisConnectionFactory( localhost );
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate()
    {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory( jedisConnectionFactory() );
//        template.getConnectionFactory().getConnection().flushDb();
        return template;
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
