package com.px.coinage.workshop.req;

import com.alibaba.fastjson2.annotation.JSONField;
import com.px.coinage.core.req.PageReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * -记录每笔出入库流水表(车间出入库记录)列表查询请求bean
 */
@Getter
@Setter
public class WorkshopLogListReq extends PageReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * -rfid标签值 -12
     */
    @ApiModelProperty(value = "rfid标签值")
    private String rfid;
    /**
     * -编号 -20
     */
    @ApiModelProperty(value = "编号")
    private String serialNumber;


    @ApiModelProperty(value = "部门ID")
    private Integer deptId;

    @ApiModelProperty(value = "租户名称")
    private String dept;
    /**
     * -绑定的品名ID -10
     */
    @ApiModelProperty(value = "绑定的品名ID")
    private Integer securitiesId;
    /**
     * -品名名称 -20
     */
    @ApiModelProperty(value = "品名名称")
    private String securities;
    /**
     * -将rfid对应的冠字号范围，拼装成字符串，保存在此字端，用于页面展示 -100
     */
    @ApiModelProperty(value = "将rfid对应的冠字号范围，拼装成字符串，保存在此字端，用于页面展示")
    private String crownBetweenStr;
    /**
     * -冠字号范围：@开头的表示冠字号段 #开头的表示各个单独的冠字号（eg:@003-4970,003-5000;）(eg:#003-0005,003-0007) -100
     */
    @ApiModelProperty(value = "冠字号范围：@开头的表示冠字号段 #开头的表示各个单独的冠字号（eg:@003-4970,003-5000;）(eg:#003-0005,003-0007)")
    private String crownBetween;
    /**
     * -皮重(kg) -10
     */
    @ApiModelProperty(value = "皮重(kg)")
    private Double tareWeight;
    /**
     * -毛重（kg） -10
     */
    @ApiModelProperty(value = "毛重（kg）")
    private Double grossWeight;
    /**
     * -称重人id -10
     */
    @ApiModelProperty(value = "称重人id")
    private Integer weightorId;
    /**
     * -称重人 -2
     */
    @ApiModelProperty(value = "称重人")
    private String weightor;
    /**
     * -产品称重时间(即打印时间) -19
     */
    @ApiModelProperty(value = "产品称重时间(即打印时间)")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date weightTime;
    /**
     * -来源地1:主库一2:主库二 3:机台 4:临时库产品库区域 5 临时库剔除品区域 -10
     */
    @ApiModelProperty(value = "来源地1:主库一2:主库二 3:机台 4:临时库产品库区域 5 临时库剔除品区域")
    private Integer locFrom;
    /**
     * -目的地 1:主库一2:主库二 3:机台 4:临时库产品库区域 5 临时库剔除品区域 -10
     */
    @ApiModelProperty(value = "目的地 1:主库一2:主库二 3:机台 4:临时库产品库区域 5 临时库剔除品区域")
    private Integer locTo;
    /**
     * -目的地部门ID -10
     */
    @ApiModelProperty(value = "目的地部门ID")
    private Integer locDeptId;
    /**
     * -目的地部门名称 -20
     */
    @ApiModelProperty(value = "目的地部门名称")
    private String locDept;
    /**
     * -零枚数量 -10
     */
    @ApiModelProperty(value = "零枚数量")
    private Integer piecesTotal;
    /**
     * -操作人ID -10
     */
    @ApiModelProperty(value = "操作人ID")
    private Integer operatorId;
    /**
     * -操作人 -20
     */
    @ApiModelProperty(value = "操作人")
    private String operatorCn;
    /**
     * -复核人ID -10
     */
    @ApiModelProperty(value = "复核人ID")
    private Integer checkId;
    /**
     * -复核人姓名 -20
     */
    @ApiModelProperty(value = "复核人姓名")
    private String checker;
    /**
     * -创建时间 -19
     */
    @ApiModelProperty(value = "创建时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * -操作类型： 1:机台入产成品库（机台->成品）（车间入库） 2.机台入剔除品库（机台->剔除品）（车间入库）3.车间成品库到主库（成品->主库）(解缴出库)
     * 4.返检入剔除品库（主库->剔除品）（返检红冲） 5抽检入剔除品库（主库->剔除品）（抽检入库） 6.抽检上机台（剔除品->机台）(抽检出库) 7.抽检入车间成品库(机台->成品)（抽检入库）
     * 8.抽检出成品库（成品-> 主库）（抽检出库） 9:车间产品库入剔除品库（成品->剔除品）（车间内部迁移） -10
     */
    @ApiModelProperty(
            value = "操作类型： 1:机台入产成品库（机台->成品）（车间入库） 2.机台入剔除品库（机台->剔除品）（车间入库）3.车间成品库到主库（成品->主库）(解缴出库)  4.返检入剔除品库（主库->剔除品）（返检红冲） 5抽检入剔除品库（主库->剔除品）（抽检入库）  6.抽检上机台（剔除品->机台）(抽检出库) 7.抽检入车间成品库(机台->成品)（抽检入库） 8.抽检出成品库（成品-> 主库）（抽检出库）  9:车间产品库入剔除品库（成品->剔除品）（车间内部迁移）")
    private Integer opType;

}
