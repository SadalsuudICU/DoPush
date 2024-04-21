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
public class CheckRefuseState extends AbstractState {

    @Override
    public BasicResultVO checkRefuse(Long id, MessageTemplate messageTemplate) {
        boolean res = messageTemplateRepository
                .alertState(id,
                        AuditStatus.AUDIT_REJECT.getCode(), AuditStatus.WAIT_COMMIT.getCode(),
                        MessageStatus.INIT.getCode(), MessageStatus.INIT.getCode(),
                        messageTemplate.getUpdater(), Math.toIntExact(DateUtil.currentSeconds()));
        return res ? BasicResultVO.success() : BasicResultVO.fail("消息模板状态流转失败");
    }
}
