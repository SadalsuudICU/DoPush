package com.sadalsuud.push.infrastructure.mq.receiver.eventbus;

import com.google.common.eventbus.Subscribe;
import com.sadalsuud.push.common.domain.RecallTaskInfo;
import com.sadalsuud.push.common.domain.TaskInfo;
import com.sadalsuud.push.domain.support.MessageQueuePipeline;
import com.sadalsuud.push.domain.receipt.service.ConsumeService;
import com.sadalsuud.push.infrastructure.mq.sender.eventbus.EventBusListener;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 * @Package com.sadalsuud.push.infrastructure.mq.receiver.eventbus
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "dopush.mq.pipeline", havingValue = MessageQueuePipeline.EVENT_BUS)
public class EventBusReceiver implements EventBusListener {

    private final ConsumeService consumeService;

    @Override
    @Subscribe
    public void consume(List<TaskInfo> lists) {
        consumeService.consume2Send(lists);

    }

    @Override
    @Subscribe
    public void recall(RecallTaskInfo recallTaskInfo) {
        consumeService.consume2recall(recallTaskInfo);
    }
}

