package com.sadalsuud.push.infrastructure.mq.sender.springeventbus;

import com.sadalsuud.push.domain.gateway.SendMqService;
import com.sadalsuud.push.domain.pipeline.MessageQueuePipeline;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @Description 基于spring event bus 实现消息发送
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 13/12/2023
 * @Package com.sadalsuud.push.infrastructure.mq.sender.springeventbus
 */
@Slf4j
@Service
@ConditionalOnProperty(name = "dopush.mq.pipeline", havingValue = MessageQueuePipeline.SPRING_EVENT_BUS)
@RequiredArgsConstructor
public class SpringEventBusSendMqServiceImpl implements SendMqService {

    private final ApplicationContext applicationContext;

    /**
     * 发送消息
     *
     * @param topic
     * @param jsonValue
     * @param tagId
     */
    @Override
    public void send(String topic, String jsonValue, String tagId) {
        DopushSpringEventSource source =
                DopushSpringEventSource.builder()
                        .topic(topic).jsonValue(jsonValue)
                        .tagId(tagId).build();
        DopushSpringEventBusEvent dopushSpringEventBusEvent = new DopushSpringEventBusEvent(this, source);
        applicationContext.publishEvent(dopushSpringEventBusEvent);
    }

    /**
     * 发送消息
     *
     * @param topic
     * @param jsonValue
     */
    @Override
    public void send(String topic, String jsonValue) {
        send(topic, jsonValue, null);
    }
}
