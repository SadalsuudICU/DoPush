package com.sadalsuud.push.domain.assign.model.push.getui;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @Description 批量推送消息的param
 * https://docs.getui.com/getui/server/rest_v2/push/
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 * @Package com.sadalsuud.push.domain.model
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BatchSendPushParam {

    /**
     * audience
     */
    @JSONField(name = "audience")
    private AudienceVO audience;
    /**
     * taskid
     */
    @JSONField(name = "taskid")
    private String taskId;
    /**
     * isAsync
     */
    @JSONField(name = "is_async")
    private Boolean isAsync;

    /**
     * AudienceVO
     */
    @NoArgsConstructor
    @Data
    @Builder
    @AllArgsConstructor
    public static class AudienceVO {
        /**
         * cid
         */
        @JSONField(name = "cid")
        private Set<String> cid;
    }
}
