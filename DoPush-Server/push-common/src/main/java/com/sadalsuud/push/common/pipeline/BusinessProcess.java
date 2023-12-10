package com.sadalsuud.push.common.pipeline;

/**
 * @Description 业务执行器
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 */
public interface BusinessProcess<T extends ProcessModel> {

    /**
     * 真正处理逻辑
     *
     * @param context
     */
    void process(ProcessContext<T> context);
}
