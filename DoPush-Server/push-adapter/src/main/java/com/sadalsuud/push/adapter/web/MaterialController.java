package com.sadalsuud.push.adapter.web;

import com.sadalsuud.push.adapter.facade.annotation.DoPushAspect;
import com.sadalsuud.push.client.api.MaterialService;
import com.sadalsuud.push.common.enums.ChannelType;
import com.sadalsuud.push.common.vo.BasicResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description 素材管理接口
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/3/6
 * @Project DoPush-Server
 */
@Slf4j
@DoPushAspect
@RestController
@RequestMapping("/material")
@Api("素材管理接口")
@RequiredArgsConstructor
public class MaterialController {

    private MaterialService materialService;


    /**
     * 素材上传接口
     *
     * @param file        文件内容
     * @param sendAccount 发送账号
     * @param sendChannel 发送渠道
     * @param fileType    文件类型
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("/素材上传接口")
    public BasicResultVO uploadMaterial(@RequestParam("file") MultipartFile file, String sendAccount, Integer sendChannel, String fileType) {
        if (ChannelType.DING_DING_WORK_NOTICE.getCode().equals(sendChannel)) {
            return materialService.dingDingMaterialUpload(file, sendAccount, fileType);
        }

        return BasicResultVO.fail();
    }

}