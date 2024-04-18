package com.sadalsuud.push.domain.assign.model.task;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Description 发送失败的任务
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/4/17
 * @Project DoPush-Server
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Accessors(chain = true)
public class FailedTask {

    /**
     * 业务消息发送Id, 用于链路追踪, 若不存在, 则使用 messageId
     */
    private String bizId;

    /**
     * 消息唯一Id(数据追踪使用)
     * 生成逻辑参考 TaskInfoUtils
     */
    @Id
    private String messageId;

    /**
     * 消息模板Id
     */
    private Long messageTemplateId;

    /**
     * 业务Id(数据追踪使用)
     * 生成逻辑参考 TaskInfoUtils
     */
    private Long businessId;

    /**
     * 接收者
     */
    private String receiver;

    private Integer time;
}
