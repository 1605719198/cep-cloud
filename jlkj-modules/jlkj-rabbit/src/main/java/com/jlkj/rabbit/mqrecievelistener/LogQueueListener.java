package com.jlkj.rabbit.mqrecievelistener;

import com.jlkj.rabbit.entity.SysLogs;
import com.jlkj.rabbit.service.impl.SysLogsServiceImpl;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.jlkj.common.core.constant.RabbitConstant.*;

/**
 * @author zyf
 * 2022-04-11 11:24
 */
@Component
@Slf4j
public class LogQueueListener {

    @Autowired
    SysLogsServiceImpl sysLogsService;

    /**
     * 系统日志接收器
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(SYSLOG_QUEUE),
            exchange = @Exchange(SYSLOG_EXCHANGE),
            key = SYSLOG_ROUTE_KEY), errorHandler = "rabbitListenerErrorHandler")
    public void receiveRouteKeyListener(SysLogs sysLog, Channel channel, Message message){
        try {
            log.info("rabbitmq: do sys log => {}", sysLog.toString());
            SysLogs sysLogs = new SysLogs();
            sysLogs.setId(sysLog.getId());
            sysLogs.setCreateTime(sysLog.getCreateTime());
            sysLogs.setType(sysLog.getType());
            sysLogs.setCreateUserId(sysLog.getCreateUserId());
            sysLogs.setCreateUserName(sysLog.getCreateUserName());
            sysLogs.setContent(sysLog.getContent());
            sysLogs.setUri(sysLog.getUri());
            sysLogsService.save(sysLogs);
        } catch (Exception e) {
            log.error("rabbitmq: do sys log => {} \nrabbitmq: do sys log error => {}", sysLog.toString(), e.getMessage());
        } finally {
            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {
                log.error("rabbitmq: do sys log => {} \nrabbitmq: do sys log manual ack error => {}", sysLog.toString(), e.getMessage());
            }
        }
    }
}
