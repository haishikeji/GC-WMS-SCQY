package com.px.coinage.monitor.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CacheUpdateReq extends CacheKeysReq {


    @NotBlank(message = "缓存内容不能为空")
    private String value;

}
