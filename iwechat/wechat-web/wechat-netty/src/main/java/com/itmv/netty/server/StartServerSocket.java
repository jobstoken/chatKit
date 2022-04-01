package com.itmv.netty.server;

import com.itmv.netty.handler.ChatMessageHandler;
import com.itmv.netty.handler.ConnMessageHandler;
import com.itmv.netty.handler.HeartMessageHandler;
import com.itmv.netty.handler.WebSocketHandler;
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
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
public class StartServerSocket implements CommandLineRunner {

    @Value("${netty.port}")
    private Integer port;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public void run(String... args) throws Exception {
        System.out.println("ServerSocket启动");
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
//                    pipeline.addLast(new ReadTimeoutHandler(10, TimeUnit.SECONDS));

                    pipeline.addLast(new WebSocketHandler());
                    pipeline.addLast(new ConnMessageHandler(redisTemplate));
                    pipeline.addLast(new HeartMessageHandler());
                    pipeline.addLast(new ChatMessageHandler(rabbitTemplate));
                }
            });
            ChannelFuture future = bootstrap.bind(port);
            future.sync();
            System.out.println("服务端启动");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
