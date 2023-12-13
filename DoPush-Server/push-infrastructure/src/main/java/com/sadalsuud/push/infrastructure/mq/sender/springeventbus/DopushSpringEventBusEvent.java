package com.sadalsuud.push.infrastructure.mq.sender.springeventbus;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @Description spring 消息事件
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 13/12/2023
 * @Package com.sadalsuud.push.infrastructure.mq.sender.springeventbus
 */
@Getter
public class DopushSpringEventBusEvent extends ApplicationEvent {

    private final DopushSpringEventSource dopushSpringEventSource;


    public DopushSpringEventBusEvent(Object source, DopushSpringEventSource dopushSpringEventSource) {
        super(source);
        this.dopushSpringEventSource = dopushSpringEventSource;
    }
}
