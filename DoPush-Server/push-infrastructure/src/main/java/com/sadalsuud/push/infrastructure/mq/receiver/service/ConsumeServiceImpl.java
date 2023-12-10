package com.sadalsuud.push.infrastructure.mq.receiver.service;

import com.mysql.cj.util.LogUtils;
import com.sadalsuud.push.common.domain.AnchorInfo;
import com.sadalsuud.push.common.domain.LogParam;
import com.sadalsuud.push.common.domain.RecallTaskInfo;
import com.sadalsuud.push.common.domain.TaskInfo;
import com.sadalsuud.push.common.enums.AnchorState;
import com.sadalsuud.push.domain.gateway.ConsumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 * @Package com.sadalsuud.push.infrastructure.mq.receiver.service
 */
@Service
@RequiredArgsConstructor
public class ConsumeServiceImpl implements ConsumeService {
    private static final String LOG_BIZ_TYPE = "Receiver#consumer";
    private static final String LOG_BIZ_RECALL_TYPE = "Receiver#recall";
    //private ApplicationContext context;
    //
    //private TaskPendingHolder taskPendingHolder;
    //
    //private LogUtils logUtils;
    //
    //private HandlerHolder handlerHolder;

    @Override
    public void consume2Send(List<TaskInfo> taskInfoLists) {
        //String topicGroupId = GroupIdMappingUtils.getGroupIdByTaskInfo(CollUtil.getFirst(taskInfoLists.iterator()));
        //for (TaskInfo taskInfo : taskInfoLists) {
        //    logUtils.print(LogParam.builder().bizType(LOG_BIZ_TYPE).object(taskInfo).build(), AnchorInfo.builder().bizId(taskInfo.getBizId()).messageId(taskInfo.getMessageId()).ids(taskInfo.getReceiver()).businessId(taskInfo.getBusinessId()).state(AnchorState.RECEIVE.getCode()).build());
        //    Task task = context.getBean(Task.class).setTaskInfo(taskInfo);
        //    taskPendingHolder.route(topicGroupId).execute(task);
        //}
    }

    @Override
    public void consume2recall(RecallTaskInfo recallTaskInfo) {
        //logUtils.print(LogParam.builder().bizType(LOG_BIZ_RECALL_TYPE).object(recallTaskInfo).build());
        //handlerHolder.route(recallTaskInfo.getSendChannel()).recall(recallTaskInfo);
    }
}