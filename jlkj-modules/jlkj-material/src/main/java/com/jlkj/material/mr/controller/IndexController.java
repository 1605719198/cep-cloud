package com.jlkj.material.mr.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @author zyf
 * @Description 集成测试
 * @create 2022-02-28 15:09
 */
@Tag(name = "测试接口1")
@RestController
@RequestMapping("/index")
@Slf4j
public class IndexController {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RedissonClient redissonClient;


}
