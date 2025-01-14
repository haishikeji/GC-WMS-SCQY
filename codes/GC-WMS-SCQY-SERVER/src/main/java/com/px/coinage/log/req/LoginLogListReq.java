package com.px.coinage.log.req;

import com.px.coinage.core.req.PageReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

/**
 * 登入登出记录查询请求bean
 */
@Getter
@Setter
public class LoginLogListReq extends PageReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 类型 1登入 2登出
     */
    @ApiModelProperty(value = "类型 1登入 2登出")
    @Range(min = 1, max = 2, message = "类型值错误,1:登入 2:登出")
    private Integer type;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String realName;

    /**
     * 操作ip
     */
    @ApiModelProperty(value = "操作ip")
    private String ip;
    /**
     * 租户id
     */
    @ApiModelProperty(value = "租户id")
    private Integer tenantId;

}
