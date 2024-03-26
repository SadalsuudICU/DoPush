package com.sadalsuud.push.infrastructure.gatewayImpl.repository.impl;

import com.sadalsuud.push.domain.template.MessageTemplate;
import com.sadalsuud.push.domain.template.repository.IMessageTemplateRepository;
import com.sadalsuud.push.infrastructure.gatewayImpl.repository.MessageTemplateDao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 11/12/2023
 * @Package com.sadalsuud.push.infrastructure.repository.impl
 */
@Repository
@RequiredArgsConstructor
public class MessageTemplateRepositoryImpl implements IMessageTemplateRepository {
    private final MessageTemplateDao messageTemplateDao;

    /**
     * 查询 列表（分页)
     *
     * @param deleted  0：未删除 1：删除
     * @param pageable 分页对象
     * @return
     */
    @Override
    public List<MessageTemplate> findAllByIsDeletedEqualsOrderByUpdatedDesc(Integer deleted, Pageable pageable) {
        return messageTemplateDao.findAllByIsDeletedEqualsOrderByUpdatedDesc(deleted, pageable);
    }

    /**
     * 统计未删除的条数
     *
     * @param deleted
     * @return
     */
    @Override
    public Long countByIsDeletedEquals(Integer deleted) {
        return messageTemplateDao.countByIsDeletedEquals(deleted);
    }

    @Override
    public Optional<MessageTemplate> findById(Long messageTemplateId) {
        return messageTemplateDao.findById(messageTemplateId);
    }

    @Override
    public Optional<MessageTemplate> save(MessageTemplate messageTemplate) {
        return Optional.of(messageTemplateDao.save(messageTemplate));
    }

    @Override
    public boolean alertState(Long id, Integer beforeStateA, Integer afterStateA, Integer beforeStateM, Integer afterStateM, String updater, int updated) {
        return messageTemplateDao.alertState(id, beforeStateA, afterStateA, beforeStateM, afterStateM, updater, updated) == 1;
    }
}
