package com.itmv.entity.netty;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ChatMessage extends NettyMessage{

    private Integer fid;

    private Integer tid;

    private String content;

}
