package com.px.coinage.sys.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Menu实体类
 */
@Data
public class MenuVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "自增主键")
    private Integer id;

    /**
     * 菜单树隐藏标记 1是 0否
     */
    @ApiModelProperty(value = "菜单树隐藏标记 1是 0否")
    private Boolean hideMenu;

    @ApiModelProperty(value = "排序")
    private Integer sorted;

    /**
     * 父id
     */
    @ApiModelProperty(value = "父id")
    private Integer pid;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String icon;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 路径
     */
    @ApiModelProperty(value = "路径")
    private String path;

    /**
     * 路由
     */
    @ApiModelProperty(value = "路由")
    private String component;

    /**
     * 跳转
     */
    @ApiModelProperty(value = "跳转")
    private String redirect;

    @ApiModelProperty(value = "按钮操作权限集合")
    private List<MenuPermissionVO> permissionList;

}
