package com.sadalsuud.push.common.pipeline;


import com.sadalsuud.push.common.enums.RespStatusEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 */
@Getter
public class ProcessException extends RuntimeException {

    /**
     * 流程处理上下文
     */
    private final ProcessContext processContext;

    public ProcessException(ProcessContext processContext) {
        super();
        this.processContext = processContext;
    }

    public ProcessException(ProcessContext processContext, Throwable cause) {
        super(cause);
        this.processContext = processContext;
    }

    @Override
    public String getMessage() {
        if (Objects.nonNull(this.processContext)) {
            return this.processContext.getResponse().getMsg();
        }
        return RespStatusEnum.CONTEXT_IS_NULL.getMsg();

    }

}
