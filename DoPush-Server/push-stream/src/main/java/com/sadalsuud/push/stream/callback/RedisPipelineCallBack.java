package com.sadalsuud.push.stream.callback;

import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.async.RedisAsyncCommands;

import java.util.List;

/**
 * @Description redis pipeline 回调接口
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 17/12/2023
 * @Package com.sadalsuud.push.stream.callback
 */
public interface RedisPipelineCallBack {

    /**
     * 具体执行逻辑
     *
     * @param redisAsyncCommands
     * @return
     */
    List<RedisFuture<?>> invoke(RedisAsyncCommands redisAsyncCommands);

}
