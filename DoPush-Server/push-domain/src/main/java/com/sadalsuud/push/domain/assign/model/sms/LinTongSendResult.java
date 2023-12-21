package com.sadalsuud.push.domain.assign.model.sms;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description Form File
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 * @Package com.sadalsuud.push.domain.model
 * @see com.sadalsuud.push.domain.assign.model.sms
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinTongSendResult {

    Integer code;

    String message;
    @JSONField(name = "data")
    List<DataDTO> dtoList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DataDTO {
        Integer code;
        String message;
        Long msgId;
        String phone;
    }
}
