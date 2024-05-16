package com.sadalsuud.push.infrastructure.gatewayImpl.repository;

import com.sadalsuud.push.domain.assign.model.task.FailedTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/4/17
 * @Project DoPush-Server
 */
public interface FailedTaskDao extends JpaRepository<FailedTask, String>, JpaSpecificationExecutor<FailedTask> {

}
