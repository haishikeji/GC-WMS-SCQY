package com.px.coinage.sys.req;

import com.baomidou.mybatisplus.annotation.TableField;
import com.px.coinage.core.req.PageReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

/**
 * 用户查询请求bean
 */
@Getter
@Setter
public class AdminUserListReq extends PageReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态 1可用 2禁用
     */
    @ApiModelProperty(value = "状态 1可用 2禁用")
    @Range(min = 1, max = 2, message = "状态值范围在1至2之间")
    private Integer status;
    /**
     * 性别 1男 2女 3未知
     */
    @ApiModelProperty(value = "性别 1男 2女 3未知")
    @Range(min = 1, max = 3, message = "性别值范围在1至3之间")
    private Integer sex;
    /**
     * 机构id
     */
    @ApiModelProperty(value = "机构id")
    private Integer orgId;
    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private Integer deptId;
    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    private String realName;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String mobile;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "租户id")
    @TableField(value = "tenant_id")
    private Integer tenantId;

}
