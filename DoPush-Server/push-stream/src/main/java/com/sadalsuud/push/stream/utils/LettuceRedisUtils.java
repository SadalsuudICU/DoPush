package com.sadalsuud.push.stream.utils;

import com.sadalsuud.push.stream.callback.RedisPipelineCallBack;
import com.sadalsuud.push.stream.constants.FlinkConstant;
import io.lettuce.core.LettuceFutures;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.codec.ByteArrayCodec;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description 无Spring环境下使用Redis，基于Lettuce封装
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 17/12/2023
 * @Package com.sadalsuud.push.stream.utils
 */
public class LettuceRedisUtils {

    /**
     * 初始化 redisClient
     */
    private static final RedisClient redisClient;

    static {
        RedisURI redisUri = RedisURI.Builder.redis(FlinkConstant.REDIS_IP)
                .withPort(Integer.parseInt(FlinkConstant.REDIS_PORT))
                .withPassword(FlinkConstant.REDIS_PASSWORD.toCharArray())
                .build();
        redisClient = RedisClient.create(redisUri);
    }

    private LettuceRedisUtils() {

    }

    /**
     * 封装pipeline操作
     */
    public static void pipeline(RedisPipelineCallBack pipelineCallBack) {
        StatefulRedisConnection<byte[], byte[]> connect = redisClient.connect(new ByteArrayCodec());
        RedisAsyncCommands<byte[], byte[]> commands = connect.async();

        List<RedisFuture<?>> futures = pipelineCallBack.invoke(commands);

        commands.flushCommands();
        LettuceFutures.awaitAll(10, TimeUnit.SECONDS,
                futures.toArray(new RedisFuture[0]));
        connect.close();
    }

}