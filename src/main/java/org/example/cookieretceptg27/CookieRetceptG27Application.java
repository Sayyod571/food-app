package org.example.cookieretceptg27;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;


@SpringBootApplication
@RequiredArgsConstructor
public class CookieRetceptG27Application {

    public static void main(String[] args) {
        SpringApplication.run(CookieRetceptG27Application.class, args);
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory()
    {
        RedisStandaloneConfiguration localhost = new RedisStandaloneConfiguration( "redis-18402.c55.eu-central-1-1.ec2.cloud.redislabs.com", 18402 );
        localhost.setUsername("default");
        localhost.setPassword("id4weIPgfqUB8COBcPbz2SNdi6XFSfnO");
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

}
