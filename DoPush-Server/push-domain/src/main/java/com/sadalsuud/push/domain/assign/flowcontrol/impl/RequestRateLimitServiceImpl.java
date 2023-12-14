package com.sadalsuud.push.domain.assign.flowcontrol.impl;

import com.google.common.util.concurrent.RateLimiter;
import com.sadalsuud.push.common.domain.TaskInfo;
import com.sadalsuud.push.domain.assign.flowcontrol.FlowControlService;
import com.sadalsuud.push.domain.assign.flowcontrol.annotations.LocalRateLimit;
import com.sadalsuud.push.domain.assign.enums.RateLimitStrategy;
import com.sadalsuud.push.domain.assign.flowcontrol.FlowControlParam;


/**
 * Created by TOM
 * On 2022/7/21 17:05
 *
 * @author TOM
 */
@LocalRateLimit(rateLimitStrategy = RateLimitStrategy.REQUEST_RATE_LIMIT)
public class RequestRateLimitServiceImpl implements FlowControlService {

    /**
     * 根据渠道进行流量控制
     *
     * @param taskInfo
     * @param flowControlParam
     */
    @Override
    public Double flowControl(TaskInfo taskInfo, FlowControlParam flowControlParam) {
        RateLimiter rateLimiter = flowControlParam.getRateLimiter();
        return rateLimiter.acquire(1);
    }
}
