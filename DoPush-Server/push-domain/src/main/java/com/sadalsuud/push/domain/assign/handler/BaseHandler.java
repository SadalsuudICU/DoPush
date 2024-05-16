package com.sadalsuud.push.domain.assign.handler;

import cn.hutool.core.bean.BeanUtil;
import com.sadalsuud.push.common.domain.AnchorInfo;
import com.sadalsuud.push.common.domain.TaskInfo;
import com.sadalsuud.push.common.enums.AnchorState;
import com.sadalsuud.push.domain.assign.flowcontrol.FlowControlFactory;
import com.sadalsuud.push.domain.assign.flowcontrol.FlowControlParam;
import com.sadalsuud.push.domain.assign.model.task.FailedTask;
import com.sadalsuud.push.domain.assign.model.task.repository.IFailedTaskRepository;
import com.sadalsuud.push.domain.support.LogUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Description 各渠道的分发handler
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 11/12/2023
 * @Package com.sadalsuud.push.domain.pipeline.task.handler
 */

public abstract class BaseHandler implements Handler {
    /**
     * 标识渠道的Code
     * 子类初始化的时候指定
     */
    protected Integer channelCode;
    /**
     * 限流相关的参数
     * 子类初始化的时候指定
     */
    protected FlowControlParam flowControlParam;

    @Resource
    private HandlerHolder handlerHolder;
    @Resource
    private LogUtils logUtils;
    @Resource
    private FlowControlFactory flowControlFactory;
    @Resource
    private IFailedTaskRepository failedTaskRepository;

    /**
     * 初始化渠道与Handler的映射关系
     */
    @PostConstruct
    private void init() {
        handlerHolder.putHandler(channelCode, this);
    }


    @Override
    public void doHandler(TaskInfo taskInfo) {
        // 只有子类指定了限流参数，才需要限流
        if (Objects.nonNull(flowControlParam)) {
            flowControlFactory.flowControl(taskInfo, flowControlParam);
        }
        if (handler(taskInfo)) {
            logUtils.print(AnchorInfo.builder().state(AnchorState.SEND_SUCCESS.getCode()).bizId(taskInfo.getBizId()).messageId(taskInfo.getMessageId()).businessId(taskInfo.getBusinessId()).ids(taskInfo.getReceiver()).build());
            return;
        }
        // 失败则保存失败的任务信息 以便后续清洗显示和重发
        FailedTask failedTask = new FailedTask();
        BeanUtil.copyProperties(taskInfo, failedTask, true);
        failedTask.setId(taskInfo.getMessageId());
        // TODO 任务失败逻辑 MQ重试 最大次数后放弃重试落库
        failedTaskRepository.save(failedTask);
        logUtils.print(AnchorInfo.builder().state(AnchorState.SEND_FAIL.getCode()).bizId(taskInfo.getBizId()).messageId(taskInfo.getMessageId()).businessId(taskInfo.getBusinessId()).ids(taskInfo.getReceiver()).build());
    }


    /**
     * 统一处理的handler接口
     *
     * @param taskInfo
     * @return
     */
    public abstract boolean handler(TaskInfo taskInfo);


}
