package com.sadalsuud.push.common.dto.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 模板消息参数
 * <p>
 * 参数示例：
 * https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Template_Message_Interface.html
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeChatOfficialAccount {

    /**
     * 账号相关
     */
    private String appId;
    private String secret;
    private String token;

}
