package com.sadalsuud.push.domain.assign.handler.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import com.sadalsuud.push.common.constant.SendChanelUrlConstant;
import com.sadalsuud.push.common.domain.RecallTaskInfo;
import com.sadalsuud.push.common.domain.TaskInfo;
import com.sadalsuud.push.common.dto.account.GeTuiAccount;
import com.sadalsuud.push.common.dto.model.PushContentModel;
import com.sadalsuud.push.common.enums.ChannelType;
import com.sadalsuud.push.domain.assign.handler.BaseHandler;
import com.sadalsuud.push.domain.assign.handler.Handler;
import com.sadalsuud.push.domain.gateway.AccountService;
import com.sadalsuud.push.domain.assign.model.push.PushParam;
import com.sadalsuud.push.domain.assign.model.push.getui.BatchSendPushParam;
import com.sadalsuud.push.domain.assign.model.push.getui.SendPushParam;
import com.sadalsuud.push.domain.assign.model.push.getui.SendPushResult;
import com.sadalsuud.push.domain.support.AccessTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @Description 通知栏消息发送处理 已经接入个推
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 19/12/2023
 * @Package com.sadalsuud.push.domain.assign.handler.impl
 */
@Component
@Slf4j
public class PushHandler extends BaseHandler implements Handler {

    private static final String HEADER_TOKEN_NAME = "token";

    @Resource
    private AccountService accountService;
    @Resource
    private AccessTokenUtils accessTokenUtils;

    public PushHandler() {
        channelCode = ChannelType.PUSH.getCode();
    }

    @Override
    public boolean handler(TaskInfo taskInfo) {

        try {
            GeTuiAccount account = accountService.getAccountById(taskInfo.getSendAccount(), GeTuiAccount.class);
            String token = accessTokenUtils.getAccessToken(taskInfo.getSendChannel(), taskInfo.getSendAccount(), account, false);
            PushParam pushParam = PushParam.builder().token(token).appId(account.getAppId()).taskInfo(taskInfo).build();

            String result;
            if (taskInfo.getReceiver().size() == 1) {
                result = singlePush(pushParam);
            } else {
                result = batchPush(createTaskId(pushParam), pushParam);
            }
            SendPushResult sendPushResult = JSON.parseObject(result, SendPushResult.class);
            if (sendPushResult.getCode().equals(0)) {
                return true;
            }
            // 常见的错误 应当 关联至 AnchorState,由后台统一透出失败原因
            log.error("PushHandler#handler fail!result:{},params:{}", JSON.toJSONString(sendPushResult), JSON.toJSONString(taskInfo));
        } catch (Exception e) {
            log.error("PushHandler#handler fail!e:{},params:{}", Throwables.getStackTraceAsString(e), JSON.toJSONString(taskInfo));
        }
        return false;
    }


    /**
     * 单推
     *
     * @param pushParam
     * @return http result
     */
    private String singlePush(PushParam pushParam) {
        String url = SendChanelUrlConstant.GE_TUI_BASE_URL + pushParam.getAppId() + SendChanelUrlConstant.GE_TUI_SINGLE_PUSH_PATH;
        SendPushParam sendPushParam = assembleParam((PushContentModel) pushParam.getTaskInfo().getContentModel(), pushParam.getTaskInfo().getReceiver());
        return HttpRequest.post(url).header(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue())
                .header(HEADER_TOKEN_NAME, pushParam.getToken())
                .body(JSON.toJSONString(sendPushParam))
                .timeout(2000)
                .execute().body();
    }


    /**
     * 批量推送
     *
     * @param taskId    个推 返回的任务Id
     * @param pushParam
     * @return
     */
    private String batchPush(String taskId, PushParam pushParam) {
        String url = SendChanelUrlConstant.GE_TUI_BASE_URL + pushParam.getAppId() + SendChanelUrlConstant.GE_TUI_BATCH_PUSH_PATH;
        BatchSendPushParam batchSendPushParam = BatchSendPushParam.builder()
                .taskId(taskId)
                .isAsync(true)
                .audience(BatchSendPushParam.AudienceVO.builder().cid(pushParam.getTaskInfo().getReceiver()).build()).build();
        return HttpRequest.post(url).header(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue())
                .header(HEADER_TOKEN_NAME, pushParam.getToken())
                .body(JSON.toJSONString(batchSendPushParam))
                .timeout(2000)
                .execute().body();
    }


    /**
     * 群推前需要构建taskId
     *
     * @param pushParam
     * @return http result
     */
    private String createTaskId(PushParam pushParam) {
        String url = SendChanelUrlConstant.GE_TUI_BASE_URL + pushParam.getAppId() + SendChanelUrlConstant.GE_TUI_BATCH_PUSH_CREATE_TASK_PATH;
        SendPushParam param = assembleParam((PushContentModel) pushParam.getTaskInfo().getContentModel());
        String taskId = "";
        try {
            String body = HttpRequest.post(url).header(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue())
                    .header(HEADER_TOKEN_NAME, pushParam.getToken())
                    .body(JSON.toJSONString(param))
                    .timeout(2000)
                    .execute().body();

            taskId = JSON.parseObject(body, SendPushResult.class).getData().getString("taskId");
        } catch (Exception e) {
            log.error("PushHandler#createTaskId fail :{},params:{}", Throwables.getStackTraceAsString(e), JSON.toJSONString(pushParam.getTaskInfo()));
        }

        return taskId;
    }

    private SendPushParam assembleParam(PushContentModel pushContentModel) {
        return assembleParam(pushContentModel, null);
    }

    private SendPushParam assembleParam(PushContentModel pushContentModel, Set<String> cid) {
        SendPushParam param = SendPushParam.builder()
                .requestId(String.valueOf(IdUtil.getSnowflake().nextId()))
                .pushMessage(SendPushParam.PushMessageVO.builder().notification(SendPushParam.PushMessageVO.NotificationVO.builder()
                                .title(pushContentModel.getTitle()).body(pushContentModel.getContent()).clickType("startapp").build())
                        .build())
                .build();
        if (CollUtil.isNotEmpty(cid)) {
            param.setAudience(SendPushParam.AudienceVO.builder().cid(cid).build());
        }
        return param;
    }


    @Override
    public void recall(RecallTaskInfo recallTaskInfo) {

    }
}
