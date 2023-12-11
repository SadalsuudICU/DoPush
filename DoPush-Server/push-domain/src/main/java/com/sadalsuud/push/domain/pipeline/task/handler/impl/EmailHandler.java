package com.sadalsuud.push.domain.pipeline.task.handler.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.google.common.base.Throwables;
import com.google.common.util.concurrent.RateLimiter;
import com.sadalsuud.push.common.domain.RecallTaskInfo;
import com.sadalsuud.push.common.domain.TaskInfo;
import com.sadalsuud.push.common.dto.model.EmailContentModel;
import com.sadalsuud.push.common.enums.ChannelType;
import com.sadalsuud.push.domain.gateway.AccountService;
import com.sadalsuud.push.domain.gateway.FileService;
import com.sadalsuud.push.domain.pipeline.task.enums.RateLimitStrategy;
import com.sadalsuud.push.domain.pipeline.task.flowcontrol.FlowControlParam;
import com.sadalsuud.push.domain.pipeline.task.handler.BaseHandler;
import com.sadalsuud.push.domain.pipeline.task.handler.Handler;
import com.sun.mail.util.MailSSLSocketFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * @Description 邮件发送
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 11/12/2023
 * @Package com.sadalsuud.push.domain.pipeline.task.handler.impl
 */
@Component
@Slf4j
public class EmailHandler extends BaseHandler implements Handler {

    private final AccountService accountService;

    private final FileService fileService;

    @Value("${dopush.business.upload.crowd.path}")
    private String dataPath;

    public EmailHandler(AccountService accountService, FileService fileService) {
        this.accountService = accountService;
        this.fileService = fileService;
        channelCode = ChannelType.EMAIL.getCode();

        // 按照请求限流，默认单机 3 qps （具体数值配置在apollo动态调整)
        double rateInitValue = 3.0;
        flowControlParam = FlowControlParam.builder().rateInitValue(rateInitValue)
                .rateLimitStrategy(RateLimitStrategy.REQUEST_RATE_LIMIT)
                .rateLimiter(RateLimiter.create(rateInitValue)).build();

    }

    @Override
    public boolean handler(TaskInfo taskInfo) {
        EmailContentModel emailContentModel = (EmailContentModel) taskInfo.getContentModel();
        MailAccount account = getAccountConfig(taskInfo.getSendAccount());
        try {
            List<File> files = CharSequenceUtil.isNotBlank(emailContentModel.getUrl()) ? fileService.getRemoteUrl2File(dataPath, CharSequenceUtil.split(emailContentModel.getUrl(), StrPool.COMMA)) : null;
            if (CollUtil.isEmpty(files)) {
                MailUtil.send(account, taskInfo.getReceiver(), emailContentModel.getTitle(), emailContentModel.getContent(), true);
            } else {
                MailUtil.send(account, taskInfo.getReceiver(), emailContentModel.getTitle(), emailContentModel.getContent(), true, files.toArray(new File[files.size()]));
            }


        } catch (Exception e) {
            log.error("EmailHandler#handler fail!{},params:{}", Throwables.getStackTraceAsString(e), taskInfo);
            return false;
        }
        return true;
    }

    /**
     * 获取账号信息和配置
     *
     * @return
     */
    private MailAccount getAccountConfig(Integer sendAccount) {
        MailAccount account = accountService.getAccountById(sendAccount, MailAccount.class);
        try {
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            account.setAuth(account.isAuth()).setStarttlsEnable(account.isStarttlsEnable()).setSslEnable(account.isSslEnable()).setCustomProperty("mail.smtp.ssl.socketFactory", sf);
            account.setTimeout(25000).setConnectionTimeout(25000);
        } catch (Exception e) {
            log.error("EmailHandler#getAccount fail!{}", Throwables.getStackTraceAsString(e));
        }
        return account;
    }


    @Override
    public void recall(RecallTaskInfo recallTaskInfo) {

    }
}
