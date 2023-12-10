package com.sadalsuud.push.domain.model.sms;

import lombok.Builder;
import lombok.Data;

/**
 * @Description Form File
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 * @Package com.sadalsuud.push.domain.model
 * @see com.sadalsuud.push.domain.model.sms
 */
@Data
@Builder
public class LinTongSendMessage {
    String phone;
    String content;
}
