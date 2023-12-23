package com.sadalsuud.push.adapter.web;

import cn.hutool.core.text.CharSequenceUtil;
import com.sadalsuud.push.adapter.facade.annotation.DoPushAspect;
import com.sadalsuud.push.adapter.facade.annotation.DoPushResult;
import com.sadalsuud.push.client.api.DataService;
import com.sadalsuud.push.client.vo.DataParam;
import com.sadalsuud.push.common.domain.SimpleAnchorInfo;
import com.sadalsuud.push.domain.gateway.domain.SmsRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
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
    public Map<String, List<SimpleAnchorInfo>> getMessageData(@RequestBody DataParam dataParam) {
        if (Objects.isNull(dataParam) || CharSequenceUtil.isBlank(dataParam.getMessageId())) {
            return new HashMap<>();
        }
        return dataService.getTraceMessageInfo(dataParam.getMessageId());
    }

    @PostMapping("/user")
    @ApiOperation("/获取【当天】用户接收消息的全链路数据")
    public Map<String, List<SimpleAnchorInfo>> getUserData(@RequestBody DataParam dataParam) {
        if (Objects.isNull(dataParam) || CharSequenceUtil.isBlank(dataParam.getReceiver())) {
            return new HashMap<>();
        }
        return dataService.getTraceUserInfo(dataParam.getReceiver());
    }

    @PostMapping("/messageTemplate")
    @ApiOperation("/获取消息模板全链路数据")
    public Map<Object, Object> getMessageTemplateData(@RequestBody DataParam dataParam) {
        Map<Object, Object> res = new HashMap<>();
        if (CharSequenceUtil.isNotBlank(dataParam.getBusinessId())) {
            res = dataService.getTraceMessageTemplateInfo(dataParam.getBusinessId());
        }
        return res;
    }

    @PostMapping("/sms")
    @ApiOperation("/获取短信下发数据")
    public Map<String, List<SmsRecord>> getSmsData(@RequestBody DataParam dataParam) {
        if (Objects.isNull(dataParam) || Objects.isNull(dataParam.getDateTime()) || CharSequenceUtil.isBlank(dataParam.getReceiver())) {
            return new HashMap<>();
        }
        return dataService.getTraceSmsInfo(dataParam);
    }

}
