package com.sadalsuud.push.infrastructure.gatewayImpl.repository;

import com.sadalsuud.push.infrastructure.gatewayImpl.repository.entity.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/3/26
 * @Project DoPush-Server
 */
public interface RequestLogDao extends JpaRepository<RequestLog, String> {

}
