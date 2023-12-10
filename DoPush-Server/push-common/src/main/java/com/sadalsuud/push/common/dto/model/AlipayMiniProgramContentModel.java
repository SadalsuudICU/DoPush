package com.sadalsuud.push.common.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @Description 支付宝小程序订阅消息内容
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 */
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
