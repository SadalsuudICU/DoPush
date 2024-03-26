package com.sadalsuud.push.infrastructure.gatewayImpl.repository;

import com.sadalsuud.push.domain.template.MessageTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 11/12/2023
 * @Package com.sadalsuud.push.infrastructure.repository
 */
@Repository
public interface MessageTemplateDao extends JpaRepository<MessageTemplate, Long>, JpaSpecificationExecutor<MessageTemplate> {


    /**
     * 查询 列表（分页)
     *
     * @param deleted  0：未删除 1：删除
     * @param pageable 分页对象
     * @return
     */
    List<MessageTemplate> findAllByIsDeletedEqualsOrderByUpdatedDesc(Integer deleted, Pageable pageable);


    /**
     * 统计未删除的条数
     *
     * @param deleted
     * @return
     */
    Long countByIsDeletedEquals(Integer deleted);


    List<MessageTemplate> findAllByAuditStatusEqualsOrderByUpdatedDesc(Integer auditStatus, Pageable pageable);



    @Modifying
    @Query("update MessageTemplate m set m.auditStatus = :afterStateA, m.msgStatus = :afterStateM, m.updater = :updater, m.updated = :updated where m.id = :id and m.auditStatus = :beforeStateA and m.msgStatus = :beforeStateM")
    int alertState(Long id, Integer beforeStateA, Integer afterStateA, Integer beforeStateM, Integer afterStateM, String updater, int updated);

}
