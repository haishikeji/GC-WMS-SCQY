package com.px.coinage.workshop.vo;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.px.coinage.core.annotation.FastJsonFieldProfile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Product查询响应bean
 */
@Data
public class ProductVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * -id
     */
    @ApiModelProperty(value = "id")
    @FastJsonFieldProfile(groups = {"list", "get"})
    @TableField(value = "id")
    private Integer id;
    /**
     * -产品名称
     */
    @ApiModelProperty(value = "产品名称")
    @FastJsonFieldProfile(groups = {"list", "get"})
    @TableField(value = "securities")
    private String securities;
    /**
     * -规格
     */
    @ApiModelProperty(value = "规格")
    @FastJsonFieldProfile(groups = {"list", "get"})
    @TableField(value = "spec")
    private String spec;
    /**
     * -冠字号代码
     */
    @ApiModelProperty(value = "冠字号代码")
    @FastJsonFieldProfile(groups = {"list", "get"})
    @TableField(value = "crown_prefix")
    private String crownPrefix;
    /**
     * -整拍箱数（箱）
     */
    @ApiModelProperty(value = "整拍箱数（箱）")
    @FastJsonFieldProfile(groups = {"list", "get"})
    @TableField(value = "box_amount")
    private Integer boxAmount;
    /**
     * -整箱枚数(枚)
     */
    @ApiModelProperty(value = "整箱枚数(枚)")
    @FastJsonFieldProfile(groups = {"list", "get"})
    @TableField(value = "amount_per_box")
    private Integer amountPerBox;
    /**
     * -是否启用 1:启用 2:停用
     */
    @ApiModelProperty(value = "是否启用 1:启用 2:停用")
    @FastJsonFieldProfile(groups = {"list", "get"})
    @TableField(value = "status")
    private Integer status;
    /**
     * -创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @FastJsonFieldProfile(groups = {"list", "get"})
    @TableField(value = "create_time")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
