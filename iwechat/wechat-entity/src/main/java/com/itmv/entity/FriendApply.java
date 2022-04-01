package com.itmv.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("t_apply")
public class FriendApply implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer fid;

    private Integer tid;

    private String content;

    @TableField(value = "create_time")
    private Date createTime;

    private Integer status;

    @TableField(exist = false)
    private User friend;
}
