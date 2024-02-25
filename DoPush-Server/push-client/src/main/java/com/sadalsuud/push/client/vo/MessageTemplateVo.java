package com.sadalsuud.push.client.vo;

import com.sadalsuud.push.domain.template.MessageTemplate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description 消息模板VO
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 14/12/2023
 * @Package com.sadalsuud.push.client.vo
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageTemplateVo {
    /**
     * 返回List列表
     */
    private List<MessageTemplate> rows;

    /**
     * 总条数
     */
    private Long count;
}
