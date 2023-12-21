package com.sadalsuud.push.domain.assign.model.push.getui;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 发送消息后的返回值
 * https://docs.getui.com/getui/server/rest_v2/common_args/?id=doc-title-1
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 * @Package com.sadalsuud.push.domain.model
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendPushResult {
    /**
     * msg
     */
    @JSONField(name = "msg")
    private String msg;
    /**
     * code
     */
    @JSONField(name = "code")
    private Integer code;
    /**
     * data
     */
    @JSONField(name = "data")
    private JSONObject data;

}
