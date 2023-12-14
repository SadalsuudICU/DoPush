package com.sadalsuud.push.domain;

import com.sadalsuud.push.common.pipeline.BusinessProcess;
import com.sadalsuud.push.common.pipeline.ProcessController;
import com.sadalsuud.push.common.pipeline.ProcessTemplate;
import com.sadalsuud.push.domain.facade.impl.SendServiceImpl;
import com.sadalsuud.push.domain.receipt.BusinessCode;
import com.sadalsuud.push.domain.receipt.MessageParam;
import com.sadalsuud.push.domain.receipt.SendRequest;
import com.sadalsuud.push.domain.receipt.SendResponse;
import com.sadalsuud.push.domain.receipt.pipeline.action.send.SendAfterCheckAction;
import com.sadalsuud.push.domain.receipt.pipeline.action.send.SendAssembleAction;
import com.sadalsuud.push.domain.receipt.pipeline.action.send.SendMqAction;
import com.sadalsuud.push.domain.receipt.pipeline.action.send.SendPreCheckAction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 * @Package com.sadalsuud.push.domain
 */
@ExtendWith(MockitoExtension.class)
public class SendServiceTest {
    @Spy
    private ProcessController processController;

    //@Mock
    @Mock
    private Map<String, ProcessTemplate> templateConfig;

    @Spy
    private ProcessTemplate processTemplate;

    @Mock
    private BusinessProcess businessProcess;

    @InjectMocks
    private SendServiceImpl sendServiceImplUnderTest;

    @Spy
    private SendPreCheckAction sendPreCheckAction;
    @Spy
    private SendAssembleAction sendAssembleAction;
    @Spy
    private SendAfterCheckAction sendAfterCheckAction;
    @Spy
    private SendMqAction sendMqAction;

    @Test
    void testSend() {

        // params
        final SendRequest sendRequest = new SendRequest("send", 1L,
                new MessageParam("12314", "13711111111", new HashMap<>(), new HashMap<>()),
                Arrays.asList("1", "2"));

        // predict result
        //final ProcessContext<SendTaskModel> processContext = new ProcessContext<>(sendRequest.getCode(), new SendTaskModel(), false, new BasicResultVO<>(
        //        RespStatusEnum.SUCCESS, "data"));
        //final SendResponse expectedResult = new SendResponse(processContext.getResponse().getStatus(), processContext.getResponse().getMsg(), (List<SimpleTaskInfo>) processContext.getResponse().getData());

        // stub
        Map<String, ProcessTemplate> templateConfig = new HashMap<>(4);
        processTemplate.setProcessList(Arrays.asList(sendPreCheckAction, sendAssembleAction,
                sendAfterCheckAction, sendMqAction));
        templateConfig.put(BusinessCode.COMMON_SEND.getCode(), processTemplate);

        processController.setTemplateConfig(templateConfig);


        // Run the test
        final SendResponse result = sendServiceImplUnderTest.send(sendRequest);
        System.out.println(result);

        // Verify the results
        //assertEquals(expectedResult, result);
    }
}
