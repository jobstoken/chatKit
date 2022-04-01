package com.itmv.netty.pipeline;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();
                pipeline.addLast(new StringEncoder());
            }
        });

        ChannelFuture future = bootstrap.connect("localhost", 8088);
        try {
            future.sync();
            System.out.println("客户端连接成功");

            Scanner scanner = new Scanner(System.in);
            while(true){
                String msg = scanner.nextLine();
//                ByteBuf buf = Unpooled.buffer(1024);
//                buf.writeBytes(msg.getBytes(StandardCharsets.UTF_8));
                future.channel().writeAndFlush(msg);            // 写入是个异步操作
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
