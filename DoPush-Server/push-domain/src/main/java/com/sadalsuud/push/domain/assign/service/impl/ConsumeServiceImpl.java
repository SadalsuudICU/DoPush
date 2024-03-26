package com.sadalsuud.push.domain.assign.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.sadalsuud.push.common.domain.AnchorInfo;
import com.sadalsuud.push.common.domain.LogParam;
import com.sadalsuud.push.common.domain.RecallTaskInfo;
import com.sadalsuud.push.common.domain.TaskInfo;
import com.sadalsuud.push.common.enums.AnchorState;
import com.sadalsuud.push.common.enums.MessageStatus;
import com.sadalsuud.push.domain.assign.pending.Task;
import com.sadalsuud.push.domain.assign.pending.TaskPendingHolder;
import com.sadalsuud.push.domain.assign.pipeline.config.HandlerThreadPoolConfig;
import com.sadalsuud.push.domain.assign.service.ConsumeService;
import com.sadalsuud.push.domain.support.GroupIdMappingUtils;
import com.sadalsuud.push.domain.support.LogUtils;
import com.sadalsuud.push.domain.assign.handler.HandlerHolder;
import com.sadalsuud.push.domain.template.MessageTemplate;
import com.sadalsuud.push.domain.template.repository.IMessageTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 11/12/2023
 * @Package com.sadalsuud.push.domain.pipeline.task.service.impl
 */
@Service
@RequiredArgsConstructor
public class ConsumeServiceImpl implements ConsumeService {
    private static final String LOG_BIZ_TYPE = "Receiver#consumer";
    private static final String LOG_BIZ_RECALL_TYPE = "Receiver#recall";

    private final ApplicationContext context;

    private final TaskPendingHolder taskPendingHolder;

    private final LogUtils logUtils;

    private final HandlerHolder handlerHolder;

    //TODO 需要重构解耦 这里直接使用模板仓储服务不合适
    private final IMessageTemplateRepository messageTemplateRepository;

    //单模板最大发送时间
    @Value("${dopush.template.max-sending-time}")
    private String MAX_SENDING_TIME;

    @Override
    public void consume2Send(List<TaskInfo> taskInfoLists) {
        // 开始真正的消费逻辑 更改消息模板的状态
        Optional<MessageTemplate> messageTemplate = messageTemplateRepository.findById(CollUtil.getFirst(taskInfoLists).getMessageTemplateId());
        final MessageTemplate[] clone = new MessageTemplate[1];
        if (messageTemplate.isPresent()) {
            clone[0] = ObjectUtil.clone(messageTemplate.get()).setMsgStatus(MessageStatus.SENDING.getCode()).setUpdated(Math.toIntExact(DateUtil.currentSeconds()));
            messageTemplateRepository.save(clone[0]);
        }else {
            //TODO 埋点日志
        }

        //线程执行情况
        CountDownLatch count = new CountDownLatch(taskInfoLists.size());

        String topicGroupId = GroupIdMappingUtils.getGroupIdByTaskInfo(CollUtil.getFirst(taskInfoLists.iterator()));
        for (TaskInfo taskInfo : taskInfoLists) {
            logUtils.print(
                    LogParam.builder()
                            .bizType(LOG_BIZ_TYPE)
                            .object(taskInfo).build(),
                    AnchorInfo.builder()
                            .bizId(taskInfo.getBizId())
                            .messageId(taskInfo.getMessageId())
                            .ids(taskInfo.getReceiver())
                            .businessId(taskInfo.getBusinessId())
                            .state(AnchorState.RECEIVE.getCode()).build());
            Task task = context.getBean(Task.class).setTaskInfo(taskInfo);
            ExecutorService route = taskPendingHolder.route(topicGroupId);
            route.execute(() -> {
                try {
                    task.run();
                }finally {
                    count.countDown();
                }
            });
        }

        taskPendingHolder.route(HandlerThreadPoolConfig.Monitor).execute(() -> {
            try {
                count.await(Long.parseLong(MAX_SENDING_TIME), TimeUnit.HOURS);
                if (messageTemplate.isPresent()) {
                    clone[0] = ObjectUtil.clone(messageTemplate.get()).setMsgStatus(MessageStatus.SEND_SUCCESS.getCode()).setUpdated(Math.toIntExact(DateUtil.currentSeconds()));
                    messageTemplateRepository.save(clone[0]);
                }
            } catch (InterruptedException e) {
                if (messageTemplate.isPresent() && count.getCount() == taskInfoLists.size()) {
                    clone[0] = ObjectUtil.clone(messageTemplate.get()).setMsgStatus(MessageStatus.SEND_FAIL.getCode()).setUpdated(Math.toIntExact(DateUtil.currentSeconds()));
                    messageTemplateRepository.save(clone[0]);
                }

                // TODO 任务失败通知逻辑 发消息到MQ


                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void consume2recall(RecallTaskInfo recallTaskInfo) {
        logUtils.print(LogParam.builder().bizType(LOG_BIZ_RECALL_TYPE).object(recallTaskInfo).build());
        handlerHolder.route(recallTaskInfo.getSendChannel()).recall(recallTaskInfo);
    }
}
