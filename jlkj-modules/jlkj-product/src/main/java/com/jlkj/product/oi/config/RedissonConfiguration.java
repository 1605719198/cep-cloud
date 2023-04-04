package com.jlkj.product.oi.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zyf
 * @Description
 * @create 2022-03-20 15:24
 */
@Configuration
@Slf4j
public class RedissonConfiguration {
    @Value("${jedis.ssh-host}")
    String host;

    @Value("${jedis.port}")
    String post;

    @Bean
    public RedissonClient redissonClient() {
        log.info("Create bean with redisson configuration.");
        Config config = new Config();
        config.useSingleServer().setAddress(host + ":" + post);
        return Redisson.create(config);
    }
}
