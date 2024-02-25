package com.sadalsuud.push.infrastructure.gatewayImpl.handler.script.impl;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaException;
import com.google.common.base.Throwables;
import com.sadalsuud.push.common.dto.account.sms.AliSmsAccount;
import com.sadalsuud.push.common.enums.SmsStatus;
import com.sadalsuud.push.infrastructure.gatewayImpl.handler.script.SmsScript;
import com.sadalsuud.push.domain.support.gateway.AccountService;
import com.sadalsuud.push.domain.support.gateway.domain.SmsRecord;
import com.sadalsuud.push.domain.assign.model.sms.SmsParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Description 阿里云短信脚本
 * https://next.api.aliyun.com/api-tools/sdk/Dysmsapi?version=2017-05-25&language=java-tea
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 15/12/2023
 * @Package com.sadalsuud.push.domain.assign.script.impl
 */
@Slf4j
@Component("AliSmsScript")
@RequiredArgsConstructor
public class AliSmsScript implements SmsScript {

    private final AccountService accountService;

    @Override
    public List<SmsRecord> send(SmsParam smsParam) {
        Integer accountId = smsParam.getSendAccountId();
        AliSmsAccount aliSmsAccount = Objects.nonNull(accountId) ?
                accountService.getAccountById(accountId, AliSmsAccount.class) :
                accountService.getSmsAccountByScriptName(smsParam.getScriptName(), AliSmsAccount.class);
        try {
            Client client = createClient(aliSmsAccount);
            List<SendSmsRequest> sendSmsRequests = assembleSendReq(smsParam, aliSmsAccount);
            return doSend(client, smsParam, sendSmsRequests, aliSmsAccount);
        } catch (Exception e) {
            log.error("AliSmsScript#send fail:{},params:{}", Throwables.getStackTraceAsString(e), JSON.toJSONString(smsParam));
            return new ArrayList<>();
        }
    }


    @Override
    public List<SmsRecord> pull(Integer id) {
        return new ArrayList<>();
    }


    /**
     * 使用AK&SK初始化账号Client
     *
     * @param aliSmsAccount
     * @return Client
     * @throws Exception
     */
    private Client createClient(AliSmsAccount aliSmsAccount) throws Exception {
        com.aliyun.teaopenapi.models.Config config =
                new com.aliyun.teaopenapi.models.Config()
                        .setAccessKeyId(aliSmsAccount.getAccessKeyId())
                        .setAccessKeySecret(aliSmsAccount.getAccessKeySecret());
        // Endpoint 请参考 https://api.aliyun.com/product/Dysmsapi
        config.endpoint = aliSmsAccount.getEndpoint();
        return new Client(config);
    }

    private List<SendSmsRequest> assembleSendReq(SmsParam smsParam, AliSmsAccount account) {
        ArrayList<SendSmsRequest> sendSmsRequests = new ArrayList<>();
        SendSmsRequest currentBizReq = getCurrentBizReq(smsParam, account);
        for (String phone : smsParam.getPhones()) {
            currentBizReq.setPhoneNumbers(phone);
            sendSmsRequests.add(currentBizReq);
        }
        return sendSmsRequests;
    }

    private SendSmsRequest getCurrentBizReq(SmsParam smsParam, AliSmsAccount account) {
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        sendSmsRequest.setTemplateCode(account.getTemplateCode());
        sendSmsRequest.setSignName(account.getSignName());
        //sendSmsRequest.setTemplateParam(smsParam.getContent());
        sendSmsRequest.setTemplateParam("{\"code\":\"1234\"}");
        return sendSmsRequest;
    }

    private List<SmsRecord> doSend(Client client, SmsParam smsParam, List<SendSmsRequest> sendSmsRequests, AliSmsAccount account) {
        ArrayList<SmsRecord> smsRecords = new ArrayList<>();
        for (SendSmsRequest sendSmsRequest : sendSmsRequests) {
            try {
                com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
                SendSmsResponse sendSmsResponse = client.sendSmsWithOptions(sendSmsRequest, runtime);
                System.out.println("sendSmsResponse.getBody().getBizId() = " + sendSmsResponse.getBody().getBizId());
                System.out.println("sendSmsResponse.getBody().getRequestId() = " + sendSmsResponse.getBody().getRequestId());
                SmsRecord smsRecord = SmsRecord.builder()
                        .sendDate(Integer.valueOf(DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN)))
                        .messageTemplateId(smsParam.getMessageTemplateId())
                        .phone(Long.valueOf(sendSmsRequest.phoneNumbers))
                        .supplierId(account.getSupplierId())
                        .supplierName(account.getSupplierName())
                        .msgContent(smsParam.getContent())
                        .seriesId(sendSmsResponse.getBody().getRequestId())
                        // 每次发送一个
                        .chargingNum(1)
                        .status(SmsStatus.SEND_SUCCESS.getCode())
                        .reportContent(sendSmsResponse.getBody().getCode())
                        .created(Math.toIntExact(DateUtil.currentSeconds()))
                        .updated(Math.toIntExact(DateUtil.currentSeconds()))
                        .build();
                smsRecords.add(smsRecord);
            } catch (TeaException error) {
                // 错误 message
                System.out.println(error.getMessage());
                // 诊断地址
                System.out.println(error.getData().get("Recommend"));
                com.aliyun.teautil.Common.assertAsString(error.message);
            } catch (Exception e) {
                // 发送信息未消费完全 需要通知或者记录下来
                log.error("AliSmsScript#send fail:{},params:{}", Throwables.getStackTraceAsString(e), JSON.toJSONString(smsParam));
            }
        }
        return smsRecords;
    }
}
