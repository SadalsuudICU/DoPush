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
public class InitiatedState extends AbstractState {
    @Override
    public BasicResultVO arraignment(Long id, MessageTemplate messageTemplate) {
        boolean res = messageTemplateRepository
                .alertState(id,
                        AuditStatus.WAIT_COMMIT.getCode(), AuditStatus.WAIT_AUDIT.getCode(),
                        MessageStatus.INIT.getCode(), MessageStatus.INIT.getCode(),
                        messageTemplate.getUpdater(), Math.toIntExact(DateUtil.currentSeconds()));
        return res ? BasicResultVO.success() : BasicResultVO.fail(MSG);
    }
}
