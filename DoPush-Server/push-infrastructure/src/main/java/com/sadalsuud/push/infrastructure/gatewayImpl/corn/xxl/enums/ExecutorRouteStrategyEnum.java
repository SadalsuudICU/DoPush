package com.sadalsuud.push.infrastructure.gatewayImpl.corn.xxl.enums;

/**
 * @Description 路由策略
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/3/6
 * @Project DoPush-Server
 */
public enum ExecutorRouteStrategyEnum {

    /**
     * FIRST
     */
    FIRST,
    /**
     * LAST
     */
    LAST,
    /**
     * ROUND
     */
    ROUND,
    /**
     * RANDOM
     */
    RANDOM,
    /**
     * CONSISTENT_HASH
     */
    CONSISTENT_HASH,
    /**
     * LEAST_FREQUENTLY_USED
     */
    LEAST_FREQUENTLY_USED,
    /**
     * LEAST_RECENTLY_USED
     */
    LEAST_RECENTLY_USED,
    /**
     * FAILOVER
     */
    FAILOVER,
    /**
     * BUSYOVER
     */
    BUSYOVER,
    /**
     * SHARDING_BROADCAST
     */
    SHARDING_BROADCAST;

    ExecutorRouteStrategyEnum() {
    }
}
