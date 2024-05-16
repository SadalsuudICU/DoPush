package com.sadalsuud.push.application.service;

import com.sadalsuud.push.client.api.FailedTaskService;
import com.sadalsuud.push.client.dto.FailedTaskParam;
import com.sadalsuud.push.domain.assign.model.task.FailedTask;
import com.sadalsuud.push.infrastructure.gatewayImpl.repository.FailedTaskDao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/5/16
 * @Project DoPush-Server
 */
@Service
@RequiredArgsConstructor
public class FailedTaskServiceImpl implements FailedTaskService {

    private final FailedTaskDao failedTaskDao;

    @Override
    public Page<FailedTask> search(FailedTaskParam param) {
        PageRequest pageRequest = PageRequest.of(param.getPage() - 1, param.getPerPage());
        return failedTaskDao.findAll((Specification<FailedTask>) (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            // 加搜索条件
            predicateList.add(cb.equal(root.get("messageTemplateId").as(Integer.class), param.getMessageTemplateId()));
            Predicate[] p = new Predicate[predicateList.size()];
            // 查询
            query.where(cb.and(predicateList.toArray(p)));
            // 排序
            query.orderBy(cb.desc(root.get("time")));
            return query.getRestriction();
        }, pageRequest);
    }
}
