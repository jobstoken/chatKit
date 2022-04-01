package com.itmv.netty.handler;

import com.itmv.entity.netty.HeartMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class HeartMessageHandler extends SimpleChannelInboundHandler<HeartMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartMessage heartMessage) throws Exception {
        ctx.channel().writeAndFlush(new TextWebSocketFrame("heart"));
    }
}
