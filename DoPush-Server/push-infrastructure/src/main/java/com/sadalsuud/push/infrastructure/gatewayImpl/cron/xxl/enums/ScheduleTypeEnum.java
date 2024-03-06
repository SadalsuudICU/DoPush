package com.sadalsuud.push.infrastructure.gatewayImpl.cron.xxl.enums;

/**
 * @Description 调度类型
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/3/6
 * @Project DoPush-Server
 */
public enum ScheduleTypeEnum {

    /**
     * NONE
     */
    NONE,
    /**
     * schedule by cron
     */
    CRON,

    /**
     * schedule by fixed rate (in seconds)
     */
    FIX_RATE;

    ScheduleTypeEnum() {
    }

}
