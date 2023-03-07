package com.jlkj.material.mr.configs;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin2.reporter.Sender;
import zipkin2.reporter.amqp.RabbitMQSender;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * @author zyf
 * @Description
 * @create 2022-06-21 10:20
 */
@Configuration
public class ZipkinRabbitSenderConfiguration {
    @Bean(value = "zipkinSender")
    Sender sender(@Value("${spring.rabbitmq.host}") String rabbitmqHostUrl,
                  @Value("${spring.rabbitmq.port}") int rabbitmqPort,
                  @Value("${spring.rabbitmq.username}") String username,
                  @Value("${spring.rabbitmq.password}") String password,
                  @Value("${spring.rabbitmq.virtual-host}") String virtualhost,
                  @Value("${spring.zipkin.rabbitmq.addresses:empty}") String addresses,
                  @Value("${spring.zipkin.rabbitmq.queue:zipkin}") String zipkinQueue) throws KeyManagementException, NoSuchAlgorithmException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(rabbitmqHostUrl);
        connectionFactory.setPort(rabbitmqPort);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualhost);
        return RabbitMQSender.newBuilder().connectionFactory(connectionFactory).queue(zipkinQueue).addresses(addresses).build();
    }
}
