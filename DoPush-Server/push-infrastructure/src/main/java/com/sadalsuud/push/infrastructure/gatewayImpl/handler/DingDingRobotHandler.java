package com.sadalsuud.push.infrastructure.gatewayImpl.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import com.sadalsuud.push.common.constant.CommonConstant;
import com.sadalsuud.push.common.constant.DoPushConstant;
import com.sadalsuud.push.common.domain.RecallTaskInfo;
import com.sadalsuud.push.common.domain.TaskInfo;
import com.sadalsuud.push.common.dto.account.DingDingRobotAccount;
import com.sadalsuud.push.common.dto.model.DingDingRobotContentModel;
import com.sadalsuud.push.common.enums.ChannelType;
import com.sadalsuud.push.common.enums.SendMessageType;
import com.sadalsuud.push.domain.assign.handler.BaseHandler;
import com.sadalsuud.push.domain.assign.handler.Handler;
import com.sadalsuud.push.domain.gateway.AccountService;
import com.sadalsuud.push.domain.assign.model.dingding.DingDingRobotParam;
import com.sadalsuud.push.domain.assign.model.dingding.DingDingRobotResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 钉钉自定义机器人
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 20/12/2023
 * @Package com.sadalsuud.push.domain.assign.handler.impl
 */
@Slf4j
@Service
public class DingDingRobotHandler extends BaseHandler implements Handler {
    @Resource
    private AccountService accountService;

    public DingDingRobotHandler() {
        channelCode = ChannelType.DING_DING_ROBOT.getCode();
    }

    @Override
    public boolean handler(TaskInfo taskInfo) {
        try {
            DingDingRobotAccount account = accountService.getAccountById(taskInfo.getSendAccount(), DingDingRobotAccount.class);
            DingDingRobotParam dingDingRobotParam = assembleParam(taskInfo);
            String httpResult = HttpUtil.post(assembleParamUrl(account), JSON.toJSONString(dingDingRobotParam));
            DingDingRobotResult dingDingRobotResult = JSON.parseObject(httpResult, DingDingRobotResult.class);
            if (dingDingRobotResult.getErrCode() == 0) {
                return true;
            }
            // 常见的错误 应当 关联至 AnchorState,由后台统一透出失败原因
            log.error("DingDingHandler#handler fail!result:{},params:{}", JSON.toJSONString(dingDingRobotResult), JSON.toJSONString(taskInfo));
        } catch (Exception e) {
            log.error("DingDingHandler#handler fail!e:{},params:{}", Throwables.getStackTraceAsString(e), JSON.toJSONString(taskInfo));
        }
        return false;
    }


    private DingDingRobotParam assembleParam(TaskInfo taskInfo) {

        // 接收者相关
        DingDingRobotParam.AtVO atVo = DingDingRobotParam.AtVO.builder().build();
        if (DoPushConstant.SEND_ALL.equals(CollUtil.getFirst(taskInfo.getReceiver()))) {
            atVo.setIsAtAll(true);
        } else {
            atVo.setAtUserIds(new ArrayList<>(taskInfo.getReceiver()));
        }

        // 消息类型以及内容相关
        DingDingRobotContentModel contentModel = (DingDingRobotContentModel) taskInfo.getContentModel();
        DingDingRobotParam param = DingDingRobotParam.builder().at(atVo)
                .msgtype(SendMessageType.getDingDingRobotTypeByCode(contentModel.getSendType()))
                .build();
        if (SendMessageType.TEXT.getCode().equals(contentModel.getSendType())) {
            param.setText(DingDingRobotParam.TextVO.builder().content(contentModel.getContent()).build());
        }
        if (SendMessageType.MARKDOWN.getCode().equals(contentModel.getSendType())) {
            param.setMarkdown(DingDingRobotParam.MarkdownVO.builder().title(contentModel.getTitle()).text(contentModel.getContent()).build());
        }
        if (SendMessageType.LINK.getCode().equals(contentModel.getSendType())) {
            param.setLink(DingDingRobotParam.LinkVO.builder().title(contentModel.getTitle()).text(contentModel.getContent()).messageUrl(contentModel.getUrl()).picUrl(contentModel.getPicUrl()).build());
        }
        if (SendMessageType.NEWS.getCode().equals(contentModel.getSendType())) {
            List<DingDingRobotParam.FeedCardVO.LinksVO> linksVoS = JSON.parseArray(contentModel.getFeedCards(), DingDingRobotParam.FeedCardVO.LinksVO.class);
            DingDingRobotParam.FeedCardVO feedCardVO = DingDingRobotParam.FeedCardVO.builder().links(linksVoS).build();
            param.setFeedCard(feedCardVO);
        }
        if (SendMessageType.ACTION_CARD.getCode().equals(contentModel.getSendType())) {
            List<DingDingRobotParam.ActionCardVO.BtnsVO> btnsVoS = JSON.parseArray(contentModel.getBtns(), DingDingRobotParam.ActionCardVO.BtnsVO.class);
            DingDingRobotParam.ActionCardVO actionCardVO = DingDingRobotParam.ActionCardVO.builder().title(contentModel.getTitle()).text(contentModel.getContent()).btnOrientation(contentModel.getBtnOrientation()).btns(btnsVoS).build();
            param.setActionCard(actionCardVO);
        }

        return param;
    }

    /**
     * 拼装 url
     *
     * @param account
     * @return
     */
    private String assembleParamUrl(DingDingRobotAccount account) {
        long currentTimeMillis = System.currentTimeMillis();
        String sign = assembleSign(currentTimeMillis, account.getSecret());
        return (account.getWebhook() + "&timestamp=" + currentTimeMillis + "&sign=" + sign);
    }

    /**
     * 使用HmacSHA256算法计算签名
     *
     * @param currentTimeMillis
     * @param secret
     * @return
     */
    private String assembleSign(long currentTimeMillis, String secret) {
        String sign = "";
        try {
            String stringToSign = currentTimeMillis + String.valueOf(StrPool.C_LF) + secret;
            Mac mac = Mac.getInstance(CommonConstant.HMAC_SHA256_ENCRYPTION_ALGO);
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), CommonConstant.HMAC_SHA256_ENCRYPTION_ALGO));
            byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
            sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), CommonConstant.CHARSET_NAME);
        } catch (Exception e) {
            log.error("DingDingHandler#assembleSign fail!:{}", Throwables.getStackTraceAsString(e));
        }
        return sign;
    }


    /**
     * 钉钉自定义机器人 不支持撤回消息
     * https://open.dingtalk.com/document/group/custom-robot-access
     * @param recallTaskInfo
     */
    @Override
    public void recall(RecallTaskInfo recallTaskInfo) {

    }
}