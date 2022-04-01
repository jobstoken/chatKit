package com.itmv.entity.netty;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OutLineMessage extends NettyMessage{

    /**
     * 用于用户下线
     */
    {
        setType(6);
    }
}
