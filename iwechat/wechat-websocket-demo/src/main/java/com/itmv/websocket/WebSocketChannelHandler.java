package com.itmv.websocket;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.EventExecutorGroup;

@ChannelHandler.Sharable
public class WebSocketChannelHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        String text = frame.text();
        System.out.println(text);
        if (text.equals("heart")){
            TextWebSocketFrame resp = new TextWebSocketFrame("heart");
            ctx.channel().writeAndFlush(resp);
            return;
        }

        TextWebSocketFrame resp = new TextWebSocketFrame("嗯！");
        ctx.channel().writeAndFlush(resp);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("新客户端连接");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端断开");
    }
}
