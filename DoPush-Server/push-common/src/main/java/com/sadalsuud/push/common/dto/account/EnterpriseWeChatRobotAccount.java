package com.sadalsuud.push.common.dto.account;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 企业微信 机器人 账号信息
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseWeChatRobotAccount {
    /**
     * 自定义群机器人中的 webhook
     */
    private String webhook;

}
