package com.px.coinage.sys.model;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)

@TableName("sys_user")
// @ApiModel(value = "用户对象", description = "用户")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键 -10
     */
    @ApiModelProperty(value = "自增主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "状态 1可用 2禁用")
    @TableField(value = "status")
    private Integer status;

    /**
     * 删除标记 1未删除 2已删除 -3
     */
    @ApiModelProperty(value = "删除标记 1未删除 2已删除")
    @TableField(value = "del_flag")
    private Integer delFlag;

    /**
     * 用户类型 1管理员 -3
     */
    @ApiModelProperty(value = "用户类型 1超级管理员 2管理员")
    @TableField(value = "type")
    private Integer type;

    /**
     * 初始密码 1是 2否 -3
     */
    @ApiModelProperty(value = "初始密码 1是 2否")
    @TableField(value = "pwd_init")
    private Integer pwdInit;

    /**
     * 性别 1男 2女 3未知 -3
     */
    @ApiModelProperty(value = "性别 1男 2女 3未知")
    @TableField(value = "sex")
    private Integer sex;

    /**
     * 机构id -10
     */
    @ApiModelProperty(value = "机构id")
    @TableField(value = "org_id")
    private Integer orgId;

    /**
     * 部门id -10
     */
    @ApiModelProperty(value = "部门id")
    @TableField(value = "dept_id")
    private Integer deptId;

    /**
     * 真实姓名 -10
     */
    @ApiModelProperty(value = "真实姓名")
    @TableField(value = "real_name")
    private String realName;

    /**
     * 手机号 -12
     */
    @ApiModelProperty(value = "手机号")
    @TableField(value = "mobile")
    private String mobile;

    /**
     * 创建人 -16
     */
    @ApiModelProperty(value = "创建人")
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 用户名 -16
     */
    @ApiModelProperty(value = "用户名")
    @TableField(value = "username")
    private String username;

    /**
     * 电话 -16
     */
    @ApiModelProperty(value = "电话")
    @TableField(value = "telephone")
    private String telephone;

    /**
     * 生日 -10
     */
    @ApiModelProperty(value = "生日")
    @TableField(value = "birthday")
    @JSONField(format = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 密码(md5加盐) -32
     */
    @ApiModelProperty(value = "密码(md5加盐)")
    @TableField(value = "password")
    private String password;

    /**
     * 盐 -32
     */
    @ApiModelProperty(value = "盐 ")
    @TableField(value = "salt")
    private String salt;

    /**
     * 电子邮箱 -32
     */
    @ApiModelProperty(value = "电子邮箱")
    @TableField(value = "email")
    private String email;

    /**
     * 工号 -32
     */
    @ApiModelProperty(value = "工号")
    @TableField(value = "work_no")
    private String workNo;

    /**
     * 职务 -32
     */
    @ApiModelProperty(value = "职务")
    @TableField(value = "duty")
    private String duty;

    /**
     * 创建时间 -19
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 最后修改人 -10
     */
    @ApiModelProperty(value = "最后修改人")
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * 最后修改时间 -19
     */
    @ApiModelProperty(value = "最后修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 头像 -200
     */
    @ApiModelProperty(value = "头像")
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 备注 -200
     */
    @ApiModelProperty(value = "备注")
    @TableField(value = "remark")
    private String remark;

    /**
     * 租户id -10
     */
    @ApiModelProperty(value = "租户id")
    @TableField(value = "tenant_id")
    private Integer tenantId;

}
