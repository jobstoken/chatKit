package com.itmv.netty.handler;

import com.google.gson.Gson;
import com.itmv.entity.netty.ChatMessage;
import com.itmv.entity.netty.ConnMessage;
import com.itmv.entity.netty.HeartMessage;
import com.itmv.entity.netty.NettyMessage;
import com.itmv.netty.channel.ChannelGroup;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;


@ChannelHandler.Sharable
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 消息类型：
     * 1、新的连接
     * 2、心跳
     * 3、聊天消息
     * 4、输入状态
     * 5、结束输入状态
     *
     * @param ctx
     * @param frame
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        String text = frame.text();
        Gson gson = new Gson();

        System.out.println(text);
        NettyMessage message = gson.fromJson(text, NettyMessage.class);

        if (message.getType() == 1) {
            message = gson.fromJson(text, ConnMessage.class);
        } else if (message.getType() == 2) {
            message = gson.fromJson(text, HeartMessage.class);
        } else if (message.getType() == 3) {
            message = gson.fromJson(text, ChatMessage.class);

        }

        ctx.fireChannelRead(message);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("新客户端链接");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端断开");
        ChannelGroup.removeChannel(ctx.channel());
    }
}
