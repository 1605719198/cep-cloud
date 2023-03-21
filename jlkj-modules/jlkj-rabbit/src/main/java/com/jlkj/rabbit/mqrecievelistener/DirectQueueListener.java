package com.jlkj.rabbit.mqrecievelistener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.jlkj.rabbit.configs.RabbitmqConfiguration.*;

/**
 * @author zyf
 * @Description 发布订阅队列接收器
 * @create 2022-03-09 13:40
 */
@Component
@Slf4j
public class DirectQueueListener {

    /**
     * 接收队列中指定路由键的消息
     * @param msg
     * @param channel
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(DIRECT_QUEUE),
            exchange = @Exchange(DIRECT_EXCHANGE),
            key = ROUTE_KEY))
    public void receiveRouteKeyListener(String msg, Channel channel, Message message) {
        log.info("rabbitmq:directQueue:receive-messageA => {}", msg);
    }
}
