package com.sadalsuud.push.domain.assign.pending;

import com.dtp.core.thread.DtpExecutor;
import com.sadalsuud.push.domain.support.ThreadPool.TreadPoolService;
import com.sadalsuud.push.domain.support.GroupIdMappingUtils;
import com.sadalsuud.push.domain.assign.pipeline.config.HandlerThreadPoolConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @Description 存储每一种消息类型与TaskPending之间的映射关系
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 11/12/2023
 * @Package com.sadalsuud.push.domain.pipeline.task.pending
 */
@Component
@RequiredArgsConstructor
public class TaskPendingHolder {
    /**
     * 获取得到所有的groupId
     */
    private static final List<String> groupIds = GroupIdMappingUtils.getAllGroupIds();
    private final TreadPoolService treadPoolService;
    private static final Map<String, ExecutorService> holder = new HashMap<>(32);

    /**
     * 给每个渠道，每种消息类型初始化一个线程池
     */
    @PostConstruct
    public void init() {
        /*
         * example ThreadPoolName:dopush.im.notice
         * 可以通过apollo配置：dynamic-tp-apollo-dtp.yml  动态修改线程池的信息
         */
        for (String groupId : groupIds) {
            DtpExecutor executor = HandlerThreadPoolConfig.getExecutor(groupId);
            treadPoolService.register(executor);

            holder.put(groupId, executor);
        }
    }

    /**
     * 得到对应的线程池
     *
     * @param groupId
     * @return
     */
    public ExecutorService route(String groupId) {
        return holder.get(groupId);
    }


}
