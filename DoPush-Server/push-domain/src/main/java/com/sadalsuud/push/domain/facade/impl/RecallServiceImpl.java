package com.sadalsuud.push.domain.facade.impl;

import com.sadalsuud.push.common.enums.RespStatusEnum;
import com.sadalsuud.push.common.pipeline.ProcessContext;
import com.sadalsuud.push.common.pipeline.ProcessController;
import com.sadalsuud.push.common.vo.BasicResultVO;
import com.sadalsuud.push.domain.facade.RecallService;
import com.sadalsuud.push.domain.receipt.RecallTaskModel;
import com.sadalsuud.push.domain.receipt.SendRequest;
import com.sadalsuud.push.domain.receipt.SendResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 14/12/2023
 * @Package com.sadalsuud.push.domain.facade.impl
 */
@Service
public class RecallServiceImpl implements RecallService {
    @Resource(name = "apiProcessController")
    private ProcessController processController;

    @Override
    public SendResponse recall(SendRequest sendRequest) {

        if (ObjectUtils.isEmpty(sendRequest)) {
            return new SendResponse(RespStatusEnum.CLIENT_BAD_PARAMETERS.getCode(), RespStatusEnum.CLIENT_BAD_PARAMETERS.getMsg(), null);
        }
        RecallTaskModel recallTaskModel = RecallTaskModel.builder().messageTemplateId(sendRequest.getMessageTemplateId()).recallMessageId(sendRequest.getRecallMessageIds()).build();
        ProcessContext context = ProcessContext.builder().code(sendRequest.getCode()).processModel(recallTaskModel).needBreak(false).response(BasicResultVO.success()).build();

        ProcessContext process = processController.process(context);
        return new SendResponse(process.getResponse().getStatus(), process.getResponse().getMsg(), null);
    }
}

