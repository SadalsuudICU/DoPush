package com.sadalsuud.push.client.api;

import com.sadalsuud.push.client.dto.FailedTaskParam;
import com.sadalsuud.push.domain.assign.model.task.FailedTask;
import org.springframework.data.domain.Page;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/5/16
 * @Project DoPush-Server
 */
public interface FailedTaskService {
    Page<FailedTask> search(FailedTaskParam param);

}
