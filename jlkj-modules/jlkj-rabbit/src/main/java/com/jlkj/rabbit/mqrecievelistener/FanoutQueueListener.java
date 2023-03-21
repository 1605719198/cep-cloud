package com.jlkj.rabbit.mqrecievelistener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.jlkj.rabbit.configs.RabbitmqConfiguration.FANOUT_EXCHANGE;
import static com.jlkj.rabbit.configs.RabbitmqConfiguration.FANOUT_QUEUE;

/**
 * @author zyf
 * @Description
 * @create 2022-03-10 10:04
 */
@Component
@Slf4j
public class FanoutQueueListener {

    /**
     * FUNOUT广播模式接收队列消息
     * @param msg   消息内容
     * @param channel   信道
     * @param message   附加参数
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(value = FANOUT_EXCHANGE, type = ExchangeTypes.FANOUT)))
    public void receiveListener(String msg, Channel channel, Message message) {
        log.info("rabbitmq:fanoutQueue:receive-messageA => {}", msg);
    }
}
