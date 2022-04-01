package com.itmv.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.concurrent.TimeUnit;

public class WebSocketServer {
    public static void main(String[] args) {
        EventLoopGroup master = new NioEventLoopGroup();
        EventLoopGroup slave = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(master, slave);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel channel) throws Exception {
                    ChannelPipeline pipeline = channel.pipeline();
                    pipeline.addLast(new HttpServerCodec());                                    // HttpRequest
                    pipeline.addLast(new HttpObjectAggregator(1024 * 10));      // FullHttpRequest
                    pipeline.addLast(new WebSocketServerProtocolHandler("/"));     // websocket解编码器
                    // 客户端N秒后不发送消息就断开连接
                    pipeline.addLast(new ReadTimeoutHandler(10, TimeUnit.SECONDS));
                    pipeline.addLast(new WebSocketChannelHandler());
                }
            });
            ChannelFuture future = bootstrap.bind(8088);
            future.sync();
            System.out.println("服务端启动");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
