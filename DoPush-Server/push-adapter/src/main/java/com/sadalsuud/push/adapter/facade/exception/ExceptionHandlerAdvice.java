package com.sadalsuud.push.adapter.facade.exception;

import com.sadalsuud.push.common.enums.RespStatusEnum;
import com.sadalsuud.push.common.vo.BasicResultVO;
import org.assertj.core.util.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Description 异常统一拦截返回
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 14/12/2023
 * @Package com.sadalsuud.push.adapter.facade.exception
 */
@ControllerAdvice(basePackages = "com.sadalsuud.push.adapter")
@ResponseBody
public class ExceptionHandlerAdvice {
    private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);


    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.OK)
    public BasicResultVO<String> exceptionResponse(Exception e) {
        log.error(Throwables.getStackTrace(e));
        return BasicResultVO.fail(RespStatusEnum.ERROR_500, "\r\n" + Throwables.getStackTrace(e) + "\r\n");
    }

    @ExceptionHandler({CommonException.class})
    @ResponseStatus(HttpStatus.OK)
    @SuppressWarnings("unchecked")
    public BasicResultVO<RespStatusEnum> commonResponse(CommonException ce) {
        log.error(Throwables.getStackTrace(ce));
        return new BasicResultVO(ce.getCode(), ce.getMessage(), ce.getRespStatusEnum());
    }
}
