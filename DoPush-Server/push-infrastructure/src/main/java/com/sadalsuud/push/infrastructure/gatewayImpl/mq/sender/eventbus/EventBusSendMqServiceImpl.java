package com.sadalsuud.push.infrastructure.gatewayImpl.mq.sender.eventbus;

import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.EventBus;
import com.sadalsuud.push.common.domain.RecallTaskInfo;
import com.sadalsuud.push.common.domain.TaskInfo;
import com.sadalsuud.push.domain.support.gateway.SendMqService;
import com.sadalsuud.push.infrastructure.gatewayImpl.mq.MessageQueuePipeline;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 * @Package com.sadalsuud.push.infrastructure.mq.eventbus
 */
@Slf4j
@Service
@ConditionalOnProperty(name = "dopush.mq.pipeline", havingValue = MessageQueuePipeline.EVENT_BUS)
@RequiredArgsConstructor
public class EventBusSendMqServiceImpl implements SendMqService {
    private static final EventBus eventBus = new EventBus();

    @Getter
    @Value("${dopush.business.topic.name}")
    private String sendTopic;
    @Value("${dopush.business.recall.topic.name}")
    private String recallTopic;

    private final EventBusListener eventBusListener;

    /**
     * 单机 队列默认不支持 tagId过滤（单机无必要）
     *
     * @param topic
     * @param jsonValue
     * @param tagId
     */
    @Override
    public void send(String topic, String jsonValue, String tagId) {
        eventBus.register(eventBusListener);
        if (topic.equals(sendTopic)) {
            eventBus.post(JSON.parseArray(jsonValue, TaskInfo.class));
        } else if (topic.equals(recallTopic)) {
            eventBus.post(JSON.parseObject(jsonValue, RecallTaskInfo.class));
        }
    }

    @Override
    public void send(String topic, String jsonValue) {
        send(topic, jsonValue, null);
    }

}
