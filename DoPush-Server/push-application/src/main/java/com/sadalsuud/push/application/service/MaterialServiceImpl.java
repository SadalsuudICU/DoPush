package com.sadalsuud.push.application.service;

import cn.hutool.core.util.IdUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMediaUploadRequest;
import com.dingtalk.api.response.OapiMediaUploadResponse;
import com.google.common.base.Throwables;
import com.sadalsuud.push.client.api.MaterialService;
import com.sadalsuud.push.client.vo.UploadResponseVo;
import com.sadalsuud.push.common.constant.SendChanelUrlConstant;
import com.sadalsuud.push.common.dto.account.DingDingWorkNoticeAccount;
import com.sadalsuud.push.common.enums.ChannelType;
import com.sadalsuud.push.common.enums.EnumUtil;
import com.sadalsuud.push.common.enums.FileType;
import com.sadalsuud.push.common.enums.RespStatusEnum;
import com.sadalsuud.push.common.vo.BasicResultVO;
import com.sadalsuud.push.domain.channel.AccountService;
import com.sadalsuud.push.infrastructure.gatewayImpl.handler.AccessTokenUtils;
import com.taobao.api.FileItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/3/6
 * @Project DoPush-Server
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {
    private AccountService accountUtils;

    private AccessTokenUtils accessTokenUtils;


    @Override
    public BasicResultVO dingDingMaterialUpload(MultipartFile file, String sendAccount, String fileType) {
        OapiMediaUploadResponse rsp;
        try {
            DingDingWorkNoticeAccount account = accountUtils.getAccountById(Integer.valueOf(sendAccount), DingDingWorkNoticeAccount.class);
            String accessToken = accessTokenUtils.getAccessToken(ChannelType.DING_DING_WORK_NOTICE.getCode(), Integer.valueOf(sendAccount), account, false);
            DingTalkClient client = new DefaultDingTalkClient(SendChanelUrlConstant.DING_DING_UPLOAD_URL);
            OapiMediaUploadRequest req = new OapiMediaUploadRequest();
            FileItem item = new FileItem(IdUtil.fastSimpleUUID() + file.getOriginalFilename(),
                    file.getInputStream());
            req.setMedia(item);
            req.setType(EnumUtil.getDescriptionByCode(Integer.valueOf(fileType), FileType.class));
            rsp = client.execute(req, accessToken);
            if (rsp.isSuccess()) {
                return BasicResultVO.success(UploadResponseVo.builder().id(rsp.getMediaId()).build());
            }
            log.error("MaterialService#dingDingMaterialUpload fail:{}", rsp.getErrmsg());
        } catch (Exception e) {
            log.error("MaterialService#dingDingMaterialUpload fail:{}", Throwables.getStackTraceAsString(e));
        }
        return BasicResultVO.fail(RespStatusEnum.SERVICE_ERROR.getMsg());
    }
}
