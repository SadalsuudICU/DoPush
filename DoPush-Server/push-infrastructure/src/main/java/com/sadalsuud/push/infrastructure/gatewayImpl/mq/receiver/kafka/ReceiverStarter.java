package com.sadalsuud.push.infrastructure.gatewayImpl.mq.receiver.kafka;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import com.sadalsuud.push.domain.support.MessageQueuePipeline;
import com.sadalsuud.push.domain.support.GroupIdMappingUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.header.Header;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListenerAnnotationBeanPostProcessor;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

/**
 * @Description 启动消费者
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 13/12/2023
 * @Package com.sadalsuud.push.infrastructure.mq.receiver.kafka
 */
@Slf4j
@Service
@ConditionalOnProperty(name = "dopush.mq.pipeline", havingValue = MessageQueuePipeline.KAFKA)
@RequiredArgsConstructor
public class ReceiverStarter {

    /**
     * receiver的消费方法常量
     */
    private static final String RECEIVER_METHOD_NAME = "Receiver.consumer";
    /**
     * 获取得到所有的groupId
     */
    private static final List<String> groupIds = GroupIdMappingUtils.getAllGroupIds();
    /**
     * 下标(用于迭代groupIds位置)
     */
    private static Integer index = 0;


    private final ApplicationContext context;

    private final ConsumerFactory consumerFactory;

    /**
     * 给每个Receiver对象的consumer方法 @KafkaListener赋值相应的groupId
     */
    @Bean
    public static KafkaListenerAnnotationBeanPostProcessor.AnnotationEnhancer groupIdEnhancer() {
        return (attrs, element) -> {
            if (element instanceof Method) {
                String name = ((Method) element).getDeclaringClass().getSimpleName() + StrPool.DOT + ((Method) element).getName();
                if (RECEIVER_METHOD_NAME.equals(name)) {
                    attrs.put("groupId", groupIds.get(index++));
                }
            }
            return attrs;
        };
    }

    /**
     * 为每个渠道不同的消息类型 创建一个Receiver对象
     */
    @PostConstruct
    public void init() {
        for (int i = 0; i < groupIds.size(); i++) {
            context.getBean(Receiver.class);
        }
    }

    /**
     * 针对tag消息过滤
     * producer 将tag写进header里
     *
     * @return true 消息将会被丢弃
     */
    @Bean
    @SuppressWarnings("unchecked")
    public ConcurrentKafkaListenerContainerFactory filterContainerFactory(@Value("${dopush.business.tagId.key}") String tagIdKey,
                                                                          @Value("${dopush.business.tagId.value}") String tagIdValue) {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory);
        factory.setAckDiscarded(true);

        factory.setRecordFilterStrategy(consumerRecord -> {

            if (Optional.ofNullable(consumerRecord.value()).isPresent()) {
                if (StrUtil.isEmpty(tagIdValue)) {
                    return false;
                }
                for (Header header : consumerRecord.headers()) {
                    if (header.key().equals(tagIdKey) && new String(header.value()).equals(new String(tagIdValue.getBytes(StandardCharsets.UTF_8)))) {
                        return false;
                    }
                }
            }
            return true;
        });
        return factory;
    }
}
