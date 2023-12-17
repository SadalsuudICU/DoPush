package com.sadalsuud.push.infrastructure.trace;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 17/12/2023
 * @Package com.sadalsuud.push.infrastructure.trace
 */
public interface TraceService {

    /**
     * 基于消息 ID 查询 链路结果
     *
     * @param messageId
     * @return
     */
    TraceResponse traceByMessageId(String messageId);
}
