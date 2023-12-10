package com.sadalsuud.push.common.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MiniProgramContentModel extends ContentModel {
    /**
     * 模板消息发送的数据
     */
    private Map<String, String> miniProgramParam;

    /**
     * 模板Id
     */
    private String templateId;

    /**
     * 跳转链接
     */
    private String page;

}
