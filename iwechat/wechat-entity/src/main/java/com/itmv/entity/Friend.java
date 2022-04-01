package com.itmv.entity;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@TableName("t_friend")
public class Friend {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer uid;

    private Integer fid;

    private Integer status;

    private String remark;

    @TableField(exist = false)
    private User friend;

}
