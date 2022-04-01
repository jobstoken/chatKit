package com.itmv.netty.hello;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {
    public static void main(String[] args) {

        // 1、创建线程池
        EventLoopGroup master = new NioEventLoopGroup();
        EventLoopGroup slave = new NioEventLoopGroup();

        // 2、创建引导类
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(master, slave);
        bootstrap.channel(NioServerSocketChannel.class);        // 设置通道
        bootstrap.childHandler(new ServerSocketHandler());      // 处理客户端饿handler

        // 3、异步绑定端口
        ChannelFuture future = bootstrap.bind(8088);
        try {
            future.sync();
            System.out.println("服务器启动");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
