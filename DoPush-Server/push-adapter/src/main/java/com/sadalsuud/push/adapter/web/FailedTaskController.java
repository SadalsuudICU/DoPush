package com.sadalsuud.push.adapter.web;

import com.sadalsuud.push.adapter.facade.annotation.DoPushAspect;
import com.sadalsuud.push.adapter.facade.annotation.DoPushResult;
import com.sadalsuud.push.client.api.FailedTaskService;
import com.sadalsuud.push.client.dto.FailedTaskParam;
import com.sadalsuud.push.client.vo.FailedTaskVo;
import com.sadalsuud.push.domain.assign.model.task.FailedTask;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/5/16
 * @Project DoPush-Server
 */
@Slf4j
@DoPushAspect
@DoPushResult
@RestController
@RequestMapping("/failedTask")
@Api(tags = {"失败任务信息管理"})
@RequiredArgsConstructor
public class FailedTaskController {

    private final FailedTaskService failedTaskService;

    /**
     * 列表数据
     */
    @GetMapping("/list")
    @ApiOperation("/列表页")
    public FailedTaskVo queryList(@Validated FailedTaskParam failedTaskParam) {

        Page<FailedTask> messageTemplates = failedTaskService.search(failedTaskParam);
        return FailedTaskVo.builder()
                .count(messageTemplates.getTotalElements())
                .rows(messageTemplates.toList())
                .build();
    }
}
