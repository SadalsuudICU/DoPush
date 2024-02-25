package com.sadalsuud.push.infrastructure.gatewayImpl.handler.handlers;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Throwables;
import com.sadalsuud.push.common.constant.CommonConstant;
import com.sadalsuud.push.common.domain.RecallTaskInfo;
import com.sadalsuud.push.common.domain.TaskInfo;
import com.sadalsuud.push.common.dto.account.sms.SmsAccount;
import com.sadalsuud.push.common.dto.model.SmsContentModel;
import com.sadalsuud.push.common.enums.ChannelType;
import com.sadalsuud.push.domain.assign.handler.BaseHandler;
import com.sadalsuud.push.domain.assign.handler.Handler;
import com.sadalsuud.push.domain.data.repository.ISmsRepository;
import com.sadalsuud.push.infrastructure.gatewayImpl.handler.script.SmsScript;
import com.sadalsuud.push.domain.channel.AccountService;
import com.sadalsuud.push.domain.support.config.ConfigService;
import com.sadalsuud.push.domain.data.SmsRecord;
import com.sadalsuud.push.domain.assign.model.sms.MessageTypeSmsConfig;
import com.sadalsuud.push.domain.assign.model.sms.SmsParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @Description 短信发送
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 15/12/2023
 * @Package com.sadalsuud.push.domain.assign.handler.impl
 */
@Slf4j
@Component
public class SmsHandler extends BaseHandler implements Handler {

    /**
     * 流量自动分配策略
     */
    private static final Integer AUTO_FLOW_RULE = 0;
    private static final String FLOW_KEY = "msgTypeSmsConfig";
    private static final String FLOW_KEY_PREFIX = "message_type_";


    private final ISmsRepository smsRepository;
    private final ConfigService configService;
    private final ApplicationContext applicationContext;
    private final AccountService accountService;

    public SmsHandler(ISmsRepository smsRepository,
                      ConfigService configService,
                      ApplicationContext applicationContext,
                      AccountService accountService) {
        this.smsRepository = smsRepository;
        this.configService = configService;
        this.applicationContext = applicationContext;
        this.accountService = accountService;

        channelCode = ChannelType.SMS.getCode();
    }

    @Override
    public boolean handler(TaskInfo taskInfo) {
        SmsParam smsParam =
                SmsParam.builder()
                        .phones(taskInfo.getReceiver())
                        .content(getSmsContent(taskInfo))
                        .messageTemplateId(taskInfo.getMessageTemplateId())
                        .build();
        // 1.基于配置做流量负载 -> 2.发送消息
        try {
            MessageTypeSmsConfig[] messageTypeSmsConfigs = loadBalance(getMessageTypeSmsConfig(taskInfo));
            for (MessageTypeSmsConfig Config : messageTypeSmsConfigs) {
                smsParam.setScriptName(Config.getScriptName());
                smsParam.setSendAccountId(Config.getSendAccount());
                List<SmsRecord> records = applicationContext.getBean(Config.getScriptName(), SmsScript.class).send(smsParam);
                if (CollUtil.isNotEmpty(records)) {
                    smsRepository.saveAll(records);
                    return true;
                }
            }
        }catch (Exception e) {
            log.error("SmsHandler#handler fail:{},params:{}", Throwables.getStackTraceAsString(e), JSON.toJSONString(smsParam));
        }
        return false;
    }


    /**
     * 处理文案 是否需要拼接上URl等内容
     * @param taskInfo
     * @return 处理好的文案
     */
    private String getSmsContent(TaskInfo taskInfo) {
        SmsContentModel contentModel = (SmsContentModel) taskInfo.getContentModel();
        String url = contentModel.getUrl();
        if (CharSequenceUtil.isNotBlank(url)) {
            return contentModel.getContent() + CharSequenceUtil.SPACE + contentModel.getUrl();
        } else {
            return contentModel.getContent();
        }
    }


    /**
     * 如模板指定具体的明确账号，则优先发其账号，否则走到流量配置
     * <p>
     * 流量配置每种类型都会有其下发渠道账号的配置(流量占比也会配置里面)
     * <p>
     * 样例：
     * key：msgTypeSmsConfig
     * value：[{"message_type_10":[{"weights":80,"scriptName":"TencentSmsScript"},{"weights":20,"scriptName":"YunPianSmsScript"}]},{"message_type_20":[{"weights":20,"scriptName":"YunPianSmsScript"}]},{"message_type_30":[{"weights":20,"scriptName":"TencentSmsScript"}]},{"message_type_40":[{"weights":20,"scriptName":"TencentSmsScript"}]}]
     * <p>
     * 通知类短信有两个发送渠道 TencentSmsScript 占80%流量，YunPianSmsScript占20%流量
     * <p>
     * 营销类短信只有一个发送渠道 YunPianSmsScript
     * <p>
     * 验证码短信只有一个发送渠道 TencentSmsScript
     *
     * @param taskInfo
     * @return
     */
    private List<MessageTypeSmsConfig> getMessageTypeSmsConfig(TaskInfo taskInfo) {

        /*
          如果模板指定了账号，则优先使用具体的账号进行发送
         */
        if (!taskInfo.getSendAccount().equals(AUTO_FLOW_RULE)) {
            SmsAccount account = accountService.getAccountById(taskInfo.getSendAccount(), SmsAccount.class);
            return Collections.singletonList(
                    MessageTypeSmsConfig.builder()
                            .sendAccount(taskInfo.getSendAccount())
                            .scriptName(account.getScriptName())
                            .weights(100)
                            .build());
        }

        /*
          读取流量配置
         */
        String property = configService.getProperty(FLOW_KEY, CommonConstant.EMPTY_VALUE_JSON_ARRAY);
        JSONArray jsonArray = JSON.parseArray(property);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONArray array =
                    jsonArray.getJSONObject(i).getJSONArray(FLOW_KEY_PREFIX + taskInfo.getMsgType());
            if (CollUtil.isNotEmpty(array)) {
                return JSON.parseArray(JSON.toJSONString(array), MessageTypeSmsConfig.class);
            }
        }
        return new ArrayList<>();
    }


    /**
     * 基于加权随机数做短信渠道账号之间的负载均衡
     * @param messageTypeSmsConfigs
     * @return 分摊到的渠道信息
     */
    private MessageTypeSmsConfig[] loadBalance(List<MessageTypeSmsConfig> messageTypeSmsConfigs) {

        int total = 0;
        for (MessageTypeSmsConfig channelConfig : messageTypeSmsConfigs) {
            total += channelConfig.getWeights();
        }

        // 生成一个随机数[1,total]，看落到哪个区间
        int index = new Random().nextInt(total) + 1;

        MessageTypeSmsConfig supplier;
        MessageTypeSmsConfig supplierBack;
        for (int i = 0; i < messageTypeSmsConfigs.size(); ++i) {
            if (index <= messageTypeSmsConfigs.get(i).getWeights()) {
                supplier = messageTypeSmsConfigs.get(i);

                // 取下一个供应商
                int j = (i + 1) % messageTypeSmsConfigs.size();
                if (i == j) {
                    return new MessageTypeSmsConfig[]{supplier};
                }
                supplierBack = messageTypeSmsConfigs.get(j);
                return new MessageTypeSmsConfig[]{supplier, supplierBack};
            }
            index -= messageTypeSmsConfigs.get(i).getWeights();
        }
        return new MessageTypeSmsConfig[0];
    }


    /**
     * 短信不支持撤回
     *
     * @param recallTaskInfo
     */
    @Override
    public void recall(RecallTaskInfo recallTaskInfo) {

    }

}
