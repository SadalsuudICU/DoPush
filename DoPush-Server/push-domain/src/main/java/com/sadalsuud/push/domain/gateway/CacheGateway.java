package com.sadalsuud.push.domain.gateway;

import org.springframework.data.redis.core.script.RedisScript;

import java.util.List;
import java.util.Map;

/**
 * @Description redis服务网关接口
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 11/12/2023
 * @Package com.sadalsuud.push.domain.gateway
 */
public interface CacheGateway {
    /**
     * mGet将结果封装为Map
     *
     * @param keys
     */
    Map<String, String> mGet(List<String> keys);

    /**
     * hGetAll
     *
     * @param key
     */
    Map<Object, Object> hGetAll(String key);

    /**
     * lRange
     *
     * @param key
     */
    List<String> lRange(String key, long start, long end);

    /**
     * pipeline 设置 key-value 并设置过期时间
     */
    void pipelineSetEx(Map<String, String> keyValues, Long seconds);


    /**
     * lpush 方法 并指定 过期时间
     */
    void lPush(String key, String value, Long seconds);

    /**
     * lLen 方法
     */
    Long lLen(String key);

    /**
     * lPop 方法
     */
    String lPop(String key);

    /**
     * pipeline 设置 key-value 并设置过期时间
     *
     * @param seconds 过期时间
     * @param delta   自增的步长
     */
    void pipelineHashIncrByEx(Map<String, String> keyValues, Long seconds, Long delta);

    /**
     * 执行指定的lua脚本返回执行结果
     * --KEYS[1]: 限流 key
     * --ARGV[1]: 限流窗口
     * --ARGV[2]: 当前时间戳（作为score）
     * --ARGV[3]: 阈值
     * --ARGV[4]: score 对应的唯一value
     *
     * @param redisScript
     * @param keys
     * @param args
     * @return
     */
    Boolean execLimitLua(RedisScript<Long> redisScript, List<String> keys, String... args);
}
