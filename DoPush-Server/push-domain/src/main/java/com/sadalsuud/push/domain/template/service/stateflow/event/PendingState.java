package com.sadalsuud.push.domain.template.service.stateflow.event;

import cn.hutool.core.date.DateUtil;
import com.sadalsuud.push.common.enums.AuditStatus;
import com.sadalsuud.push.common.enums.MessageStatus;
import com.sadalsuud.push.common.vo.BasicResultVO;
import com.sadalsuud.push.domain.template.MessageTemplate;
import com.sadalsuud.push.domain.template.service.stateflow.AbstractState;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/3/26
 * @Project DoPush-Server
 */
@Component
public class PendingState extends AbstractState {
    @Override
    public BasicResultVO sendFailed(Long id, MessageTemplate messageTemplate) {
        boolean res = messageTemplateRepository
                .alertState(id,
                        AuditStatus.AUDIT_SUCCESS.getCode(), AuditStatus.AUDIT_SUCCESS.getCode(),
                        MessageStatus.PENDING.getCode(), MessageStatus.SEND_FAIL.getCode(),
                        messageTemplate.getUpdater(), Math.toIntExact(DateUtil.currentSeconds()));
        return res ? BasicResultVO.success() : BasicResultVO.fail(MSG);
    }

    @Override
    public BasicResultVO pending(Long id, MessageTemplate messageTemplate) {
        boolean res = messageTemplateRepository
                .alertState(id,
                        AuditStatus.AUDIT_SUCCESS.getCode(), AuditStatus.AUDIT_SUCCESS.getCode(),
                        MessageStatus.PENDING.getCode(), MessageStatus.SENDING.getCode(),
                        messageTemplate.getUpdater(), Math.toIntExact(DateUtil.currentSeconds()));
        return res ? BasicResultVO.success() : BasicResultVO.fail(MSG);
    }
}
