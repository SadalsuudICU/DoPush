package com.sadalsuud.push.infrastructure.mq.sender.springeventbus;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 13/12/2023
 * @Package com.sadalsuud.push.infrastructure.mq.sender.springeventbus
 */
@Data
@Builder
public class DopushSpringEventSource implements Serializable {
    private String topic;
    private String jsonValue;
    private String tagId;
}
