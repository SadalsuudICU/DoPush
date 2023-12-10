package com.sadalsuud.push.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @Description 短信渠道商
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 */
@Getter
@ToString
@AllArgsConstructor
public enum SmsSupplier implements PowerfulEnum {


    /**
     * 腾讯渠道商
     */
    TENCENT(10, "腾讯渠道商"),
    /**
     * 云片渠道商
     */
    YUN_PAIN(20, "云片渠道商");
    private final Integer code;
    private final String description;

}
