package com.px.coinage.core.annotation;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * -在Controller的方法参数中使用此注解，在不需要响应体序列化的方法上使用此注解
 */
@Retention(RUNTIME)
public @interface NotRestResponse {

}
