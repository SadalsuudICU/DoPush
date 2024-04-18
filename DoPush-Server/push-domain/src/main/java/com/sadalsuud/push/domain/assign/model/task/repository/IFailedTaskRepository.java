package com.sadalsuud.push.domain.assign.model.task.repository;

import com.sadalsuud.push.domain.assign.model.task.FailedTask;

import java.util.List;
import java.util.Optional;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/4/17
 * @Project DoPush-Server
 */
public interface IFailedTaskRepository {
    Optional<FailedTask> save(FailedTask failedTask);

    List<FailedTask> search(FailedTask failedTask);
}
