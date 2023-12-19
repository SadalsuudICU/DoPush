package com.sadalsuud.push.domain.support;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 19/12/2023
 * @Package com.sadalsuud.push.domain.support
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class GeTuiTokenResultDTO {


    @JSONField(name = "msg")
    private String msg;
    @JSONField(name = "code")
    private Integer code;
    @JSONField(name = "data")
    private DataDTO data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JSONField(name = "expire_time")
        private String expireTime;
        @JSONField(name = "token")
        private String token;
    }
}