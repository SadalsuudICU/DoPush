package com.sadalsuud.push.adapter.facade.annotation;

import java.lang.annotation.*;

/**
 * @Description 接口切面
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 12/12/2023
 * @Package com.sadalsuud.push.adapter.facade
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DoPushAspect {
}
