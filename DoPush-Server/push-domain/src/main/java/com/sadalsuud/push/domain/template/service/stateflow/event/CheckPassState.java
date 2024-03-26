package com.sadalsuud.push.domain.template.service.stateflow.event;

import cn.hutool.core.date.DateUtil;
import com.sadalsuud.push.common.enums.AuditStatus;
import com.sadalsuud.push.common.enums.MessageStatus;
import com.sadalsuud.push.common.vo.BasicResultVO;
import com.sadalsuud.push.domain.template.MessageTemplate;
import com.sadalsuud.push.domain.template.service.stateflow.AbstractState;

/**
 * @Description 审核通过
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/3/26
 * @Project DoPush-Server
 */
public class CheckPassState extends AbstractState {
    @Override
    public BasicResultVO start(Long id, MessageTemplate messageTemplate) {
        boolean res = messageTemplateRepository
                .alertState(id,
                        AuditStatus.AUDIT_SUCCESS.getCode(), AuditStatus.AUDIT_SUCCESS.getCode(),
                        MessageStatus.INIT.getCode(), MessageStatus.RUN.getCode(),
                        messageTemplate.getUpdater(), Math.toIntExact(DateUtil.currentSeconds()));
        return res ? BasicResultVO.success() : BasicResultVO.fail("消息模板状态流转失败");
    }
}
