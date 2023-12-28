package com.sadalsuud.push.application.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.sadalsuud.push.client.api.DataService;
import com.sadalsuud.push.client.vo.DataParam;
import com.sadalsuud.push.common.constant.DoPushConstant;
import com.sadalsuud.push.common.domain.SimpleAnchorInfo;
import com.sadalsuud.push.domain.gateway.CacheService;
import com.sadalsuud.push.domain.gateway.IRepository.IMessageTemplateRepository;
import com.sadalsuud.push.domain.gateway.IRepository.ISmsRepository;
import com.sadalsuud.push.domain.gateway.domain.MessageTemplate;
import com.sadalsuud.push.domain.gateway.domain.SmsRecord;
import com.sadalsuud.push.domain.receive.TaskInfoUtils;
import com.sadalsuud.push.infrastructure.trace.TraceResponse;
import com.sadalsuud.push.infrastructure.trace.TraceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description 数据链路追踪 实现类
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 17/12/2023
 * @Package com.sadalsuud.push.application.service
 */
@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {

    private final CacheService cacheService;

    private final IMessageTemplateRepository messageTemplateRepository;

    private final ISmsRepository smsRepository;

    private final TraceService traceService;



    /**
     * 获取全链路追踪 消息自身维度信息
     *
     * @param messageId 消息
     * @return
     */
    @Override
    public Map<String, List<SimpleAnchorInfo>> getTraceMessageInfo(String messageId) {
        TraceResponse traceResponse = traceService.traceByMessageId(messageId);
        if (CollUtil.isEmpty(traceResponse.getData())) {
            return null;
        }
        return buildTimeLineVo(traceResponse.getData());
    }

    /**
     * 获取全链路追踪 用户维度信息
     *
     * @param receiver 接收者
     * @return
     */
    @Override
    public Map<String, List<SimpleAnchorInfo>> getTraceUserInfo(String receiver) {
        List<String> userInfoList = cacheService.lRange(receiver, 0, -1);
        if (CollUtil.isEmpty(userInfoList)) {
            return null;
        }

        // 0. 按时间排序
        List<SimpleAnchorInfo> sortAnchorList =
                userInfoList.stream()
                        .map(s -> JSON.parseObject(s, SimpleAnchorInfo.class))
                        .sorted(Comparator.comparing(SimpleAnchorInfo::getTimestamp).reversed())
                        .collect(Collectors.toList());
        return buildTimeLineVo(sortAnchorList);
    }

    /**
     * 获取全链路追踪 消息模板维度信息
     *
     * @param businessId 业务ID（如果传入消息模板ID，则生成当天的业务ID）
     * @return
     */
    @Override
    public Map<Object, Object> getTraceMessageTemplateInfo(String businessId) {
        // 获取businessId并获取模板信息
        businessId = getRealBusinessId(businessId);
        Optional<MessageTemplate> optional = messageTemplateRepository.findById(TaskInfoUtils.getMessageTemplateIdFromBusinessId(Long.valueOf(businessId)));
        if (!optional.isPresent()) {
            return null;
        }

        /*
          获取redis清洗好的数据
          key：state
          value:stateCount
         */
        return cacheService.hGetAll(getRealBusinessId(businessId));
    }

    /**
     * 获取短信下发记录
     *
     * @param dataParam
     * @return
     */
    @Override
    public Map<String, List<SmsRecord>> getTraceSmsInfo(DataParam dataParam) {
        Integer sendDate = Integer.valueOf(DateUtil.format(new Date(dataParam.getDateTime() * 1000L), DatePattern.PURE_DATE_PATTERN));
        List<SmsRecord> smsRecordList = smsRepository.findByPhoneAndSendDate(Long.valueOf(dataParam.getReceiver()), sendDate);
        if (CollUtil.isEmpty(smsRecordList)) {
            return null;
        }

        return smsRecordList.stream().collect(Collectors.groupingBy(o -> o.getPhone() + o.getSeriesId()));
    }


    /**
     * 如果传入的是模板ID，则生成【当天】的businessId进行查询
     * 如果传入的是businessId，则按默认的businessId进行查询
     * 判断是否为businessId则判断长度是否为16位（businessId长度固定16)
     */
    private String getRealBusinessId(String businessId) {
        if (DoPushConstant.BUSINESS_ID_LENGTH == businessId.length()) {
            return businessId;
        }
        Optional<MessageTemplate> optional = messageTemplateRepository.findById(Long.valueOf(businessId));
        if (optional.isPresent()) {
            MessageTemplate messageTemplate = optional.get();
            return String.valueOf(TaskInfoUtils.generateBusinessId(messageTemplate.getId(), messageTemplate.getTemplateType()));
        }
        return businessId;
    }

    private Map<String, List<SimpleAnchorInfo>> buildTimeLineVo(List<SimpleAnchorInfo> sortAnchorList) {
        // 1. 对相同的businessId进行分类  {"businessId":[{businessId,state,timeStamp},{businessId,state,timeStamp}]}
        Map<String, List<SimpleAnchorInfo>> map = MapUtil.newHashMap();
        for (SimpleAnchorInfo simpleAnchorInfo : sortAnchorList) {
            List<SimpleAnchorInfo> simpleAnchorInfos = map.get(String.valueOf(simpleAnchorInfo.getBusinessId()));
            if (CollUtil.isEmpty(simpleAnchorInfos)) {
                simpleAnchorInfos = new ArrayList<>();
            }
            simpleAnchorInfos.add(simpleAnchorInfo);
            map.put(String.valueOf(simpleAnchorInfo.getBusinessId()), simpleAnchorInfos);
        }
        return map;
    }
}
