package com.sadalsuud.push.client.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 全链路请求参数
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 17/12/2023
 * @Package com.sadalsuud.push.client.vo
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataParam {


    /**
     * 查看消息Id的链路信息
     */
    private String messageId;

    /**
     * 查看用户的链路信息
     */
    private String receiver;


    /**
     * 业务Id(数据追踪使用)
     * 生成逻辑参考 TaskInfoUtils
     * 如果传入的是模板ID，则生成当天的业务ID
     */
    private String businessId;


    /**
     * 日期时间(检索短信的条件使用)
     */
    private Long dateTime;


}
