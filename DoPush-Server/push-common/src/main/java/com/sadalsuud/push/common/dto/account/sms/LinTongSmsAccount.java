package com.sadalsuud.push.common.dto.account.sms;

import lombok.*;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinTongSmsAccount extends SmsAccount {
    /**
     * api相关
     */
    private String url;

    /**
     * 账号相关
     */
    private String userName;
    private String password;

}
