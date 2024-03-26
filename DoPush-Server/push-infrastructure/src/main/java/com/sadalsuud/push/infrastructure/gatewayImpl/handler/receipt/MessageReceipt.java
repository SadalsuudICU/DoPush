package com.sadalsuud.push.infrastructure.gatewayImpl.handler.receipt;

import com.google.common.base.Throwables;
import com.sadalsuud.push.infrastructure.gatewayImpl.config.SupportThreadPoolConfig;
import com.sadalsuud.push.infrastructure.gatewayImpl.handler.receipt.starter.ReceiptMessageStater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 拉取回执消息执行器
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/3/26
 * @Project DoPush-Server
 */
@Component
@Slf4j
public class MessageReceipt {

    @Resource
    private List<ReceiptMessageStater> receiptMessageStaterList;

    @PostConstruct
    private void init() {
        SupportThreadPoolConfig.getPendingSingleThreadPool().execute(() -> {
            while (true) {
                try {
                    for (ReceiptMessageStater receiptMessageStater : receiptMessageStaterList) {
                        //receiptMessageStater.start();
                    }
                    Thread.sleep(5000);
                } catch (Exception e) {
                    log.error("MessageReceipt#init fail:{}", Throwables.getStackTraceAsString(e));
                    Thread.currentThread().interrupt();
                }
            }
        });
    }
}
