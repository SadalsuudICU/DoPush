package com.sadalsuud.push.domain.template.service.stateflow;

import com.sadalsuud.push.common.vo.BasicResultVO;
import com.sadalsuud.push.domain.template.MessageTemplate;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/3/26
 * @Project DoPush-Server
 */
public interface IStateHandler {

    /**
     * 活动提审
     * @param id
     * @param messageTemplate
     * @return
     */
    BasicResultVO arraignment(Long id, MessageTemplate messageTemplate);

    /**
     * 审核通过
     * @param id
     * @param messageTemplate
     * @return
     */
    BasicResultVO checkPass(Long id, MessageTemplate messageTemplate);

    /**
     * 审核被拒
     * @param id
     * @param messageTemplate
     * @return
     */
    BasicResultVO checkRefuse(Long id, MessageTemplate messageTemplate);

    /**
     * 启用
     * @param id
     * @param messageTemplate
     * @return
     */
    BasicResultVO start(Long id, MessageTemplate messageTemplate);

    /**
     * 停用
     * @param id
     * @param messageTemplate
     * @return
     */
    BasicResultVO stop(Long id, MessageTemplate messageTemplate);

    /**
     * 等待发送
     * @param id
     * @param messageTemplate
     * @return
     */
    BasicResultVO pending(Long id, MessageTemplate messageTemplate);

    /**
     * 发送中
     * @param id
     * @param messageTemplate
     * @return
     */
    BasicResultVO sending(Long id, MessageTemplate messageTemplate);

    /**
     * 发送成功
     * @param id
     * @param messageTemplate
     * @return
     */
    BasicResultVO sendSuccess(Long id, MessageTemplate messageTemplate);

    /**
     * 发送失败
     * @param id
     * @param messageTemplate
     * @return
     */
    BasicResultVO sendFailed(Long id, MessageTemplate messageTemplate);
}
