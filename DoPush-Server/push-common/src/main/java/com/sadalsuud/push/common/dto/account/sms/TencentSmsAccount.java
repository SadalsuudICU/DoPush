package com.sadalsuud.push.common.dto.account.sms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description 腾讯短信参数
 * <p>
 * 账号参数示例：
 * {
 * "url": "sms.tencentcloudapi.com",
 * "region": "ap-guangzhou",
 * "secretId": "your secretId",
 * "secretKey": "your secretKey",
 * "smsSdkAppId": "your SDK AppId",
 * "templateId": "1182097",
 * "signName": "your signName",
 * "supplierId": 10,
 * "supplierName": "腾讯云",
 * "scriptName": "TencentSmsScript"
 * }
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TencentSmsAccount extends SmsAccount {

    /**
     * api相关
     */
    private String url;
    private String region;

    /**
     * 账号相关
     */
    private String secretId;
    private String secretKey;
    private String smsSdkAppId;
    private String templateId;
    private String signName;
}
