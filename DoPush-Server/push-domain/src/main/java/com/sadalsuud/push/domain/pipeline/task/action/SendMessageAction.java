package com.sadalsuud.push.domain.pipeline.task.action;

import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.Sets;
import com.sadalsuud.push.common.domain.TaskInfo;
import com.sadalsuud.push.common.enums.ChannelType;
import com.sadalsuud.push.common.pipeline.BusinessProcess;
import com.sadalsuud.push.common.pipeline.ProcessContext;
import com.sadalsuud.push.domain.pipeline.task.handler.HandlerHolder;
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

        // 微信小程序&服务号只支持单人推送，为了后续逻辑统一处理，于是在这做了单发处理
        if (ChannelType.MINI_PROGRAM.getCode().equals(taskInfo.getSendChannel())
                || ChannelType.OFFICIAL_ACCOUNT.getCode().equals(taskInfo.getSendChannel())) {
            for (String receiver : taskInfo.getReceiver()) {
                TaskInfo taskClone = ObjectUtil.cloneByStream(taskInfo);
                taskClone.setReceiver(Sets.newHashSet(receiver));
                handlerHolder.route(taskInfo.getSendChannel()).doHandler(taskClone);
            }
            return;
        }
        handlerHolder.route(taskInfo.getSendChannel()).doHandler(taskInfo);
    }
}
