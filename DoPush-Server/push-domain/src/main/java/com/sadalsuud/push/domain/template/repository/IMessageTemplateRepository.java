package com.sadalsuud.push.domain.template.repository;

import com.sadalsuud.push.domain.support.gateway.domain.MessageTemplate;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 31/12/2023
 * @Project DoPush-Server
 */
public interface IMessageTemplateRepository {

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

    Optional<MessageTemplate> findById(Long messageTemplateId);
}

