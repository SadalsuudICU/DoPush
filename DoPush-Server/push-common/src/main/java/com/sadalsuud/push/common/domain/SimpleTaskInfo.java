package com.sadalsuud.push.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description SimpleTaskInfo  调用发送接口成功后返回对应的信息，用于查看下发情况
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleTaskInfo {

    /**
     * 业务消息发送Id, 用于链路追踪, 若不存在, 则使用 messageId
     */
    private String bizId;

    /**
     * 消息唯一Id(数据追踪使用)
     * 生成逻辑参考 TaskInfoUtils
     */
    private String messageId;

    /**
     * 业务Id(数据追踪使用)
     * 生成逻辑参考 TaskInfoUtils
     */
    private Long businessId;
}
