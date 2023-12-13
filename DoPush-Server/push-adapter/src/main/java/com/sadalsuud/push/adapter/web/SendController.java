package com.sadalsuud.push.adapter.web;

import com.sadalsuud.push.adapter.facade.annotation.DoPushAspect;
import com.sadalsuud.push.domain.facade.SendService;
import com.sadalsuud.push.domain.assign.SendRequest;
import com.sadalsuud.push.domain.assign.SendResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 13/12/2023
 * @Package com.sadalsuud.push.adapter.web
 */
@Api(tags = {"发送消息"})
@RestController
@DoPushAspect
@RequiredArgsConstructor
public class SendController {

    private final SendService sendService;

    //private final RecallService recallService;


    /**
     * 单个文案下发相同的人
     *
     * @param sendRequest
     * @return
     */
    @ApiOperation(value = "下发接口", notes = "多渠道多类型下发消息，目前支持邮件和短信，类型支持：验证码、通知类、营销类。")
    @PostMapping("/send")
    public SendResponse send(@RequestBody SendRequest sendRequest) {
        return sendService.send(sendRequest);
    }

    ///**
    // * 不同文案下发到不同的人
    // *
    // * @param batchSendRequest
    // * @return
    // */
    //@ApiOperation(value = "batch下发接口", notes = "多渠道多类型下发消息，目前支持邮件和短信，类型支持：验证码、通知类、营销类。")
    //@PostMapping("/batchSend")
    //public SendResponse batchSend(@RequestBody BatchSendRequest batchSendRequest) {
    //    return sendService.batchSend(batchSendRequest);
    //}
    //
    ///**
    // * 优先根据messageId撤回消息，如果messageId不存在则根据模板id撤回
    // *
    // * @param sendRequest
    // * @return
    // */
    //@ApiOperation(value = "撤回消息接口", notes = "优先根据messageId撤回消息，如果messageId不存在则根据模板id撤回")
    //@PostMapping("/recall")
    //public SendResponse recall(@RequestBody SendRequest sendRequest) {
    //    return recallService.recall(sendRequest);
    //}
}
