package com.sadalsuud.push.domain.support;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/4/21
 * @Project DoPush-Server
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Material {
    @Id
    private  Long id;

    private Integer type;

    private String name;

    private String path;

    private String creator;

    private Integer createTime;

    /**
     * 是否删除
     * 0：未删除
     * 1：已删除
     */
    private Integer isDeleted;
}
