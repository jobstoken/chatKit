package com.itmv.netty.channel;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ChannelGroup {

    /**
     * key:设备id（每个客户端设备的唯一标识）
     * Channel:设备id的连接对象
     */
    public static Map<String, Channel> channelMap = new HashMap<>();

    public static void addChannel(String id, Channel channel) {
        channelMap.put(id, channel);
    }

    public static Channel getChannel(String id) {
        return channelMap.get(id);
    }

    public static void removeChannel(String id) {
        channelMap.remove(id);
    }

    /**
     * 将断开后的channel从Map中删除
     * @param channel
     */
    public static void removeChannel(Channel channel) {
        if (channelMap.containsValue(channel)) {
            Set<Map.Entry<String, Channel>> entries = channelMap.entrySet();
            for (Map.Entry<String, Channel> entry : entries) {
                if (entry.getValue() == channel) {
                    channelMap.remove(entry.getKey());
                    break;
                }
            }
        }
    }

}
