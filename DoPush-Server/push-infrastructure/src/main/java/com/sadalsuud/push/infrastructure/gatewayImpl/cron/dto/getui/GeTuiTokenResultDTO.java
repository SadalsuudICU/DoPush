package com.sadalsuud.push.infrastructure.gatewayImpl.cron.dto.getui;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 见https://docs.getui.com/getui/server/rest_v2/token/
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/3/6
 * @Project DoPush-Server
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
