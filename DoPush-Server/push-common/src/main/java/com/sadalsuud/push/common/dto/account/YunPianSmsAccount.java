package com.sadalsuud.push.common.dto.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 云片账号信息
 * <p>
 * 参数示例：
 * [{"sms_20":{"url":"https://sms.yunpian.com/v2/sms/tpl_batch_send.json","apikey":"ca55d4c8544444444444622221b5cd7","tpl_id":"533332222282","supplierId":20,"supplierName":"云片"}}]
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YunPianSmsAccount {

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

    /**
     * 标识渠道商Id
     */
    private Integer supplierId;

    /**
     * 标识渠道商名字
     */
    private String supplierName;
}
