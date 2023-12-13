package com.sadalsuud.push.domain.assign.pipeline.action.send;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Throwables;
import com.sadalsuud.push.common.domain.SimpleTaskInfo;
import com.sadalsuud.push.common.domain.TaskInfo;
import com.sadalsuud.push.common.enums.RespStatusEnum;
import com.sadalsuud.push.common.pipeline.BusinessProcess;
import com.sadalsuud.push.common.pipeline.ProcessContext;
import com.sadalsuud.push.common.vo.BasicResultVO;
import com.sadalsuud.push.domain.gateway.SendMqService;
import com.sadalsuud.push.domain.assign.SendTaskModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 1. 将消息发送到MQ
 *              2. 返回拼装好的messageId给到接口调用方
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 * @Package com.sadalsuud.push.domain.pipeline.action.send
 */
@Slf4j
@Service
public class SendMqAction implements BusinessProcess<SendTaskModel> {


    @Resource
    private SendMqService sendMqService;

    @Value("${dopush.business.topic.name}")
    private String sendMessageTopic;

    @Value("${dopush.business.tagId.value}")
    private String tagId;

    @Value("${dopush.mq.pipeline}")
    private String mqPipeline;

    @Override
    public void process(ProcessContext<SendTaskModel> context) {
        SendTaskModel sendTaskModel = context.getProcessModel();
        List<TaskInfo> taskInfo = sendTaskModel.getTaskInfo();
        try {
            //String message = JSON.toJSONString(sendTaskModel.getTaskInfo(), new SerializerFeature[]{SerializerFeature.WriteClassName});
            String message = JSON.toJSONString(sendTaskModel.getTaskInfo(), SerializerFeature.WriteClassName);
            sendMqService.send(sendMessageTopic, message, tagId);

            context.setResponse(BasicResultVO.success(taskInfo.stream().map(v -> SimpleTaskInfo.builder().businessId(v.getBusinessId()).messageId(v.getMessageId()).bizId(v.getBizId()).build()).collect(Collectors.toList())));
        } catch (Exception e) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.SERVICE_ERROR));
            log.error("send {} fail! e:{},params:{}", mqPipeline, Throwables.getStackTraceAsString(e)
                    , JSON.toJSONString(CollUtil.getFirst(taskInfo.listIterator())));
        }
    }

}
