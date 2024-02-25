package com.sadalsuud.push.infrastructure.threadPool;

import com.dtp.core.DtpRegistry;
import com.dtp.core.thread.DtpExecutor;
import com.sadalsuud.push.domain.support.ThreadPool.TreadPoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Description 线程池工具服务
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 11/12/2023
 * @Package com.sadalsuud.push.infrastructure.threadPool
 */
@Component
@RequiredArgsConstructor
public class TreadPoolServiceImpl implements TreadPoolService {
    private static final String SOURCE_NAME = "dopush";
    private final ThreadPoolExecutorShutdownDefinition shutdownDefinition;

    /**
     * 1. 将当前线程池 加入到 动态线程池内
     * 2. 注册 线程池 被Spring管理，优雅关闭
     */
    public void register(DtpExecutor dtpExecutor) {
        DtpRegistry.register(dtpExecutor, SOURCE_NAME);
        shutdownDefinition.registryExecutor(dtpExecutor);
    }
}
