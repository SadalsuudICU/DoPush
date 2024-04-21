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
 * @Date 2024/4/21
 * @Project DoPush-Server
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaterialParam {
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
     * 素材ID
     */
    private Long id;

    /**
     * 当前用户
     */
    private String creator;


    /**
     * 模版名称
     */
    private String keywords;
}
