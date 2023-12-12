package com.sadalsuud.push.adapter.facade.annotation;

import java.lang.annotation.*;

/**
 * @Description 统一返回结果注解
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 12/12/2023
 * @Package com.sadalsuud.push.adapter.facade.annotation
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DoPushResult {
}
