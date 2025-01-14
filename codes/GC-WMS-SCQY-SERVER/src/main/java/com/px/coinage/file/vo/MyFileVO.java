package com.px.coinage.file.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * ProjectSoftware实体类
 */
@Data
public class MyFileVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 软件名称
     */
    private String name;

    /**
     * 路径
     */
    private String url;

    /**
     * 标签
     */
    private String tag;

    private String remark;

}

