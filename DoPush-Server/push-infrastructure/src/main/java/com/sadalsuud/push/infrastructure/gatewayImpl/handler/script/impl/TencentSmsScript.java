package com.sadalsuud.push.infrastructure.gatewayImpl.handler.script.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import com.sadalsuud.push.common.dto.account.sms.TencentSmsAccount;
import com.sadalsuud.push.common.enums.SmsStatus;
import com.sadalsuud.push.infrastructure.gatewayImpl.handler.script.SmsScript;
import com.sadalsuud.push.domain.gateway.AccountGateway;
import com.sadalsuud.push.domain.gateway.domain.SmsRecord;
import com.sadalsuud.push.domain.assign.model.sms.SmsParam;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Description 腾讯云短信脚本
 * 1. 发送短信接入文档：https://cloud.tencent.com/document/api/382/55981
 * 2. 推荐直接使用SDK调用
 * 3. 推荐使用API Explorer 生成代码
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 15/12/2023
 * @Package com.sadalsuud.push.domain.assign.script.impl
 */
@Slf4j
@Component("TencentSmsScript")
@RequiredArgsConstructor
public class TencentSmsScript implements SmsScript {
    /**
     * 号码位数, 用于解析发送返回信息的条目, 避免使用魔法值
     */
    private static final Integer PHONE_NUM = 11;

    private final AccountGateway accountGateway;

    @Override
    public List<SmsRecord> send(SmsParam smsParam) {
        try {
            Integer accountId = smsParam.getSendAccountId();
            TencentSmsAccount account = Objects.nonNull(accountId) ?
                    accountGateway.getAccountById(accountId, TencentSmsAccount.class) :
                    accountGateway.getSmsAccountByScriptName(smsParam.getScriptName(), TencentSmsAccount.class);
            SmsClient client = init(account);
            SendSmsRequest sendSmsRequest = assembleSendReq(smsParam, account);
            SendSmsResponse sendSmsResponse = client.SendSms(sendSmsRequest);
            return assembleSendSmsRecord(smsParam, sendSmsResponse, account);
        } catch (Exception e) {
            log.error("TencentSmsScript#send fail:{},params:{}", Throwables.getStackTraceAsString(e), JSON.toJSONString(smsParam));
            return new ArrayList<>();
        }
    }

    @Override
    public List<SmsRecord> pull(Integer id) {
        try {
            TencentSmsAccount account = accountGateway.getAccountById(id, TencentSmsAccount.class);
            SmsClient client = init(account);
            PullSmsSendStatusRequest pullRequest = assemblePullReq(account);
            PullSmsSendStatusResponse pullResponse = client.PullSmsSendStatus(pullRequest);
            return assemblePullSmsRecord(account, pullResponse);
        } catch (Exception e) {
            log.error("TencentSmsReceipt#pull fail!{}", Throwables.getStackTraceAsString(e));
            return new ArrayList<>();
        }
    }


    /**
     * 初始化客户端
     *
     * @param account 渠道账号
     * @return 短信客户端
     */
    private SmsClient init(TencentSmsAccount account) {
        Credential cred = new Credential(account.getSecretId(), account.getSecretKey());
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(account.getUrl());
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        return new SmsClient(cred, account.getRegion(), clientProfile);
    }


    /**
     * 组装发送短信参数
     *
     * @param smsParam 消息本体
     * @param account  渠道账号
     * @return 发送的请求参数实体
     */
    private SendSmsRequest assembleSendReq(SmsParam smsParam, TencentSmsAccount account) {
        SendSmsRequest req = new SendSmsRequest();
        String[] phoneNumberSet1 = smsParam.getPhones().toArray(new String[smsParam.getPhones().size() - 1]);
        req.setPhoneNumberSet(phoneNumberSet1);
        req.setSmsSdkAppId(account.getSmsSdkAppId());
        req.setSignName(account.getSignName());
        req.setTemplateId(account.getTemplateId());
        String[] templateParamSet1 = {smsParam.getContent()};
        req.setTemplateParamSet(templateParamSet1);
        req.setSessionContext(IdUtil.fastSimpleUUID());
        return req;
    }

    /**
     * 组装 发送消息的返回值
     *
     * @param smsParam          消息本体
     * @param response          SDK返回的结果
     * @param tencentSmsAccount 渠道账号
     * @return 发送成功 记录数
     */
    private List<SmsRecord> assembleSendSmsRecord(SmsParam smsParam, SendSmsResponse response, TencentSmsAccount tencentSmsAccount) {

        List<SmsRecord> smsRecordList = new ArrayList<>();
        if (Objects.isNull(response) || ArrayUtil.isEmpty(response.getSendStatusSet())) {
            return smsRecordList;
        }

        for (SendStatus sendStatus : response.getSendStatusSet()) {

            // 腾讯返回的电话号有前缀，这里取巧直接翻转获取手机号
            String phone = new StringBuilder(new StringBuilder(sendStatus.getPhoneNumber())
                    .reverse().substring(0, PHONE_NUM)).reverse().toString();

            SmsRecord smsRecord = SmsRecord.builder()
                    .sendDate(Integer.valueOf(DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN)))
                    .messageTemplateId(smsParam.getMessageTemplateId())
                    .phone(Long.valueOf(phone))
                    .supplierId(tencentSmsAccount.getSupplierId())
                    .supplierName(tencentSmsAccount.getSupplierName())
                    .msgContent(smsParam.getContent())
                    .seriesId(sendStatus.getSerialNo())
                    .chargingNum(Math.toIntExact(sendStatus.getFee()))
                    .status(SmsStatus.SEND_SUCCESS.getCode())
                    .reportContent(sendStatus.getCode())
                    .created(Math.toIntExact(DateUtil.currentSeconds()))
                    .updated(Math.toIntExact(DateUtil.currentSeconds()))
                    .build();

            smsRecordList.add(smsRecord);
        }
        return smsRecordList;
    }


    /**
     * 组装拉取回执的入参
     *
     * @param account 渠道账号
     * @return 拉去回执的请求参数实体
     */
    private PullSmsSendStatusRequest assemblePullReq(TencentSmsAccount account) {
        PullSmsSendStatusRequest req = new PullSmsSendStatusRequest();
        req.setLimit(10L);
        req.setSmsSdkAppId(account.getSmsSdkAppId());
        return req;
    }

    /**
     * 组装 拉取回执信息
     *
     * @param account 渠道账号
     * @param resp    回执
     * @return 回执记录数
     */
    private List<SmsRecord> assemblePullSmsRecord(TencentSmsAccount account, PullSmsSendStatusResponse resp) {
        List<SmsRecord> smsRecordList = new ArrayList<>();
        if (Objects.nonNull(resp) && Objects.nonNull(resp.getPullSmsSendStatusSet())) {
            resp.getPullSmsSendStatusSet();
            for (PullSmsSendStatus pullSmsSendStatus : resp.getPullSmsSendStatusSet()) {
                SmsRecord smsRecord = SmsRecord.builder()
                        .sendDate(Integer.valueOf(DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN)))
                        .messageTemplateId(0L)
                        .phone(Long.valueOf(pullSmsSendStatus.getSubscriberNumber()))
                        .supplierId(account.getSupplierId())
                        .supplierName(account.getSupplierName())
                        .msgContent("")
                        .seriesId(pullSmsSendStatus.getSerialNo())
                        .chargingNum(0)
                        .status("SUCCESS".equals(pullSmsSendStatus.getReportStatus()) ? SmsStatus.RECEIVE_SUCCESS.getCode() : SmsStatus.RECEIVE_FAIL.getCode())
                        .reportContent(pullSmsSendStatus.getDescription())
                        .updated(Math.toIntExact(pullSmsSendStatus.getUserReceiveTime()))
                        .created(Math.toIntExact(DateUtil.currentSeconds()))
                        .build();
                smsRecordList.add(smsRecord);
            }
        }
        return smsRecordList;
    }

}
