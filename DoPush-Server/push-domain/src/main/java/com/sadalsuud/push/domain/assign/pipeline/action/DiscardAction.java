package com.sadalsuud.push.domain.assign.pipeline.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sadalsuud.push.common.constant.CommonConstant;
import com.sadalsuud.push.common.domain.AnchorInfo;
import com.sadalsuud.push.common.domain.TaskInfo;
import com.sadalsuud.push.common.enums.AnchorState;
import com.sadalsuud.push.common.pipeline.BusinessProcess;
import com.sadalsuud.push.common.pipeline.ProcessContext;
import com.sadalsuud.push.domain.support.config.ConfigService;
import com.sadalsuud.push.domain.support.LogUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Description 丢弃消息 一般需要将要丢弃的模板id配置到分布式配置中心
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 11/12/2023
 * @Package com.sadalsuud.push.domain.pipeline.task.action
 */
@Service
@RequiredArgsConstructor
public class DiscardAction implements BusinessProcess<TaskInfo> {
    private static final String DISCARD_MESSAGE_KEY = "discardMsgIds";

    private final ConfigService config;
    private final LogUtils logUtils;

    @Override
    public void process(ProcessContext<TaskInfo> context) {
        TaskInfo taskInfo = context.getProcessModel();
        // 配置示例:	["1","2"]
        JSONArray array = JSON.parseArray(config.getProperty(DISCARD_MESSAGE_KEY, CommonConstant.EMPTY_VALUE_JSON_ARRAY));
        if (array.contains(String.valueOf(taskInfo.getMessageTemplateId()))) {
            logUtils.print(AnchorInfo.builder().bizId(taskInfo.getBizId()).messageId(taskInfo.getMessageId()).businessId(taskInfo.getBusinessId()).ids(taskInfo.getReceiver()).state(AnchorState.DISCARD.getCode()).build());
            context.setNeedBreak(true);
        }

    }
}
