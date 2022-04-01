package com.itmv.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    @Value("${netty.port}")
    private Integer port;

    /**
     * 为每个websocket绑定一个队列
     * @return
     */
    @Bean
    public Queue queue() {
        return new Queue("ws_queue_"+port);
    }

    /**
     * 创建一个交换机
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("ws_exchange");
    }

    /**
     * 将队列绑定到交换机
     * @return
     */
    @Bean
    public Binding queueToExchange() {
        return BindingBuilder.bind(queue()).to(fanoutExchange());
    }

}
