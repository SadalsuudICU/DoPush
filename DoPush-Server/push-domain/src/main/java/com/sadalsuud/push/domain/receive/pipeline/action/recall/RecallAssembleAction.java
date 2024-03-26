package com.sadalsuud.push.domain.receive.pipeline.action.recall;

import com.google.common.base.Throwables;
import com.sadalsuud.push.common.constant.CommonConstant;
import com.sadalsuud.push.common.domain.RecallTaskInfo;
import com.sadalsuud.push.common.enums.RespStatusEnum;
import com.sadalsuud.push.common.pipeline.BusinessProcess;
import com.sadalsuud.push.common.pipeline.ProcessContext;
import com.sadalsuud.push.common.vo.BasicResultVO;
import com.sadalsuud.push.domain.template.MessageTemplate;
import com.sadalsuud.push.domain.receive.RecallTaskModel;
import com.sadalsuud.push.domain.template.repository.IMessageTemplateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Description 组装撤回参数
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 * @Package com.sadalsuud.push.domain.pipeline.api.action.recall
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RecallAssembleAction implements BusinessProcess<RecallTaskModel> {

    private final IMessageTemplateRepository messageTemplateRepository;

    @Override
    public void process(ProcessContext<RecallTaskModel> context) {
        RecallTaskModel recallTaskModel = context.getProcessModel();
        Long messageTemplateId = recallTaskModel.getMessageTemplateId();
        try {
            Optional<MessageTemplate> messageTemplate = messageTemplateRepository.findById(messageTemplateId);
            if (!messageTemplate.isPresent() || CommonConstant.TRUE.equals(messageTemplate.get().getIsDeleted())) {
                context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.TEMPLATE_NOT_FOUND));
                return;
            }

            RecallTaskInfo recallTaskInfo = RecallTaskInfo.builder().messageTemplateId(messageTemplateId)
                    .recallMessageId(recallTaskModel.getRecallMessageId())
                    .sendAccount(messageTemplate.get().getSendAccount())
                    .sendChannel(messageTemplate.get().getSendChannel())
                    .build();
            recallTaskModel.setRecallTaskInfo(recallTaskInfo);

        } catch (Exception e) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.SERVICE_ERROR));
            log.error("assemble recall task fail! templateId:{}, e:{}", messageTemplateId, Throwables.getStackTraceAsString(e));
        }
    }

}
