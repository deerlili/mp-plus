package com.deerlili.mp.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author deerlili
 * @version v1.0
 * @description 用户信息
 * @Time 2021-01-15 23:47
 */

@Data
@TableName("mp_user")
public class User {

    @TableId
    private Long id;
    @TableField(value = "name",condition = SqlCondition.LIKE)
    private String userName;
    @TableField(condition = "%s&lt;#{%s}")
    private Integer age;
    private String email;
    private Long managerId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    // 逻辑删除：默认（0：未删除，1：删除）
    // value:未删除,delval:删除
    @TableLogic(value = "0",delval = "2")
    // 查询不显示
    @TableField(select = false)
    private Integer status;
    /**
     * 用于保存一些程序调用或者组装的数据
     * 在数据中没有对应的字段
     */
    private transient String remark;
    /**
     * 需要生成set、get
     */
    private static String remark1;
    @TableField(exist = false)
    private static String remark2;

    public static String getRemark1() {
        return remark1;
    }

    public static void setRemark1(String remark1) {
        User.remark1 = remark1;
    }

}
