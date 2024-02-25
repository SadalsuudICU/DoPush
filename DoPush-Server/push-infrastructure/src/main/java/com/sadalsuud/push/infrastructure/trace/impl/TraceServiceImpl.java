package com.sadalsuud.push.infrastructure.trace.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.sadalsuud.push.common.constant.DoPushConstant;
import com.sadalsuud.push.common.domain.SimpleAnchorInfo;
import com.sadalsuud.push.common.enums.RespStatusEnum;
import com.sadalsuud.push.domain.support.gateway.CacheService;
import com.sadalsuud.push.infrastructure.trace.TraceResponse;
import com.sadalsuud.push.infrastructure.trace.TraceService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 17/12/2023
 * @Package com.sadalsuud.push.infrastructure.trace.impl
 */
@Service
@Primary
@RequiredArgsConstructor
public class TraceServiceImpl implements TraceService {

    private final CacheService cacheService;

    @Override
    public TraceResponse traceByMessageId(String messageId) {
        if (CharSequenceUtil.isBlank(messageId)) {
            return new TraceResponse(RespStatusEnum.CLIENT_BAD_PARAMETERS.getCode(), RespStatusEnum.CLIENT_BAD_PARAMETERS.getMsg(), null);
        }
        String redisMessageKey = CharSequenceUtil.join(StrUtil.COLON, DoPushConstant.CACHE_KEY_PREFIX, DoPushConstant.MESSAGE_ID, messageId);
        List<String> messageList = cacheService.lRange(redisMessageKey, 0, -1);
        if (CollUtil.isEmpty(messageList)) {
            return new TraceResponse(RespStatusEnum.FAIL.getCode(), RespStatusEnum.FAIL.getMsg(), null);
        }

        // 0. 按时间排序
        List<SimpleAnchorInfo> sortAnchorList =
                messageList.stream()
                        .map(s -> JSON.parseObject(s, SimpleAnchorInfo.class))
                        .sorted((o1, o2) -> Math.toIntExact(o1.getTimestamp() - o2.getTimestamp()))
                        .collect(Collectors.toList());

        return new TraceResponse(RespStatusEnum.SUCCESS.getCode(), RespStatusEnum.SUCCESS.getMsg(), sortAnchorList);
    }
}
