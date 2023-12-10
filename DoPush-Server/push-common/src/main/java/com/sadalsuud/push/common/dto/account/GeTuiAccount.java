package com.sadalsuud.push.common.dto.account;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 创建个推账号时的元信息
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeTuiAccount {

    private String appId;

    private String appKey;

    private String masterSecret;
}
