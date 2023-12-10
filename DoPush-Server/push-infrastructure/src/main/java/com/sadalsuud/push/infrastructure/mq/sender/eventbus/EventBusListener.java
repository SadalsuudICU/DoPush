package com.sadalsuud.push.infrastructure.mq.sender.eventbus;

import com.sadalsuud.push.common.domain.RecallTaskInfo;
import com.sadalsuud.push.common.domain.TaskInfo;

import java.util.List;

/**
 * @Description 监听器
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 * @Package com.sadalsuud.push.infrastructure.mq.eventbus
 */
public interface EventBusListener {


    /**
     * 消费消息
     *
     * @param lists
     */
    void consume(List<TaskInfo> lists);

    /**
     * 撤回消息
     *
     * @param recallTaskInfo
     */
    void recall(RecallTaskInfo recallTaskInfo);
}
