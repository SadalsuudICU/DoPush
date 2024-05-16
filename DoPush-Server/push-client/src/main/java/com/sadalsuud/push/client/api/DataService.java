package com.sadalsuud.push.client.api;

import com.sadalsuud.push.client.dto.DataParam;
import com.sadalsuud.push.client.vo.timeline.SmsTimeLineVo;
import com.sadalsuud.push.client.vo.timeline.UserTimeLineVo;

import java.util.Map;

/**
 * @Description 数据链路追踪获取接口
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 17/12/2023
 * @Package com.sadalsuud.push.client.api
 */
public interface DataService {

    /**
     * 获取全链路追踪 消息自身维度信息
     *
     * @param messageId 消息
     * @return
     */
    UserTimeLineVo getTraceMessageInfo(String messageId);

    /**
     * 获取全链路追踪 用户维度信息
     *
     * @param receiver 接收者
     * @return
     */
    UserTimeLineVo getTraceUserInfo(String receiver);


    /**
     * 获取全链路追踪 消息模板维度信息
     *
     * @param businessId 业务ID（如果传入消息模板ID，则生成当天的业务ID）
     * @return
     */
    Map<String, Integer> getTraceMessageTemplateInfo(String businessId);


    /**
     * 获取短信下发记录
     *
     * @param dataParam
     * @return
     */
    SmsTimeLineVo getTraceSmsInfo(DataParam dataParam);

}
