package com.sadalsuud.push.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/5/16
 * @Project DoPush-Server
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FailedTaskParam {

    /**
     * 当前页码
     */
    @NotNull
    private Integer page = 1;

    /**
     * 当前页大小
     */
    @NotNull
    private Integer perPage = 10;

    /**
     * 消息模板ID
     */
    @NotNull
    private Long messageTemplateId;
}
