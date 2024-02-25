package com.sadalsuud.push.common.dto.model;


import lombok.*;

/**
 * @Description 通知栏消息推送
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PushContentModel extends ContentModel {

    private String title;
    private String content;
    private String url;
}
