package com.jlkj.material.mr.configs;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zyf
 * @Description
 * @create 2022-03-01 11:10
 */
@Configuration
public class RabbitmqConfiguration {

    public static final String DIRECT_EXCHANGE = "direct_exchange";
    public static final String DIRECT_QUEUE = "direct_queue";
    public static final String ROUTE_KEY = "routeKey";
    public static final String TOPIC_EXCHANGE = "topic_exchange";
    public static final String TOPIC_QUEUE = "topic_queue";
    public static final String FANOUT_EXCHANGE = "fanout_exchange";
    public static final String FANOUT_QUEUE = "fanout_queue";

    /**
     * direct队列
     * @return
     */
    @Bean("directQueue")
    public Queue queueDirect() {
        return new Queue(DIRECT_QUEUE);
    }

    @Bean("directExchange")
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    @Bean
    public Binding directQueueExchange(@Qualifier("directQueue") Queue queue,
                                    @Qualifier("directExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTE_KEY);
    }

    /**
     * topic队列
     * @return
     */
    @Bean("topicExchange")
    public Exchange topicExchange(){
        return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE).durable(true).build();
    }

    @Bean("topicQueue")
    public Queue itemQueue(){
        return QueueBuilder.durable(TOPIC_QUEUE).build();
    }

    /**
     * WARN:  topic.*  分段匹配，etc:topic.any  error:topic.any.any
     * WARN:  topic.#  全匹配，etc:topic.any[.any.any]
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding topicQueueExchange(@Qualifier("topicQueue") Queue queue,
                                     @Qualifier("topicExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("topic.#").noargs();
    }

    /**
     * fanout队列
     * @return
     */
    @Bean("fanoutQueue")
    public Queue queueWork() {
        return new Queue(FANOUT_QUEUE);
    }

    @Bean("fanoutExchange")
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Binding fanoutQueueExchange(@Qualifier("fanoutQueue") Queue queue,
                                    @Qualifier("fanoutExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }
}
