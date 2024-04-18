package com.sadalsuud.push.infrastructure.gatewayImpl.repository.impl;

import cn.hutool.core.date.DateUtil;
import com.sadalsuud.push.domain.assign.model.task.FailedTask;
import com.sadalsuud.push.domain.assign.model.task.repository.IFailedTaskRepository;
import com.sadalsuud.push.infrastructure.gatewayImpl.repository.FailedTaskDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/4/17
 * @Project DoPush-Server
 */
@Repository
@RequiredArgsConstructor
public class FailedTaskRepositoryImpl implements IFailedTaskRepository {
    private final FailedTaskDao failedTaskDao;


    @Override
    public Optional<FailedTask> save(FailedTask failedTask) {
        failedTask.setTime(Math.toIntExact(DateUtil.currentSeconds()));
        return Optional.of(failedTaskDao.save(failedTask));
    }

    @Override
    public List<FailedTask> search(FailedTask failedTask) {
        return null;
    }
}
