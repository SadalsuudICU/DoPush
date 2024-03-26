package com.sadalsuud.push.infrastructure.gatewayImpl.handler.receipt.starter.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import com.sadalsuud.push.common.constant.CommonConstant;
import com.sadalsuud.push.common.dto.account.sms.SmsAccount;
import com.sadalsuud.push.common.enums.ChannelType;
import com.sadalsuud.push.domain.channel.ChannelAccount;
import com.sadalsuud.push.domain.data.SmsRecord;
import com.sadalsuud.push.infrastructure.gatewayImpl.handler.receipt.starter.ReceiptMessageStater;
import com.sadalsuud.push.infrastructure.gatewayImpl.handler.script.SmsScript;
import com.sadalsuud.push.infrastructure.gatewayImpl.repository.ChannelAccountDao;
import com.sadalsuud.push.infrastructure.gatewayImpl.repository.SmsRecordDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description 拉取短信回执信息
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/3/26
 * @Project DoPush-Server
 */
@Slf4j
@Component
public class SmsPullReceiptStarterImpl implements ReceiptMessageStater {

    @Autowired
    private ChannelAccountDao channelAccountDao;

    @Autowired
    private Map<String, SmsScript> scriptMap;

    @Autowired
    private SmsRecordDao smsRecordDao;

    @Override
    public void start() {
        try {
            List<ChannelAccount> channelAccountList = channelAccountDao.findAllByIsDeletedEqualsAndSendChannelEquals(CommonConstant.FALSE, ChannelType.SMS.getCode());
            for (ChannelAccount channelAccount : channelAccountList) {
                SmsAccount smsAccount = JSON.parseObject(channelAccount.getAccountConfig(), SmsAccount.class);
                List<SmsRecord> smsRecordList = scriptMap.get(smsAccount.getScriptName()).pull(channelAccount.getId().intValue());
                if (CollUtil.isNotEmpty(smsRecordList)) {
                    smsRecordDao.saveAll(smsRecordList);
                }
            }
        } catch (Exception e) {
            log.error("SmsPullReceiptStarter#start fail:{}", Throwables.getStackTraceAsString(e));

        }

    }
}
