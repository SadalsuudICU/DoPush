package com.sadalsuud.push.domain.gateway;

import com.sadalsuud.push.common.domain.RecallTaskInfo;
import com.sadalsuud.push.common.domain.TaskInfo;

import java.util.List;

/**
 * @Description 消费消息服务
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 * @Package com.sadalsuud.push.domain.gateway
 */
public interface ConsumeService {

    /**
     * 从MQ拉到消息进行消费，发送消息
     *
     * @param taskInfoLists
     */
    void consume2Send(List<TaskInfo> taskInfoLists);


    /**
     * 从MQ拉到消息进行消费，撤回消息
     * 如果有 recallMessageId ，则优先撤回 recallMessageId
     * 如果没有 recallMessageId ，则撤回整个模板的消息
     *
     * @param recallTaskInfo
     */
    void consume2recall(RecallTaskInfo recallTaskInfo);
}
