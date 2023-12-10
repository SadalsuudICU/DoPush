package com.sadalsuud.push.domain.model.push;


import com.sadalsuud.push.common.domain.TaskInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description push的参数
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 * @Package com.sadalsuud.push.domain.model
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PushParam {

    /**
     * 调用 接口时需要的token
     */
    private String token;

    /**
     * 调用接口时需要的appId
     */
    private String appId;

    /**
     * 消息模板的信息
     */
    private TaskInfo taskInfo;

}
