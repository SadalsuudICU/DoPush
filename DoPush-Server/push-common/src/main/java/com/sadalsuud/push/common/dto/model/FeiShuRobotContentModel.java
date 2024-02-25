package com.sadalsuud.push.common.dto.model;

import lombok.*;

/**
 * @Description 飞书群 机器人
 * <p>
 * https://open.feishu.cn/document/ukTMukTMukTM/ucTM5YjL3ETO24yNxkjN#756b882f
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeiShuRobotContentModel extends ContentModel {

    /**
     * 发送类型
     */
    private String sendType;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 发送标题
     */
    private String title;

    /**
     * 媒体Id
     */
    private String mediaId;


    /**
     * 富文本内容：[[{"tag":"text","text":"项目有更新: "},{"tag":"a","text":"请查看","href":"http://www.example.com/"},{"tag":"at","user_id":"ou_18eac8********17ad4f02e8bbbb"}]]
     */
    private String postContent;

}
