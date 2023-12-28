package com.sadalsuud.push.infrastructure.gatewayImpl.mq.receiver.springeventbus;

import com.sadalsuud.push.common.domain.RecallTaskInfo;
import com.sadalsuud.push.common.domain.TaskInfo;
import com.sadalsuud.push.infrastructure.gatewayImpl.mq.MessageQueuePipeline;
import com.sadalsuud.push.domain.assign.service.ConsumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description 消息接收者 专注消息的接收与发送
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 13/12/2023
 * @Package com.sadalsuud.push.infrastructure.mq.receiver.springeventbus
 */
@Component
@ConditionalOnProperty(name = "dopush.mq.pipeline", havingValue = MessageQueuePipeline.SPRING_EVENT_BUS)
@RequiredArgsConstructor
public class SpringEventBusReceiver {

    private final ConsumeService consumeService;

    public void consume(List<TaskInfo> lists) {
        consumeService.consume2Send(lists);
    }

    public void recall(RecallTaskInfo recallTaskInfo) {
        consumeService.consume2recall(recallTaskInfo);
    }

}
