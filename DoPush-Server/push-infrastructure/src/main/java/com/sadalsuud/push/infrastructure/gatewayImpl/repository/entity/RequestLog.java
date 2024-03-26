package com.sadalsuud.push.infrastructure.gatewayImpl.repository.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

/**
 * @Description 请求日志DTO
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 12/12/2023
 * @Package com.sadalsuud.push.client.dto
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RequestLog {

    /**
     * 请求ID（UUID）与 ResponseLogVo id 一致
     */
    @Id
    private String id;

    /**
     * 接口URI
     */
    @JSONField(ordinal = 1)
    private String uri;

    /**
     * 请求方法
     */
    @JSONField(ordinal = 2)
    private String method;

    /**
     * 参数数组
     */
    @JSONField(ordinal = 3)
    private String args;

    /**
     * 是否需要认证
     */
    @JSONField(ordinal = 4)
    private Integer auth;

    /**
     * 认证令牌
     */
    @JSONField(ordinal = 5)
    private String token;

    /**
     * 登录账号信息
     */
    @JSONField(ordinal = 6)
    private String loginAccount;

    /**
     * 产品
     */
    @JSONField(ordinal = 7)
    private String product;

    /**
     * 类名+方法名
     */
    @JSONField(ordinal = 8)
    private String path;

    /**
     * 页面引用
     */
    @JSONField(ordinal = 9)
    private String referer;

    /**
     * 请求地址
     */
    @JSONField(ordinal = 10)
    private String remoteAddr;

    /**
     * 用户代理（浏览器）
     */
    @JSONField(ordinal = 11)
    private String userAgent;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        RequestLog that = (RequestLog) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
