package com.example.dividendproject.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@RequiredArgsConstructor
@Configuration
public class CacheConfig { // redis cache config bean


    @Value("${spring.redis.host}")
    private final String host;

    @Value("${spring.redis.port}")
    private final Integer port;


    @Bean
    public RedisConnectionFactory redisConnectionFactory() { // redis connection 을 맺을 수 있는 Connection Factory

        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(host);
        config.setPort(port);
        return new LettuceConnectionFactory(config);
    }
}
