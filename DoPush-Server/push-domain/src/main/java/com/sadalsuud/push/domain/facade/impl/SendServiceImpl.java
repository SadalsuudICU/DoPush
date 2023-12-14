package com.sadalsuud.push.domain.facade.impl;

import cn.monitor4all.logRecord.annotation.OperationLog;
import com.sadalsuud.push.common.domain.SimpleTaskInfo;
import com.sadalsuud.push.common.enums.RespStatusEnum;
import com.sadalsuud.push.common.pipeline.ProcessContext;
import com.sadalsuud.push.common.pipeline.ProcessController;
import com.sadalsuud.push.common.vo.BasicResultVO;
import com.sadalsuud.push.domain.facade.SendService;
import com.sadalsuud.push.domain.receipt.SendRequest;
import com.sadalsuud.push.domain.receipt.SendResponse;
import com.sadalsuud.push.domain.receipt.SendTaskModel;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 * @Package com.sadalsuud.push.domain.facade
 */
@Service
public class SendServiceImpl implements SendService {

    @Resource(name = "apiProcessController")
    private ProcessController processController;

    @Override
    @OperationLog(bizType = "SendService#send", bizId = "#sendRequest.messageTemplateId", msg = "#sendRequest")
    @SuppressWarnings("unchecked")
    public SendResponse send(SendRequest sendRequest) {
        if (ObjectUtils.isEmpty(sendRequest)) {
            System.out.println("sendRequest is empty");
            return new SendResponse(RespStatusEnum.CLIENT_BAD_PARAMETERS.getCode(), RespStatusEnum.CLIENT_BAD_PARAMETERS.getMsg(), null);
        }

        SendTaskModel sendTaskModel = SendTaskModel.builder()
                .messageTemplateId(sendRequest.getMessageTemplateId())
                .messageParamList(Collections.singletonList(sendRequest.getMessageParam()))
                .build();

        ProcessContext context = ProcessContext.builder()
                .code(sendRequest.getCode())
                .processModel(sendTaskModel)
                .needBreak(false)
                .response(BasicResultVO.success()).build();

        ProcessContext process = processController.process(context);

        return new SendResponse(process.getResponse().getStatus(), process.getResponse().getMsg(), (List<SimpleTaskInfo>) process.getResponse().getData());
    }

    //@Override
    //@OperationLog(bizType = "SendService#batchSend", bizId = "#batchSendRequest.messageTemplateId", msg = "#batchSendRequest")
    //public SendResponse batchSend(BatchSendRequest batchSendRequest) {
    //    if (ObjectUtils.isEmpty(batchSendRequest)) {
    //        return new SendResponse(RespStatusEnum.CLIENT_BAD_PARAMETERS.getCode(), RespStatusEnum.CLIENT_BAD_PARAMETERS.getMsg(), null);
    //    }
    //
    //    SendTaskModel sendTaskModel = SendTaskModel.builder()
    //            .messageTemplateId(batchSendRequest.getMessageTemplateId())
    //            .messageParamList(batchSendRequest.getMessageParamList())
    //            .build();
    //
    //    ProcessContext context = ProcessContext.builder()
    //            .code(batchSendRequest.getCode())
    //            .processModel(sendTaskModel)
    //            .needBreak(false)
    //            .response(BasicResultVO.success()).build();
    //
    //    ProcessContext process = processController.process(context);
    //
    //    return new SendResponse(process.getResponse().getStatus(), process.getResponse().getMsg(), (List<SimpleTaskInfo>) process.getResponse().getData());
    //}


}
