package com.sadalsuud.push.common.pipeline;

import lombok.Getter;

import java.util.List;

/**
 * @Description 业务执行模板（把责任链的逻辑串起来）
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 */
@Getter
public class ProcessTemplate {

    private List<BusinessProcess> processList;

    public void setProcessList(List<BusinessProcess> processList) {
        this.processList = processList;
    }
}