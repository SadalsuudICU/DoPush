package com.sadalsuud.push.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @Description 素材类型枚举
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/5/16
 * @Project DoPush-Server
 */
@Getter
@ToString
@AllArgsConstructor
public enum MaterialType implements PowerfulEnum {
    IMAGE(10, "image"),
    VOICE(20, "voice"),
    VIDEO(30, "video"),
    OTHERS(40, "others");

    private final Integer code;
    private final String description;
}
