package com.sadalsuud.push.infrastructure.gatewayImpl.cron.service;

/**
 * @Description 具体的定时任务逻辑
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/3/6
 * @Project DoPush-Server
 */
public interface TaskHandler {

    /**
     * 处理具体的逻辑
     *
     * @param messageTemplateId
     */
    void handle(Long messageTemplateId);

}
