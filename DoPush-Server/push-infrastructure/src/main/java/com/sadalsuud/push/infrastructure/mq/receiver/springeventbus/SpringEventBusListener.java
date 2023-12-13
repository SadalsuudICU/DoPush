package com.sadalsuud.push.infrastructure.mq.receiver.springeventbus;

import com.alibaba.fastjson.JSON;
import com.sadalsuud.push.common.domain.RecallTaskInfo;
import com.sadalsuud.push.common.domain.TaskInfo;
import com.sadalsuud.push.domain.support.MessageQueuePipeline;
import com.sadalsuud.push.infrastructure.mq.sender.springeventbus.DopushSpringEventBusEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Description 事件监听器
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 13/12/2023
 * @Package com.sadalsuud.push.infrastructure.mq.receiver.springeventbus
 */
@Component
@ConditionalOnProperty(name = "dopush.mq.pipeline", havingValue = MessageQueuePipeline.SPRING_EVENT_BUS)
@RequiredArgsConstructor
public class SpringEventBusListener implements ApplicationListener<DopushSpringEventBusEvent> {

    private final SpringEventBusReceiver springEventBusReceiver;

    @Value("${dopush.business.topic.name}")
    private String sendTopic;
    @Value("${dopush.business.recall.topic.name}")
    private String recallTopic;


    @Override
    public void onApplicationEvent(DopushSpringEventBusEvent event) {
        String topic = event.getDopushSpringEventSource().getTopic();
        String jsonValue = event.getDopushSpringEventSource().getJsonValue();
        if (sendTopic.equals(topic)) {
            springEventBusReceiver.consume(JSON.parseArray(jsonValue, TaskInfo.class));
        } else if (recallTopic.equals(topic)) {
            springEventBusReceiver.recall(JSON.parseObject(jsonValue, RecallTaskInfo.class));
        }
    }
}
