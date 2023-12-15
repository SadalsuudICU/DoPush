package com.sadalsuud.push.domain.facade;

import com.sadalsuud.push.domain.receive.SendRequest;
import com.sadalsuud.push.domain.receive.SendResponse;

/**
 * @Description 撤回接口
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 14/12/2023
 * @Package com.sadalsuud.push.domain.facade
 */
public interface RecallService {


    /**
     * 根据 模板ID 或消息id 撤回消息
     * 如果只传入 messageTemplateId，则会撤回整个模板下发的消息
     * 如果还传入 recallMessageId，则优先撤回该 ids 的消息
     *
     * @param sendRequest
     * @return
     */
    SendResponse recall(SendRequest sendRequest);
}
