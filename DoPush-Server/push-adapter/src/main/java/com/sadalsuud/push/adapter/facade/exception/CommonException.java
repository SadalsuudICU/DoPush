package com.sadalsuud.push.adapter.facade.exception;

import com.sadalsuud.push.common.enums.RespStatusEnum;
import lombok.Getter;

/**
 * @Description 自定义通用异常
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 14/12/2023
 * @Package com.sadalsuud.push.adapter.facade.exception
 */
@Getter
public class CommonException extends RuntimeException {
    private String code = RespStatusEnum.ERROR_400.getCode();
    private RespStatusEnum respStatusEnum = null;

    public CommonException(String message) {
        super(message);
    }

    public CommonException(RespStatusEnum respStatusEnum) {
        super(respStatusEnum.getMsg());
        this.code = respStatusEnum.getCode();
        this.respStatusEnum = respStatusEnum;
    }

    public CommonException(String code, String message) {
        super(message);
        this.code = code;
    }

    public CommonException(String code, String message, RespStatusEnum respStatusEnum) {
        super(message);
        this.code = code;
        this.respStatusEnum = respStatusEnum;
    }

    public CommonException(String message, Exception e) {
        super(message, e);
    }

    public CommonException(String code, String message, Exception e) {
        super(message, e);
        this.code = code;
    }

}
