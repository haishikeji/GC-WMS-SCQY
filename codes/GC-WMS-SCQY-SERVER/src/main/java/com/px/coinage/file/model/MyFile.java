package com.px.coinage.file.model;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * MyFile实体类
 */
@Data
@TableName("t_my_file")
public class MyFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "软件名称")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value = "路径")
    @TableField(value = "url")
    private String url;

    @ApiModelProperty(value = "排序")
    @TableField(value = "sorted")
    private Integer sorted;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "创建者")
    @TableField(value = "create_by")
    private String createBy;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value = "文件大小")
    @TableField(value = "file_size")
    private String fileSize;

    @ApiModelProperty(value = "备注")
    @TableField(value = "remark")
    private String remark;

}

