package com.sadalsuud.push.adapter.facade.advice;

import com.sadalsuud.push.adapter.facade.annotation.DoPushResult;
import com.sadalsuud.push.common.vo.BasicResultVO;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * @Description 基于ResponseBodyAdvice实现统一返回结构
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 12/12/2023
 * @Package com.sadalsuud.push.adapter.facade.advice
 */
@ControllerAdvice(basePackages = "com.sadalsuud.push.adapter.web")
public class DoPushResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private static final Class<?> RETURN_CLAZZ = BasicResultVO.class;

    @Override
    public boolean supports(MethodParameter parameter, Class<? extends HttpMessageConverter<?>> clz) {
        return parameter.getContainingClass().isAnnotationPresent(DoPushResult.class)
                || parameter.hasMethodAnnotation(DoPushResult.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter parameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> clz,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        if (Objects.nonNull(body) && body.getClass() == RETURN_CLAZZ) {
            return body;
        }
        return BasicResultVO.success(body);
    }
}
