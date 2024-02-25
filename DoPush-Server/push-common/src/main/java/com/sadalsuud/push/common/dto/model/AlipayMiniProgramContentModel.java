package com.sadalsuud.push.common.dto.model;

import lombok.*;

import java.util.Map;

/**
 * @Description 支付宝小程序订阅消息内容
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlipayMiniProgramContentModel extends ContentModel {

    /**
     * 模板消息发送的数据
     */
    private Map<String, String> map;

}
