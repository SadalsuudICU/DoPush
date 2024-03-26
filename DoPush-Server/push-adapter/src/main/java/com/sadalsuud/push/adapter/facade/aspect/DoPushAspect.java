package com.sadalsuud.push.adapter.facade.aspect;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.sadalsuud.push.infrastructure.gatewayImpl.repository.RequestLogDao;
import com.sadalsuud.push.infrastructure.gatewayImpl.repository.entity.RequestLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @Description AOP日志切面类
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 12/12/2023
 * @Package com.sadalsuud.push.adapter.facade.aspect
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class DoPushAspect {

    /**
     * 同一个请求的KEY
     */
    private static final String REQUEST_ID_KEY = "request_unique_id";


    private final HttpServletRequest request;

    private final RequestLogDao requestLogDao;

    @Pointcut("@within(com.sadalsuud.push.adapter.facade.annotation.DoPushAspect) || @annotation(com.sadalsuud.push.adapter.facade.annotation.DoPushAspect)")
    public void executeService(){}


    /**
     * 前置通知，方法调用前被调用
     *
     * @param joinPoint
     */
    @Before("executeService()")
    public void doBeforeAdvice(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        RequestLog requestLog = this.printRequestLog(methodSignature, joinPoint.getArgs());
        //TODO 日志批量落库 使用binlog解耦日志的拦截与落库
        requestLogDao.save(requestLog);
    }

    /**
     * 异常通知
     *
     * @param ex
     */
    @AfterThrowing(value = "executeService()", throwing = "ex")
    public void doAfterThrowingAdvice(Throwable ex) {
        printExceptionLog(ex);
    }

    /**
     * 打印请求日志
     *
     * @param methodSignature
     * @param argObs
     */
    public RequestLog printRequestLog(MethodSignature methodSignature, Object[] argObs) {
        RequestLog requestLog = new RequestLog();
        //设置请求唯一ID
        requestLog.setId(IdUtil.fastUUID());
        request.setAttribute(REQUEST_ID_KEY, requestLog.getId());
        requestLog.setUri(request.getRequestURI());
        requestLog.setMethod(request.getMethod());
        List<Object> args = Lists.newArrayList();
        //过滤掉一些不能转为json字符串的参数
        Arrays.stream(argObs).forEach(e -> {
            if (e instanceof MultipartFile || e instanceof HttpServletRequest
                    || e instanceof HttpServletResponse || e instanceof BindingResult) {
                return;
            }
            args.add(e);
        });
        requestLog.setArgs(Arrays.toString(args.toArray()));
        requestLog.setProduct("dopush");
        requestLog.setPath(methodSignature.getDeclaringTypeName() + "." + methodSignature.getMethod().getName());
        requestLog.setReferer(request.getHeader("referer"));
        requestLog.setRemoteAddr(request.getRemoteAddr());
        requestLog.setUserAgent(request.getHeader("user-agent"));
        String auth = request.getHeader("auth");
        String account = request.getHeader("loginAccount");
        try {
            int auth1;
            if (!StringUtils.isEmpty(auth) && !StringUtils.isEmpty(account) && (auth1 = Integer.parseInt(auth)) > 0) {
                requestLog.setAuth(auth1);
                requestLog.setLoginAccount(account);
            }
        }catch (Exception e) {
            log.info("trans log's filed auth failed for {}", JSON.toJSONString(e.getCause()));
        }

        log.info(JSON.toJSONString(requestLog));

        return requestLog;
    }

    /**
     * 打印异常日志
     *
     * @param ex
     */
    public void printExceptionLog(Throwable ex) {
        JSONObject logVo = new JSONObject();
        logVo.put("id", request.getAttribute(REQUEST_ID_KEY));
        log.error(JSON.toJSONString(logVo), ex);
    }
}
