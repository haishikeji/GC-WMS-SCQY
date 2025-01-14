package com.px.coinage.workshop.model;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * -产品表实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)

@TableName("rfid_product_plan")
@ApiModel(value = "产品计划表对象", description = "产品计划表")
public class ProductPlan implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * -id -10
     */
    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "产品id")
    @TableField(value = "product_id")
    private Integer productId;

    /**
     * -产品名称 -60
     */
    @ApiModelProperty(value = "产品名称")
    @TableField(value = "securities")
    private String securities;
    /**
     * -创建时间 -19
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "冠字号代码")
    @TableField(value = "crown_prefix")
    private String crownPrefix;

    @ApiModelProperty(value = "是否启用 1:启用 2:停用")
    @TableField(value = "status")
    private Integer status;

    @ApiModelProperty(value = "冠字号起始")
    @TableField(value = "crown_from")
    private String crownFrom;

    @ApiModelProperty(value = "冠字号结束点")
    @TableField(value = "crown_to")
    private String crownTo;

}
