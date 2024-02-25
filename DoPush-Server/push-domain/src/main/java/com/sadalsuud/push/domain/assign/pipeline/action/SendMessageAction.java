package com.sadalsuud.push.domain.assign.pipeline.action;

import com.sadalsuud.push.common.domain.TaskInfo;
import com.sadalsuud.push.common.pipeline.BusinessProcess;
import com.sadalsuud.push.common.pipeline.ProcessContext;
import com.sadalsuud.push.domain.assign.handler.HandlerHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Description 真正的发送消息 通过holder路由到对应消息类型的handler
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 11/12/2023
 * @Package com.sadalsuud.push.domain.pipeline.task.action
 */
@Service
@RequiredArgsConstructor
public class SendMessageAction implements BusinessProcess<TaskInfo> {

    private final HandlerHolder handlerHolder;

    @Override
    public void process(ProcessContext<TaskInfo> context) {
        TaskInfo taskInfo = context.getProcessModel();
        handlerHolder.route(taskInfo.getSendChannel()).doHandler(taskInfo);
    }
}
