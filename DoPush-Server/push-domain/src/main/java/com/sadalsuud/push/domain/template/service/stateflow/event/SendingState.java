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
public class SendingState extends AbstractState {

    @Override
    public BasicResultVO sendFailed(Long id, MessageTemplate messageTemplate) {
        boolean res = messageTemplateRepository
                .alertState(id,
                        AuditStatus.AUDIT_SUCCESS.getCode(), AuditStatus.AUDIT_SUCCESS.getCode(),
                        MessageStatus.SENDING.getCode(), MessageStatus.SEND_FAIL.getCode(),
                        messageTemplate.getUpdater(), Math.toIntExact(DateUtil.currentSeconds()));
        return res ? BasicResultVO.success() : BasicResultVO.fail("消息模板状态流转失败");
    }


    @Override
    public BasicResultVO sending(Long id, MessageTemplate messageTemplate) {
        boolean res = messageTemplateRepository
                .alertState(id,
                        AuditStatus.AUDIT_SUCCESS.getCode(), AuditStatus.AUDIT_SUCCESS.getCode(),
                        MessageStatus.SENDING.getCode(), MessageStatus.SEND_SUCCESS.getCode(),
                        messageTemplate.getUpdater(), Math.toIntExact(DateUtil.currentSeconds()));
        return res ? BasicResultVO.success() : BasicResultVO.fail("消息模板状态流转失败");
    }
}
