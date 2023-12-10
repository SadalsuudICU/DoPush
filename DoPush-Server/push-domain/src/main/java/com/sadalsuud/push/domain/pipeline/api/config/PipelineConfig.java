package com.sadalsuud.push.domain.pipeline.api.config;

import com.sadalsuud.push.common.pipeline.ProcessController;
import com.sadalsuud.push.common.pipeline.ProcessTemplate;
import com.sadalsuud.push.domain.pipeline.BusinessCode;
import com.sadalsuud.push.domain.pipeline.api.action.recall.RecallAssembleAction;
import com.sadalsuud.push.domain.pipeline.api.action.recall.RecallMqAction;
import com.sadalsuud.push.domain.pipeline.api.action.send.SendAfterCheckAction;
import com.sadalsuud.push.domain.pipeline.api.action.send.SendAssembleAction;
import com.sadalsuud.push.domain.pipeline.api.action.send.SendMqAction;
import com.sadalsuud.push.domain.pipeline.api.action.send.SendPreCheckAction;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description api层对应的pipeline配置类
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 * @Package com.sadalsuud.push.domain.pipeline.api.config
 */
@Configuration
@RequiredArgsConstructor
public class PipelineConfig {

    private final SendPreCheckAction sendPreCheckAction;
    private final SendAssembleAction sendAssembleAction;
    private final SendAfterCheckAction sendAfterCheckAction;
    private final SendMqAction sendMqAction;
    private final RecallAssembleAction recallAssembleAction;
    private final RecallMqAction recallMqAction;


    /**
     * 普通发送执行流程
     * 1. 前置参数校验
     * 2. 组装参数
     * 3. 后置参数校验
     * 4. 发送消息至MQ
     *
     * @return
     */
    @Bean("commonSendTemplate")
    public ProcessTemplate commonSendTemplate() {
        ProcessTemplate processTemplate = new ProcessTemplate();
        processTemplate.setProcessList(Arrays.asList(sendPreCheckAction, sendAssembleAction,
                sendAfterCheckAction, sendMqAction));
        return processTemplate;
    }

    /**
     * 消息撤回执行流程
     * 1.组装参数
     * 2.发送MQ
     *
     * @return
     */
    @Bean("recallMessageTemplate")
    public ProcessTemplate recallMessageTemplate() {
        ProcessTemplate processTemplate = new ProcessTemplate();
        processTemplate.setProcessList(Arrays.asList(recallAssembleAction, recallMqAction));
        return processTemplate;
    }

    /**
     * pipeline流程控制器
     * 后续扩展则加BusinessCode和ProcessTemplate
     *
     * @return
     */
    @Bean("apiProcessController")
    public ProcessController apiProcessController() {
        System.out.println("generate apiProcessController");
        ProcessController processController = new ProcessController();
        Map<String, ProcessTemplate> templateConfig = new HashMap<>(4);
        templateConfig.put(BusinessCode.COMMON_SEND.getCode(), commonSendTemplate());
        templateConfig.put(BusinessCode.RECALL.getCode(), recallMessageTemplate());
        processController.setTemplateConfig(templateConfig);
        return processController;
    }

}
