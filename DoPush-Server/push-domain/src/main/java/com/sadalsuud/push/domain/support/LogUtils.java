package com.sadalsuud.push.domain.support;

import cn.monitor4all.logRecord.bean.LogDTO;
import cn.monitor4all.logRecord.service.CustomLogListener;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import com.sadalsuud.push.common.domain.AnchorInfo;
import com.sadalsuud.push.common.domain.LogParam;
import com.sadalsuud.push.domain.gateway.MqServiceGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 11/12/2023
 * @Package com.sadalsuud.push.domain.pipeline.task
 */
@Slf4j
@Component
public class LogUtils extends CustomLogListener {

    // LogUtils SendMqService EventBusListener  ConsumeService 出现了spring无法解决的循环依赖问题
    // 因为LogUtils只用在SendMqService真正触发调用方法时才使用 所以在此使用@Lazy打破循环依赖(并且日志服务没有发送重要)
    @Lazy
    @Resource
    private MqServiceGateway mqServiceGateway;

    @Value("${dopush.business.log.topic.name}")
    private String topicName;

    /**
     * 方法切面的日志 @OperationLog 所产生
     */
    @Override
    public void createLog(LogDTO logDTO) throws Exception {
        log.info("LogUtils#createLog :" + JSON.toJSONString(logDTO));
        //log.info(JSON.toJSONString(logDTO));
    }

    /**
     * 记录当前对象信息
     */
    public void print(LogParam logParam) {
        logParam.setTimestamp(System.currentTimeMillis());
        log.info("LogUtils#createLog#logParam :" + JSON.toJSONString(logParam));
        //log.info(JSON.toJSONString(logParam));
    }

    /**
     * 记录打点信息
     */
    public void print(AnchorInfo anchorInfo) {
        anchorInfo.setLogTimestamp(System.currentTimeMillis());
        String message = JSON.toJSONString(anchorInfo);
        log.info("LogUtils#print#anchorInfo :" + message);
        //log.info(message);

        try {
            mqServiceGateway.send(topicName, message);
        } catch (Exception e) {
            log.error("LogUtils#print send mq fail! e:{},params:{}", Throwables.getStackTraceAsString(e)
                    , JSON.toJSONString(anchorInfo));
        }
    }

    /**
     * 记录当前对象信息和打点信息
     */
    public void print(LogParam logParam, AnchorInfo anchorInfo) {
        print(anchorInfo);
        print(logParam);
    }
}
