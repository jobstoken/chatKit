package com.itmv.listener;


import com.google.gson.Gson;
import com.itmv.entity.netty.ChatMessage;
import com.itmv.entity.netty.OutLineMessage;
import com.itmv.netty.channel.ChannelGroup;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;


@Component
@RabbitListener(queues = "ws_queue_${netty.port}")
public class RabbitMQListener {

    @Autowired
    private StringRedisTemplate redisTemplate;


    @RabbitHandler
    public void wsMsg(OutLineMessage message) {
        // 查看当前设备号是否在本台服务器
        String deviceId = message.getId();
        Channel channel = ChannelGroup.getChannel(deviceId);
        if (channel != null) {
            TextWebSocketFrame text = new TextWebSocketFrame(new Gson().toJson(message));
            channel.writeAndFlush(text);
        }
    }

    @RabbitHandler
    public void wsChat(ChatMessage chatMessage) {

        // 根据tid到redis上查到deviceId
        String deviceId = redisTemplate.opsForValue().get(chatMessage.getTid().toString());

        // 根据deviceId去ChannelGroup查到channel
        Channel channel = ChannelGroup.getChannel(deviceId);
        if (channel != null) {

            TextWebSocketFrame frame = new TextWebSocketFrame(new Gson().toJson(chatMessage));
            channel.writeAndFlush(frame);

        }

    }








}
