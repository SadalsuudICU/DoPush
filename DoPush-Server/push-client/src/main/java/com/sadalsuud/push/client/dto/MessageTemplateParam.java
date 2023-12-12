package com.sadalsuud.push.client.dto;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 消息模板请求参数
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 12/12/2023
 * @Package com.sadalsuud.push.client.dto
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageTemplateParam {

    /**
     * 当前页码
     */
    @NotNull
    private Integer page = 1;

    /**
     * 当前页大小
     */
    @NotNull
    private Integer perPage = 10;

    /**
     * 模板ID
     */
    private Long id;

    /**
     * 当前用户
     */
    private String creator;

    /**
     * 消息接收者(测试发送时使用)
     */
    private String receiver;

    /**
     * 下发参数信息
     */
    private String msgContent;

    /**
     * 模版名称
     */
    private String keywords;
}
