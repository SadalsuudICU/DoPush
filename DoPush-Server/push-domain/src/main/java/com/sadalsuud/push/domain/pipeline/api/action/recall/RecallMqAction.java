package com.sadalsuud.push.domain.pipeline.api.action.recall;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Throwables;
import com.sadalsuud.push.common.domain.RecallTaskInfo;
import com.sadalsuud.push.common.enums.RespStatusEnum;
import com.sadalsuud.push.common.pipeline.BusinessProcess;
import com.sadalsuud.push.common.pipeline.ProcessContext;
import com.sadalsuud.push.common.vo.BasicResultVO;
import com.sadalsuud.push.domain.gateway.SendMqService;
import com.sadalsuud.push.domain.pipeline.api.RecallTaskModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Description 将撤回消息发送到MQ
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 * @Package com.sadalsuud.push.domain.pipeline.api.action.recall
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RecallMqAction implements BusinessProcess<RecallTaskModel> {
    private final SendMqService sendMqService;

    @Value("${dopush.business.recall.topic.name}")
    private String dopushRecall;
    @Value("${dopush.business.tagId.value}")
    private String tagId;

    @Value("${dopush.mq.pipeline}")
    private String mqPipeline;

    @Override
    public void process(ProcessContext<RecallTaskModel> context) {
        RecallTaskInfo recallTaskInfo = context.getProcessModel().getRecallTaskInfo();
        try {
            String message = JSON.toJSONString(recallTaskInfo, SerializerFeature.WriteClassName);
            sendMqService.send(dopushRecall, message, tagId);
        } catch (Exception e) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.SERVICE_ERROR));
            log.error("send {} fail! e:{},params:{}", mqPipeline, Throwables.getStackTraceAsString(e)
                    , JSON.toJSONString(recallTaskInfo));
        }
    }

}
