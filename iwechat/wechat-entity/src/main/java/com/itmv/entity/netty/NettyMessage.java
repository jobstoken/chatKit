package com.itmv.entity.netty;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class NettyMessage implements Serializable {

    /**
     * 1----新链接
     * 2----心跳
     * 3----聊天消息
     * 4----正在输入
     * 5----结束输入
     * 6----用户下线
     */
    private Integer type;

    /**
     * 客户端设备id
     */
    private String id;

}
