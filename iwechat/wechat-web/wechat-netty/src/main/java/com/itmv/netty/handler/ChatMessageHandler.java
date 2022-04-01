package com.itmv.netty.handler;

import com.itmv.entity.netty.ChatMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

public class ChatMessageHandler extends SimpleChannelInboundHandler<ChatMessage> {

    private RabbitTemplate rabbitTemplate;

    public ChatMessageHandler(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatMessage message) throws Exception {
        System.out.println("聊天内容" + message);

        // 存消息，数据库要存一份，客户端本地要存一份

        // 转发给交换机
        rabbitTemplate.convertAndSend("ws exchange", "", message);



    }
}
