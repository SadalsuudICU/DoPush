package com.sadalsuud.push.infrastructure.mq.receiver.kafka;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.sadalsuud.push.common.domain.RecallTaskInfo;
import com.sadalsuud.push.common.domain.TaskInfo;
import com.sadalsuud.push.domain.pipeline.MessageQueuePipeline;
import com.sadalsuud.push.domain.pipeline.task.GroupIdMappingUtils;
import com.sadalsuud.push.domain.pipeline.task.service.ConsumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @Description 消费消息
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 13/12/2023
 * @Package com.sadalsuud.push.infrastructure.mq.receiver.kafka
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "dopush.mq.pipeline", havingValue = MessageQueuePipeline.KAFKA)
@RequiredArgsConstructor
public class Receiver {

    private final ConsumeService consumeService;

    /**
     * 消费要下发的消息
     * @param consumerRecord kafka record
     * @param topicGroupId 消息主题id
     */
    //@KafkaListener(topics = "#{'${dopush.business.topic.name}'}", containerFactory = "filterContainerFactory")
    @KafkaListener(topics = "#{'${dopush.business.topic.name}'}")
    public void consumer(ConsumerRecord<?, String> consumerRecord, @Header(KafkaHeaders.GROUP_ID) String topicGroupId) {
        Optional<String> value = Optional.ofNullable(consumerRecord.value());
        if (value.isPresent()) {
            List<TaskInfo> taskInfos = JSON.parseArray(value.get(), TaskInfo.class);
            String messageGroupId = GroupIdMappingUtils.getGroupIdByTaskInfo(CollUtil.getFirst(taskInfos.iterator()));
            System.out.println("topicGroupId = " + topicGroupId);
            System.out.println("messageGroupId = " + messageGroupId);
            System.out.println("consumerRecord.headers() = " + consumerRecord.headers());
            System.out.println("consumerRecord.value() = " + consumerRecord.value());
            // 只关注属于自己group的消息
            //if (topicGroupId.equals(messageGroupId)) {
            //    consumeService.consume2Send(taskInfos);
            //}
            consumeService.consume2Send(taskInfos);

        }
    }

    /**
     *
     * @param consumerRecord
     */
    //@KafkaListener(topics = "#{'${dopush.business.recall.topic.name}'}", groupId = "#{'${dopush.business.recall.group.name}'}", containerFactory = "filterContainerFactory")
    @KafkaListener(topics = "#{'${dopush.business.recall.topic.name}'}", groupId = "#{'${dopush.business.recall.group.name}'}")
    public void recall(ConsumerRecord<?, String> consumerRecord) {
        Optional<String> value = Optional.ofNullable(consumerRecord.value());
        if (value.isPresent()) {
            RecallTaskInfo recallTaskInfo = JSON.parseObject(value.get(), RecallTaskInfo.class);
            consumeService.consume2recall(recallTaskInfo);
        }
    }

}
