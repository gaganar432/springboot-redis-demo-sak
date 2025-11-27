package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {

        // Read environment variables
        String host = System.getenv("SPRING_DATA_REDIS_HOST");
        String portStr = System.getenv("SPRING_DATA_REDIS_PORT");

        // Fallback if environment variables not found (important!)
        if (host == null || host.isEmpty()) {
            host = "redis";   // docker service name
        }

        if (portStr == null || portStr.isEmpty()) {
            portStr = "6379";
        }

        int port = Integer.parseInt(portStr);

        // Configure standalone Redis
        RedisStandaloneConfiguration config =
                new RedisStandaloneConfiguration(host, port);

        return new LettuceConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // Key serializer (string)
        template.setKeySerializer(new StringRedisSerializer());

        // Value serializer (JSON)
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }
}
