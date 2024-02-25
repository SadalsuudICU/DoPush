package com.sadalsuud.push.domain.support.gateway;

import com.dtp.core.thread.DtpExecutor;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 11/12/2023
 * @Package com.sadalsuud.push.domain.gateway
 */
public interface TreadPoolService {
    public void register(DtpExecutor dtpExecutor);
}
