package com.sadalsuud.push.adapter.web;

import cn.hutool.core.text.CharSequenceUtil;
import com.sadalsuud.push.adapter.facade.annotation.DoPushAspect;
import com.sadalsuud.push.adapter.facade.annotation.DoPushResult;
import com.sadalsuud.push.client.api.DataService;
import com.sadalsuud.push.client.dto.DataParam;
import com.sadalsuud.push.client.vo.timeline.SmsTimeLineVo;
import com.sadalsuud.push.client.vo.timeline.UserTimeLineVo;
import com.sadalsuud.push.common.vo.BasicResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

/**
 * @Description 获取全链路追踪数据
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 17/12/2023
 * @Package com.sadalsuud.push.adapter.web
 */
@Slf4j
@DoPushAspect
@DoPushResult
@RestController
@RequestMapping("/trace")
@Api(tags = {"获取数据接口（全链路追踪)"})
@RequiredArgsConstructor
public class DataController {
    private final DataService dataService;

    @PostMapping("/message")
    @ApiOperation("/获取【72小时】发送消息的全链路数据")
    public UserTimeLineVo getMessageData(@RequestBody DataParam dataParam) {
        if (Objects.isNull(dataParam) || CharSequenceUtil.isBlank(dataParam.getMessageId())) {
            return UserTimeLineVo.builder().build();
        }
        return dataService.getTraceMessageInfo(dataParam.getMessageId());
    }

    @PostMapping("/user")
    @ApiOperation("/获取【当天】用户接收消息的全链路数据")
    public UserTimeLineVo getUserData(@RequestBody DataParam dataParam) {
        if (Objects.isNull(dataParam) || CharSequenceUtil.isBlank(dataParam.getReceiver())) {
            return UserTimeLineVo.builder().build();
        }
        return dataService.getTraceUserInfo(dataParam.getReceiver());
    }

    @PostMapping("/messageTemplate")
    @ApiOperation("/获取消息模板全链路数据")
    public BasicResultVO getMessageTemplateData(@RequestBody DataParam dataParam) {
        Map<String, Integer> res;
        System.out.println(dataParam);
        if (CharSequenceUtil.isNotBlank(dataParam.getBusinessId())) {
            res = dataService.getTraceMessageTemplateInfo(dataParam.getBusinessId());
            return BasicResultVO.success(res);
        }
        return BasicResultVO.fail("BusinessId involved");
    }

    @PostMapping("/sms")
    @ApiOperation("/获取短信下发数据")
    public SmsTimeLineVo getSmsData(@RequestBody DataParam dataParam) {
        if (Objects.isNull(dataParam) || Objects.isNull(dataParam.getDateTime()) || CharSequenceUtil.isBlank(dataParam.getReceiver())) {
            return SmsTimeLineVo.builder().build();
        }
        return dataService.getTraceSmsInfo(dataParam);
    }

}
