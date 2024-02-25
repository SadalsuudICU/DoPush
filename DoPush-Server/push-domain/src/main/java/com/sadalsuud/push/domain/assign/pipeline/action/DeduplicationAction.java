package com.sadalsuud.push.domain.assign.pipeline.action;

import cn.hutool.core.collection.CollUtil;
import com.sadalsuud.push.common.constant.CommonConstant;
import com.sadalsuud.push.common.domain.TaskInfo;
import com.sadalsuud.push.common.enums.DeduplicationType;
import com.sadalsuud.push.common.enums.EnumUtil;
import com.sadalsuud.push.common.pipeline.BusinessProcess;
import com.sadalsuud.push.common.pipeline.ProcessContext;
import com.sadalsuud.push.domain.support.gateway.ConfigService;
import com.sadalsuud.push.domain.assign.deduplication.DeduplicationHolder;
import com.sadalsuud.push.domain.assign.deduplication.DeduplicationParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Description 去重服务
 * 1. 根据相同内容N分钟去重（SlideWindowLimitService）
 * 2. 相同的渠道一天内频次去重（SimpleLimitService）
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 11/12/2023
 * @Package com.sadalsuud.push.domain.pipeline.task.action
 */
@Service
@RequiredArgsConstructor
public class DeduplicationAction implements BusinessProcess<TaskInfo> {

    public static final String DEDUPLICATION_RULE_KEY = "deduplicationRule";

    private final ConfigService config;

    private final DeduplicationHolder deduplicationHolder;

    @Override
    public void process(ProcessContext<TaskInfo> context) {
        TaskInfo taskInfo = context.getProcessModel();

        // 配置样例{"deduplication_10":{"num":1,"time":300},"deduplication_20":{"num":5}}
        String deduplicationConfig = config.getProperty(DEDUPLICATION_RULE_KEY, CommonConstant.EMPTY_JSON_OBJECT);

        // 去重
        List<Integer> deduplicationList = EnumUtil.getCodeList(DeduplicationType.class);
        for (Integer deduplicationType : deduplicationList) {
            DeduplicationParam deduplicationParam = deduplicationHolder.selectBuilder(deduplicationType).build(deduplicationConfig, taskInfo);
            if (Objects.nonNull(deduplicationParam)) {
                deduplicationHolder.selectService(deduplicationType).deduplication(deduplicationParam);
            }
        }

        if (CollUtil.isEmpty(taskInfo.getReceiver())) {
            context.setNeedBreak(true);
        }
    }
}
