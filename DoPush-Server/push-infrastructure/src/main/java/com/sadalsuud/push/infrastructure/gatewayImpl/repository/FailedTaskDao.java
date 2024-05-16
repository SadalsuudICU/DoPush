package com.sadalsuud.push.infrastructure.gatewayImpl.repository;

import com.sadalsuud.push.domain.assign.model.task.FailedTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/4/17
 * @Project DoPush-Server
 */
public interface FailedTaskDao extends JpaRepository<FailedTask, String>, JpaSpecificationExecutor<FailedTask> {

    //@Modifying
    //@Query("Select ft from FailedTask ft where ft.bizId = :bizId " +
    //        "and ft.businessId = :businessId " +
    //        "and ft.messageId = :messageId " +
    //        "and ft.messageTemplateId = :messageTemplateId " +
    //        "and ft.receiver = :receiver and ft.time between :startTime and :endTime ")
    //List<FailedTask> search(String bizId, Long businessId,
    //                        String messageId, Long messageTemplateId,
    //                        String receiver, int startTime, int endTime);

    List<FailedTask> findFailedTaskByMessageTemplateId(Long messageTemplateId);
}
