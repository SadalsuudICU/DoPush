package com.sadalsuud.push.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 日志参数
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogParam {

    /**
     * 需要记录的日志
     */
    private Object object;

    /**
     * 标识日志的业务
     */
    private String bizType;

    /**
     * 生成时间
     */
    private long timestamp;

}
