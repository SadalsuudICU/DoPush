package com.sadalsuud.push.domain.template.service.stateflow;

import com.sadalsuud.push.common.vo.BasicResultVO;
import com.sadalsuud.push.domain.template.MessageTemplate;
import com.sadalsuud.push.domain.template.repository.IMessageTemplateRepository;

import javax.annotation.Resource;

/**
 * @Description 模板状态抽象类
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/3/26
 * @Project DoPush-Server
 */
public abstract class AbstractState {

    @Resource
    protected IMessageTemplateRepository messageTemplateRepository;


    /**
     * 活动提审
     * @param id
     * @param messageTemplate
     * @return
     */
    public BasicResultVO arraignment(Long id, MessageTemplate messageTemplate){
        return BasicResultVO.fail("消息模板方法非法流转……");
    }

    /**
     * 审核通过
     * @param id
     * @param messageTemplate
     * @return
     */
    public BasicResultVO checkPass(Long id, MessageTemplate messageTemplate){
        return BasicResultVO.fail("消息模板方法非法流转……");
    }

    /**
     * 审核被拒
     * @param id
     * @param messageTemplate
     * @return
     */
    public BasicResultVO checkRefuse(Long id, MessageTemplate messageTemplate){
        return BasicResultVO.fail("消息模板方法非法流转……");
    }

    /**
     * 启用
     * @param id
     * @param messageTemplate
     * @return
     */
    public BasicResultVO start(Long id, MessageTemplate messageTemplate){
        return BasicResultVO.fail("消息模板方法非法流转……");
    }

    /**
     * 停用
     * @param id
     * @param messageTemplate
     * @return
     */
    public BasicResultVO stop(Long id, MessageTemplate messageTemplate){
        return BasicResultVO.fail("消息模板方法非法流转……");
    }

    /**
     * 等待发送
     * @param id
     * @param messageTemplate
     * @return
     */
    public BasicResultVO pending(Long id, MessageTemplate messageTemplate){
        return BasicResultVO.fail("消息模板方法非法流转……");
    }

    /**
     * 发送中
     * @param id
     * @param messageTemplate
     * @return
     */
    public BasicResultVO sending(Long id, MessageTemplate messageTemplate){
        return BasicResultVO.fail("消息模板方法非法流转……");
    }

    /**
     * 发送成功
     * @param id
     * @param messageTemplate
     * @return
     */
    public BasicResultVO sendSuccess(Long id, MessageTemplate messageTemplate){
        return BasicResultVO.fail("消息模板方法非法流转……");
    }

    /**
     * 发送失败
     * @param id
     * @param messageTemplate
     * @return
     */
    public BasicResultVO sendFailed(Long id, MessageTemplate messageTemplate) {
        return BasicResultVO.fail("消息模板方法非法流转……");
    }

}
