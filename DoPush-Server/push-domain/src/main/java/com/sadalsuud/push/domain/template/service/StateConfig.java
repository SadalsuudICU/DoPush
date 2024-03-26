package com.sadalsuud.push.domain.template.service;

import com.sadalsuud.push.common.enums.AuditStatus;
import com.sadalsuud.push.common.enums.MessageStatus;
import com.sadalsuud.push.common.enums.PowerfulEnum;
import com.sadalsuud.push.domain.template.MessageTemplate;
import com.sadalsuud.push.domain.template.service.stateflow.AbstractState;
import com.sadalsuud.push.domain.template.service.stateflow.event.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/3/26
 * @Project DoPush-Server
 */
public class StateConfig {

    @Resource
    private InitiatedState initiatedState;

    @Resource
    private ArraignmentState arraignmentState;

    @Resource
    private CheckPassState checkPassState;


    @Resource
    private CheckRefuseState checkRefuseState;

    @Resource
    private PendingState pendingState;

    @Resource
    private SendingState sendingState;

    @Resource
    private StartState startState;

    @Resource
    private StopState stopState;

    protected Map<PowerfulEnum, AbstractState> stateGroup = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        stateGroup.put(AuditStatus.WAIT_COMMIT, initiatedState);
        stateGroup.put(AuditStatus.WAIT_AUDIT, arraignmentState);
        stateGroup.put(AuditStatus.AUDIT_SUCCESS, checkPassState);
        stateGroup.put(AuditStatus.AUDIT_REJECT, checkRefuseState);
        stateGroup.put(MessageStatus.RUN, startState);
        stateGroup.put(MessageStatus.PENDING, pendingState);
        stateGroup.put(MessageStatus.SENDING, sendingState);
        stateGroup.put(MessageStatus.STOP, stopState);
    }

    protected PowerfulEnum getCurrentState(MessageTemplate messageTemplate) {

        Integer msgStatus = messageTemplate.getMsgStatus();
        Integer auditStatus = messageTemplate.getAuditStatus();

        if (AuditStatus.AUDIT_REJECT.getCode().equals(auditStatus)) {
            return AuditStatus.AUDIT_REJECT;
        }

        if (msgStatus == 10 && auditStatus == 0) {
            return AuditStatus.findEnumByCode(msgStatus);
        }

        if (msgStatus == 10 && auditStatus < 30) {
            return AuditStatus.findEnumByCode(auditStatus);
        }

        return MessageStatus.findEnumByCode(msgStatus);
    }
}
