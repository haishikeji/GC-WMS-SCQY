package com.px.coinage.sys.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserListByRoleNameReq {
    /**
     * -角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

}
