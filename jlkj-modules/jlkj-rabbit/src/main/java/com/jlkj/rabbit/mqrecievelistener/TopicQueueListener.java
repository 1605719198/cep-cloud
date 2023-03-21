package com.jlkj.rabbit.mqrecievelistener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.jlkj.rabbit.configs.RabbitmqConfiguration.TOPIC_QUEUE;

/**
 * @author zyf
 * @Description 主题队列接收器
 * @create 2022-03-09 13:59
 */
@Component
@Slf4j
public class TopicQueueListener {

    @RabbitListener(queuesToDeclare = @Queue(TOPIC_QUEUE))
    public void receiveListener(String msg, Channel channel, Message message) {
        log.info("rabbitmq:topicmode:receive-messageA => {}", msg);
    }
}
