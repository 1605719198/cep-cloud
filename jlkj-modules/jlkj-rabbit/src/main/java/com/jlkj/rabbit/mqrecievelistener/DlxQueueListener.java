package com.jlkj.rabbit.mqrecievelistener;

import com.jlkj.rabbit.dto.QueueExceptionDTO;
import com.jlkj.rabbit.service.impl.SysQueueDlxServiceImpl;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.jlkj.rabbit.configs.RabbitmqConfiguration.DLX_QUEUE;

/**
 * @author zyf
 * @Description
 * @create 2022-07-07 16:13
 * 死信队列监听器（备用）
 */
@Component
@Slf4j
public class DlxQueueListener {

    @Autowired
    SysQueueDlxServiceImpl sysQueueDlxService;

    @RabbitListener(queuesToDeclare = @Queue(DLX_QUEUE))
    public void queueExceptionListener(QueueExceptionDTO msg, Channel channel, Message message) {
        log.info("rabbitmq:{} => {}", DLX_QUEUE, msg);
        try {
            sysQueueDlxService.saveQueueException(msg.getQueueName(), msg.getMessage(), msg.getException());
        } catch (Exception e) {
            log.error("error:rabbitmq:{} => {}", DLX_QUEUE, msg);
        } finally {
            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {
                log.error("rabbitmq-ack-error:{} => {}", DLX_QUEUE, e.getMessage());
            }
        }
    }
}
