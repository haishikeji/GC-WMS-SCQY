package com.px.coinage.workshop.model;

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
 * -实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)

@TableName("rfid_scan_log")
@ApiModel(value = "对象", description = "")
public class ScanLog implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * -id -10
     */
    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * -rfid -12
     */
    @ApiModelProperty(value = "rfid")
    @TableField(value = "rfid")
    private String rfid;
    /**
     * -物料编号 -20
     */
    @ApiModelProperty(value = "物料编号")
    @TableField(value = "serial_number")
    private String serialNumber;
    /**
     * -质量标识 1:无质量问题 2:有质量问题 -10
     */
    @ApiModelProperty(value = "质量标识 1:无质量问题 2:有质量问题")
    @TableField(value = "is_quality")
    private Integer isQuality;
    /**
     * -是否为整拍 1:整拍 2: 拆拍 -10
     */
    @ApiModelProperty(value = "是否为整拍 1:整拍 2: 拆拍")
    @TableField(value = "is_whole")
    private Integer isWhole;
    /**
     * -拆拍的箱子数量 -10
     */
    @ApiModelProperty(value = "拆拍的箱子数量")
    @TableField(value = "qty")
    private Integer qty;
    /**
     * -冠字号前缀 -20
     */
    @ApiModelProperty(value = "冠字号前缀")
    @TableField(value = "crown_prefix")
    private String crownPrefix;
    /**
     * -扫描时间 -19
     */
    @ApiModelProperty(value = "扫描时间")
    @TableField(value = "created")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date created;
    /**
     * -仓库编号:101:制造一部临时库 102:制造二部临时库103:制造三部临时库 201:主库一 202 主库二 （同一主库下的分库） -10
     */
    @ApiModelProperty(value = "仓库编号:101:制造一部临时库 102:制造二部临时库103:制造三部临时库  201:主库一 202 主库二 （同一主库下的分库）")
    @TableField(value = "ware_id")
    private String wareId;
    /**
     * -操作类型: 2 入库 3 出库 -10
     */
    @ApiModelProperty(value = "操作类型: 2 入库 3 出库")
    @TableField(value = "op_type")
    private Integer opType;
    /**
     * -1:扫描正常 2:扫描异常（没在基本信息表的rfid） -10
     */
    @ApiModelProperty(value = "1:扫描正常 2:扫描异常（没在基本信息表的rfid）")
    @TableField(value = "status")
    private Integer status;
    /**
     * -品种 -20
     */
    @ApiModelProperty(value = "品种")
    @TableField(value = "securities")
    private String securities;
    /**
     * -品种ID -10
     */
    @ApiModelProperty(value = "品种ID")
    @TableField(value = "securities_id")
    private Integer securitiesId;
}
