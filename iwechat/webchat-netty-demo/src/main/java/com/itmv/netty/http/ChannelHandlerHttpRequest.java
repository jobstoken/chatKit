package com.itmv.netty.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@ChannelHandler.Sharable
public class ChannelHandlerHttpRequest extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        System.out.println(request.uri());
        System.out.println(request.method().name());
        HttpHeaders headers = request.headers();
        List<Map.Entry<String, String>> entries = headers.entries();
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key+" : "+value);
        }

        System.out.println(request.content().toString(Charset.defaultCharset()));

        Channel channel = ctx.channel();
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        String html = "<html><body><p>Hello</p></body></html>";
        response.content().writeBytes(html.getBytes(StandardCharsets.UTF_8));
        channel.writeAndFlush(response);

    }
}
