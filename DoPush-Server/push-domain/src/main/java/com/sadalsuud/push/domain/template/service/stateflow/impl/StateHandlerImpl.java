package com.sadalsuud.push.domain.template.service.stateflow.impl;

import com.sadalsuud.push.common.vo.BasicResultVO;
import com.sadalsuud.push.domain.template.MessageTemplate;
import com.sadalsuud.push.domain.template.service.StateConfig;
import com.sadalsuud.push.domain.template.service.stateflow.IStateHandler;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/3/26
 * @Project DoPush-Server
 */
public class StateHandlerImpl extends StateConfig implements IStateHandler {
    @Override
    public BasicResultVO arraignment(Long id, MessageTemplate messageTemplate) {
        return stateGroup.get(getCurrentState(messageTemplate)).arraignment(id, messageTemplate);
    }

    @Override
    public BasicResultVO checkPass(Long id, MessageTemplate messageTemplate) {
        return stateGroup.get(getCurrentState(messageTemplate)).checkPass(id, messageTemplate);
    }

    @Override
    public BasicResultVO checkRefuse(Long id, MessageTemplate messageTemplate) {
        return stateGroup.get(getCurrentState(messageTemplate)).checkRefuse(id, messageTemplate);
    }

    @Override
    public BasicResultVO start(Long id, MessageTemplate messageTemplate) {
        return stateGroup.get(getCurrentState(messageTemplate)).start(id, messageTemplate);
    }

    @Override
    public BasicResultVO stop(Long id, MessageTemplate messageTemplate) {
        return stateGroup.get(getCurrentState(messageTemplate)).stop(id, messageTemplate);
    }

    @Override
    public BasicResultVO pending(Long id, MessageTemplate messageTemplate) {
        return stateGroup.get(getCurrentState(messageTemplate)).pending(id, messageTemplate);
    }

    @Override
    public BasicResultVO sending(Long id, MessageTemplate messageTemplate) {
        return stateGroup.get(getCurrentState(messageTemplate)).sending(id, messageTemplate);
    }

    @Override
    public BasicResultVO sendSuccess(Long id, MessageTemplate messageTemplate) {
        return stateGroup.get(getCurrentState(messageTemplate)).sendSuccess(id, messageTemplate);
    }

    @Override
    public BasicResultVO sendFailed(Long id, MessageTemplate messageTemplate) {
        return stateGroup.get(getCurrentState(messageTemplate)).sendFailed(id, messageTemplate);
    }
}
