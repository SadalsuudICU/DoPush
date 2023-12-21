package com.sadalsuud.push.domain.assign.model.sms;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 对于每种消息类型的 短信配置
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 * @Package com.sadalsuud.push.domain.model
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageTypeSmsConfig {

    /**
     * 权重(决定着流量的占比)
     */
    private Integer weights;

    /**
     * 短信模板若指定了账号，则该字段有值
     */
    private Integer sendAccount;

    /**
     * script名称
     */
    private String scriptName;

}
