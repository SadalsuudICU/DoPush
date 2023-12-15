package com.sadalsuud.push.domain.assign.script;

import com.sadalsuud.push.domain.gateway.domain.SmsRecord;
import com.sadalsuud.push.domain.model.sms.SmsParam;

import java.util.List;

/**
 * @Description 短信脚本 接口
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 15/12/2023
 * @Package com.sadalsuud.push.domain.assign.script
 */
public interface SmsScript {

    /**
     * 发送短信
     *
     * @param smsParam
     * @return 渠道商发送接口返回值
     */
    List<SmsRecord> send(SmsParam smsParam);


    /**
     * 拉取回执
     *
     * @param id 渠道账号的ID
     * @return 渠道商回执接口返回值
     */
    List<SmsRecord> pull(Integer id);
}
