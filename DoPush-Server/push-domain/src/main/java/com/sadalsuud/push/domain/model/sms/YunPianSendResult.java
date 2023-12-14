package com.sadalsuud.push.domain.model.sms;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description 云片网短信调用发送接口返回值
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 * @Package com.sadalsuud.push.domain.model
 */

@NoArgsConstructor
@Data
public class YunPianSendResult {

    /**
     * totalCount
     */
    @JSONField(name = "total_count")
    private Integer totalCount;
    /**
     * totalFee
     */
    @JSONField(name = "total_fee")
    private String totalFee;
    /**
     * unit
     */
    @JSONField(name = "unit")
    private String unit;
    /**
     * data
     */
    @JSONField(name = "data")
    private List<DataDTO> data;

    /**
     * DataDTO
     */
    @NoArgsConstructor
    @Data
    public static class DataDTO {
        /**
         * httpStatusCode
         */
        @JSONField(name = "http_status_code")
        private Integer httpStatusCode;
        /**
         * code
         */
        @JSONField(name = "code")
        private Integer code;
        /**
         * msg
         */
        @JSONField(name = "msg")
        private String msg;
        /**
         * count
         */
        @JSONField(name = "count")
        private Integer count;
        /**
         * fee
         */
        @JSONField(name = "fee")
        private Integer fee;
        /**
         * unit
         */
        @JSONField(name = "unit")
        private String unit;
        /**
         * mobile
         */
        @JSONField(name = "mobile")
        private String mobile;
        /**
         * sid
         */
        @JSONField(name = "sid")
        private String sid;
    }
}