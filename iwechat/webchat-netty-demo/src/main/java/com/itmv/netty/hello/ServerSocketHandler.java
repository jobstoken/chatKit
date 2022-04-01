package com.itmv.netty.hello;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;


@ChannelHandler.Sharable
public class ServerSocketHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
        System.out.println("客户端: " + byteBuf.toString(Charset.defaultCharset()));

        Channel channel = ctx.channel();
        ByteBuf resp = ctx.alloc().buffer(1024);
        resp.writeBytes(byteBuf);
        channel.writeAndFlush(resp);
    }
}
