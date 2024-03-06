package com.sadalsuud.push.infrastructure.gatewayImpl.corn.handler;

import com.dtp.core.thread.DtpExecutor;
import com.sadalsuud.push.infrastructure.gatewayImpl.corn.config.CronAsyncThreadPoolConfig;
import com.sadalsuud.push.infrastructure.gatewayImpl.corn.service.TaskHandler;
import com.sadalsuud.push.infrastructure.gatewayImpl.corn.utils.ThreadPoolUtils;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description 后台提交任务处理类
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/3/6
 * @Project DoPush-Server
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CronTaskHandler {

    private TaskHandler taskHandler;


    private ThreadPoolUtils threadPoolUtils;
    private final DtpExecutor dtpExecutor = CronAsyncThreadPoolConfig.getXxlCronExecutor();

    /**
     * 处理后台的 austin 定时任务消息
     */
    @XxlJob("austinJob")
    public void execute() {
        log.info("CronTaskHandler#execute messageTemplateId:{} cron exec!", XxlJobHelper.getJobParam());
        threadPoolUtils.register(dtpExecutor);

        Long messageTemplateId = Long.valueOf(XxlJobHelper.getJobParam());
        dtpExecutor.execute(() -> taskHandler.handle(messageTemplateId));

    }
}

