package com.sadalsuud.push.common.dto.account.sms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @Description 云片账号信息
 * <p>
 * 账号参数示例：
 * {"url":"https://sms.yunpian.com/v2/sms/tpl_batch_send.json","apikey":"caffff8234234231b5cd7","tpl_id":"523333332","supplierId":20,"supplierName":"云片","scriptName":"YunPianSmsScript"}
 *
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YunPianSmsAccount extends SmsAccount {

    /**
     * apikey
     */
    private String apikey;
    /**
     * tplId
     */
    private String tplId;

    /**
     * api相关
     */
    private String url;

}
