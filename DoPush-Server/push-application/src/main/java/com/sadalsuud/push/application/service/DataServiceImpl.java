package com.sadalsuud.push.application.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import com.alibaba.fastjson.JSON;
import com.sadalsuud.push.client.api.DataService;
import com.sadalsuud.push.client.dto.DataParam;
import com.sadalsuud.push.client.vo.timeline.SmsTimeLineVo;
import com.sadalsuud.push.client.vo.timeline.UserTimeLineVo;
import com.sadalsuud.push.common.constant.DoPushConstant;
import com.sadalsuud.push.common.domain.SimpleAnchorInfo;
import com.sadalsuud.push.common.enums.AnchorState;
import com.sadalsuud.push.common.enums.ChannelType;
import com.sadalsuud.push.common.enums.EnumUtil;
import com.sadalsuud.push.common.enums.SmsStatus;
import com.sadalsuud.push.domain.data.repository.ISmsRepository;
import com.sadalsuud.push.domain.support.cache.CacheService;
import com.sadalsuud.push.domain.template.MessageTemplate;
import com.sadalsuud.push.domain.data.SmsRecord;
import com.sadalsuud.push.domain.receive.TaskInfoUtils;
import com.sadalsuud.push.domain.template.repository.IMessageTemplateRepository;
import com.sadalsuud.push.infrastructure.trace.TraceResponse;
import com.sadalsuud.push.infrastructure.trace.TraceService;
import com.sadalsuud.push.infrastructure.util.AnchorStateUtils;
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
    public UserTimeLineVo getTraceMessageInfo(String messageId) {
        TraceResponse traceResponse = traceService.traceByMessageId(messageId);
        if (CollUtil.isEmpty(traceResponse.getData())) {
            return null;
        }
        return buildUserTimeLineVo(traceResponse.getData());
    }

    /**
     * 获取全链路追踪 用户维度信息
     *
     * @param receiver 接收者
     * @return
     */
    @Override
    public UserTimeLineVo getTraceUserInfo(String receiver) {
        List<String> userInfoList = cacheService.lRange(receiver, 0, -1);
        if (CollUtil.isEmpty(userInfoList)) {
            return UserTimeLineVo.builder().build();
        }

        // 0. 按时间排序
        List<SimpleAnchorInfo> sortAnchorList =
                userInfoList.stream()
                        .map(s -> JSON.parseObject(s, SimpleAnchorInfo.class))
                        .sorted(Comparator.comparing(SimpleAnchorInfo::getTimestamp).reversed())
                        .collect(Collectors.toList());
        return buildUserTimeLineVo(sortAnchorList);
    }

    /**
     * 获取全链路追踪 消息模板维度信息
     *
     * @param businessId 业务ID（如果传入消息模板ID，则生成当天的业务ID）
     * @return
     */
    @Override
    public Map<String, Integer> getTraceMessageTemplateInfo(String businessId) {
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
        Map<Object, Object> objectMap = cacheService.hGetAll(getRealBusinessId(businessId));
        HashMap<String, Integer> data = new HashMap<>();
        for (Object key : objectMap.keySet()) {
            String state = (String) key;
            Integer count = Integer.valueOf((String) objectMap.get(key));
            String des = AnchorState.findEnumByCode(Integer.valueOf(state)).getDescription();
            data.put(des, count);
        }

        return data;
    }

    /**
     * 获取短信下发记录
     *
     * @param dataParam
     * @return
     */
    @Override
    public SmsTimeLineVo getTraceSmsInfo(DataParam dataParam) {
        Integer sendDate = Integer.valueOf(DateUtil.format(new Date(dataParam.getDateTime()), DatePattern.PURE_DATE_PATTERN));
        List<SmsRecord> smsRecordList = smsRepository.findByPhoneAndSendDate(Long.valueOf(dataParam.getReceiver()), sendDate);
        if (CollUtil.isEmpty(smsRecordList)) {
            return SmsTimeLineVo.builder().items(Collections.singletonList(SmsTimeLineVo.ItemsVO.builder().build())).build();
        }

        return buildSmsTimeLineVo(
                smsRecordList.stream()
                        .collect(Collectors.groupingBy(o -> o.getPhone() + o.getSeriesId())));
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

    private UserTimeLineVo buildUserTimeLineVo(List<SimpleAnchorInfo> sortAnchorList) {
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

        // 2. 封装vo 给到前端渲染展示
        List<UserTimeLineVo.ItemsVO> items = new ArrayList<>();
        for (Map.Entry<String, List<SimpleAnchorInfo>> entry : map.entrySet()) {
            Long messageTemplateId = TaskInfoUtils.getMessageTemplateIdFromBusinessId(Long.valueOf(entry.getKey()));
            MessageTemplate messageTemplate = messageTemplateRepository.findById(messageTemplateId).orElse(null);
            if (Objects.isNull(messageTemplate)) {
                continue;
            }

            StringBuilder sb = new StringBuilder();
            for (SimpleAnchorInfo simpleAnchorInfo : entry.getValue()) {
                if (AnchorState.RECEIVE.getCode().equals(simpleAnchorInfo.getState())) {
                    sb.append(StrPool.CRLF);
                }
                String startTime = DateUtil.format(new Date(simpleAnchorInfo.getTimestamp()), DatePattern.NORM_DATETIME_PATTERN);
                String stateDescription = AnchorStateUtils.getDescriptionByState(messageTemplate.getSendChannel(), simpleAnchorInfo.getState());

                sb.append(startTime).append(StrPool.C_COLON).append(stateDescription).append("==>");
            }

            for (String detail : sb.toString().split(StrPool.CRLF)) {
                if (CharSequenceUtil.isNotBlank(detail)) {
                    UserTimeLineVo.ItemsVO itemsVO = UserTimeLineVo.ItemsVO.builder()
                            .businessId(entry.getKey())
                            .sendType(EnumUtil.getEnumByCode(messageTemplate.getSendChannel(), ChannelType.class).getDescription())
                            .creator(messageTemplate.getCreator())
                            .title(messageTemplate.getName())
                            .detail(detail)
                            .build();
                    items.add(itemsVO);
                }
            }
        }
        return UserTimeLineVo.builder().items(items).build();
    }

    private SmsTimeLineVo buildSmsTimeLineVo(Map<String, List<SmsRecord>> smsRecords) {
        ArrayList<SmsTimeLineVo.ItemsVO> itemsVoS = new ArrayList<>();
        SmsTimeLineVo smsTimeLineVo = SmsTimeLineVo.builder().items(itemsVoS).build();

        for (Map.Entry<String, List<SmsRecord>> entry : smsRecords.entrySet()) {
            SmsTimeLineVo.ItemsVO itemsVO = SmsTimeLineVo.ItemsVO.builder().build();
            for (SmsRecord smsRecord : entry.getValue()) {
                // 发送记录 messageTemplateId >0 ,回执记录 messageTemplateId =0
                if (smsRecord.getMessageTemplateId() > 0) {
                    itemsVO.setBusinessId(String.valueOf(smsRecord.getMessageTemplateId()));
                    itemsVO.setContent(smsRecord.getMsgContent());
                    itemsVO.setSendType(EnumUtil.getDescriptionByCode(smsRecord.getStatus(), SmsStatus.class));
                    itemsVO.setSendTime(DateUtil.format(new Date(smsRecord.getCreated() * 1000L), DatePattern.NORM_DATETIME_PATTERN));
                } else {
                    itemsVO.setReceiveType(EnumUtil.getDescriptionByCode(smsRecord.getStatus(), SmsStatus.class));
                    itemsVO.setReceiveContent(smsRecord.getReportContent());
                    itemsVO.setReceiveTime(DateUtil.format(new Date(smsRecord.getUpdated() * 1000L), DatePattern.NORM_DATETIME_PATTERN));
                }
            }
            itemsVoS.add(itemsVO);
        }

        return smsTimeLineVo;
    }

}
