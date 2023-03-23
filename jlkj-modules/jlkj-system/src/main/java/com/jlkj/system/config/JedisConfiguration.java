package com.jlkj.system.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * @author zyf
 * @Description
 * @create 2022-02-28 16:51
 */
@Configuration
@Slf4j
public class JedisConfiguration {

    @Bean
    public JedisPoolConfig jedisPoolConfig(@Value("${jedis.maxActive}") int maxActive,
                                           @Value("${jedis.maxIdle}") int maxIdle,
                                           @Value("${jedis.minIdle}") int minIdle,
                                           @Value("${jedis.maxWaitMillis}") long maxWaitMillis,
                                           @Value("${jedis.testOnBorrow}") boolean testOnBorrow) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxWait(Duration.ofMillis(maxWaitMillis));
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);

        return jedisPoolConfig;
    }

    @Bean
    public JedisPool jedisPool(@Value("${jedis.host}") String host,
                               @Value("${jedis.password}") String password,
                               @Value("${jedis.port}") int port,
                               @Value("${jedis.timeout}") int timeout, JedisPoolConfig jedisPoolConfig) {

        log.info("=====创建JedisPool连接池=====");
        if(!password.isEmpty()) {
            return new JedisPool(jedisPoolConfig, host, port, timeout, password);
        }

        return new JedisPool(jedisPoolConfig, host, port, timeout);
    }

    /*
    redis集群场景使用
     */
//    @Bean
//    public JedisCluster jedisCluster(@Value("${jedis.maxActive}") int maxActive,
//                                     @Value("${jedis.maxIdle}") int maxIdle,
//                                     @Value("${jedis.minIdle}") int minIdle,
//                                     @Value("${jedis.maxWaitMillis}") long maxWaitMillis,
//                                     @Value("${jedis.testOnBorrow}") boolean testOnBorrow) {
//        Set<HostAndPort> nodeSet = new HashSet<>();
//        nodeSet.add(new HostAndPort("192.168.105.218", 6379));
//        nodeSet.add(new HostAndPort("192.168.105.218", 6380));
//        GenericObjectPoolConfig<redis.clients.jedis.Connection> genericObjectPoolConfig = new GenericObjectPoolConfig<redis.clients.jedis.Connection>();
//        genericObjectPoolConfig.setMaxTotal(maxActive);
//        genericObjectPoolConfig.setMaxIdle(maxIdle);
//        genericObjectPoolConfig.setMinIdle(minIdle);
//        genericObjectPoolConfig.setMaxWaitMillis(maxWaitMillis);
//        genericObjectPoolConfig.setTestOnBorrow(testOnBorrow);
//        JedisCluster jedisCluster = new JedisCluster(nodeSet, 2000, genericObjectPoolConfig);
//
//        log.info("=====创建JedisCluster=====");
//
//        return jedisCluster;
//    }
}
