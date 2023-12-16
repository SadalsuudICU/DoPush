package com.sadalsuud.push.common.dto.account.sms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 15/12/2023
 * @Package com.sadalsuud.push.common.dto.account.sms
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AliSmsAccount extends SmsAccount{
    /**
     * api相关
     */
    private String endpoint;

    /**
     * 账号相关
     */
    private String accessKeyId;
    private String accessKeySecret;
    private String templateCode;
    private String SignName;
}
