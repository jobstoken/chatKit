package com.itmv.netty.handler;

import com.google.gson.Gson;
import com.itmv.entity.netty.ConnMessage;
import com.itmv.entity.netty.OutLineMessage;
import com.itmv.netty.channel.ChannelGroup;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.data.redis.core.StringRedisTemplate;

public class ConnMessageHandler extends SimpleChannelInboundHandler<ConnMessage> {

    private StringRedisTemplate redisTemplate;

    public ConnMessageHandler(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ConnMessage connMessage) throws Exception {
        System.out.println(connMessage.getId() +" "+connMessage.getType());
        ChannelGroup.addChannel(connMessage.getId(), ctx.channel());

        /**
         * 这里解决的是一个用户登陆了然后下线再在另一个机器上登陆，这个时候旧的机器上面不会被挤下线
         * 但是等旧的机器再次登陆，这个时候不走login直接进index来连接，这个时候直接把他挤下线
         */
        Integer uid = connMessage.getUid();
        String deviceId = redisTemplate.opsForValue().get(uid.toString());
        if (deviceId != null && !deviceId.equals(connMessage.getId())) {
            OutLineMessage message = new OutLineMessage();
            TextWebSocketFrame text = new TextWebSocketFrame(new Gson().toJson(message));
            ctx.channel().writeAndFlush(text);
        }

    }
}
