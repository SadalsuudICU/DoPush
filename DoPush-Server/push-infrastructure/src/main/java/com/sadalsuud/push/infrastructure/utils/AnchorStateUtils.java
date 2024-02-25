package com.sadalsuud.push.infrastructure.utils;

import com.sadalsuud.push.common.enums.AnchorState;
import com.sadalsuud.push.common.enums.EnumUtil;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 17/12/2023
 * @Package com.sadalsuud.push.infrastructure.utils
 */
public class AnchorStateUtils {

    private AnchorStateUtils() {

    }

    /**
     * 根据渠道和点位 获取点位的描述
     *
     * @param channel 发送渠道
     * @param state   点位状态码
     * @return 点位描述
     */
    public static String getDescriptionByState(Integer channel, Integer state) {
        return EnumUtil.getDescriptionByCode(state, AnchorState.class);

    }
}