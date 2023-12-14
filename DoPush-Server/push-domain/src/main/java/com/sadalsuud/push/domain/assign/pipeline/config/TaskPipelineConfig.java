package com.sadalsuud.push.domain.assign.pipeline.config;

import com.sadalsuud.push.common.pipeline.ProcessController;
import com.sadalsuud.push.common.pipeline.ProcessTemplate;
import com.sadalsuud.push.domain.assign.pipeline.action.DeduplicationAction;
import com.sadalsuud.push.domain.assign.pipeline.action.DiscardAction;
import com.sadalsuud.push.domain.assign.pipeline.action.SendMessageAction;
import com.sadalsuud.push.domain.assign.pipeline.action.ShieldAction;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description handler的pipeline配置类
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 11/12/2023
 * @Package com.sadalsuud.push.domain.pipeline.task.config
 */
@Configuration
@RequiredArgsConstructor
public class TaskPipelineConfig {
    public static final String PIPELINE_HANDLER_CODE = "handler";

    private final DiscardAction discardAction;
    private final ShieldAction shieldAction;
    private final DeduplicationAction deduplicationAction;
    private final SendMessageAction sendMessageAction;


    /**
     * 消息从MQ消费的流程
     * 0.丢弃消息
     * 1.屏蔽消息
     * 2.通用去重功能
     * 3.发送消息
     *
     * @return
     */
    @Bean("taskTemplate")
    public ProcessTemplate taskTemplate() {
        ProcessTemplate processTemplate = new ProcessTemplate();
        processTemplate.setProcessList(Arrays.asList(discardAction, shieldAction,
                //deduplicationAction,
                sendMessageAction));
        return processTemplate;
    }

    /**
     * pipeline流程控制器
     * 后续扩展则加BusinessCode和ProcessTemplate
     *
     * @return
     */
    @Bean("handlerProcessController")
    public ProcessController processController() {
        ProcessController processController = new ProcessController();
        Map<String, ProcessTemplate> templateConfig = new HashMap<>(4);
        templateConfig.put(PIPELINE_HANDLER_CODE, taskTemplate());
        processController.setTemplateConfig(templateConfig);
        return processController;
    }
}
