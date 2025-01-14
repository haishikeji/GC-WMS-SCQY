package com.px.coinage.sys.model;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * -用户扩展实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)

@TableName("sys_user_ext")
@ApiModel(value = "用户扩展对象", description = "用户扩展")
public class UserExt implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * -用户id -10
     */
    @ApiModelProperty(value = "用户id")
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;
    /**
     * -登录次数 -5
     */
    @ApiModelProperty(value = "登录次数")
    @TableField(value = "login_num")
    private Integer loginNum;
    /**
     * -最后登录时间 -19
     */
    @ApiModelProperty(value = "最后登录时间")
    @TableField(value = "last_time")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastTime;
    /**
     * -最后登录ip -16
     */
    @ApiModelProperty(value = "最后登录ip")
    @TableField(value = "ip")
    private String ip;
    /**
     * - 班组ID -10
     */
    @ApiModelProperty(value = " 班组ID")
    @TableField(value = "team_id")
    private Integer teamId;
    /**
     * -用户编号：唯一标识符8位,由用户填写 -8
     */
    @ApiModelProperty(value = "用户编号：唯一标识符8位,由用户填写")
    @TableField(value = "code")
    private String code;
    /**
     * -卡号：每个卡号对应一个员工编号，使用卡号进行登录 -20
     */
    @ApiModelProperty(value = "卡号：每个卡号对应一个员工编号，使用卡号进行登录")
    @TableField(value = "card_number")
    private String cardNumber;
}
