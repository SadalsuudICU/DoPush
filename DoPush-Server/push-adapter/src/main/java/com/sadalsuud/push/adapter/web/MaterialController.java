package com.sadalsuud.push.adapter.web;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import com.sadalsuud.push.adapter.facade.annotation.DoPushAspect;
import com.sadalsuud.push.client.api.MaterialService;
import com.sadalsuud.push.client.dto.MaterialParam;
import com.sadalsuud.push.client.vo.MaterialVo;
import com.sadalsuud.push.common.enums.ChannelType;
import com.sadalsuud.push.common.vo.BasicResultVO;
import com.sadalsuud.push.domain.support.Material;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
@Api(tags = {"素材管理接口"})
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;


    /**
     * 钉钉素材上传直接发送接口
     *
     * @param file        文件内容
     * @param sendAccount 发送账号
     * @param sendChannel 发送渠道
     * @param fileType    文件类型
     * @return
     */
    @PostMapping("/dingDingUpload")
    @ApiOperation("/钉钉素材上传发送接口")
    public BasicResultVO uploadDingDingMaterial(@RequestParam("file") MultipartFile file, String sendAccount, Integer sendChannel, String fileType, String creator, String name) {
        if (ChannelType.DING_DING_WORK_NOTICE.getCode().equals(sendChannel)) {
            return materialService.dingDingMaterialUpload(file, sendAccount, fileType, creator, name);
        }

        return materialService.dingDingMaterialUpload(file, sendAccount, fileType, creator, name);
    }

    /**
     * 素材上传接口
     * @param file
     * @param name
     * @param fileType
     * @param creator
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("/钉钉素材上传发送接口")
    public BasicResultVO uploadMaterial(@RequestParam("file") MultipartFile file, String name, String fileType, String creator) {
        Optional<Material> upload = materialService.upload(file, name, Integer.valueOf(fileType), creator);
        if (upload.isPresent()) {
            return BasicResultVO.success(upload.get());
        }

        return BasicResultVO.fail("upload material failed");
    }


    /**
     * 素材列表接口
     */
    @GetMapping("/list")
    @ApiOperation("/素材列表")
    public MaterialVo materialList(@Validated MaterialParam param) {
        Page<Material> materials = materialService.queryList(param);
        return MaterialVo.builder()
                .count(materials.getTotalElements())
                .rows(materials.toList())
                .build();
    }

    /**
     * 根据Id删除
     * id多个用逗号分隔开
     */
    @DeleteMapping("delete/{id}")
    @ApiOperation("/根据Ids删除")
    public void deleteByIds(@PathVariable("id") String id) {
        if (CharSequenceUtil.isNotBlank(id)) {
            List<Long> idList = Arrays.stream(id.split(StrPool.COMMA)).map(Long::valueOf).collect(Collectors.toList());
            materialService.deleteByIds(idList);
        }
    }

    /**
     * 模板根据Id引用素材
     * id多个用逗号分隔开
     */
    @PostMapping("reference/{id}")
    @ApiOperation("/模板根据Id引用素材")
    public BasicResultVO reference(@PathVariable("id") String id) {
        if (CharSequenceUtil.isBlank(id)) {
            return BasicResultVO.fail("please input id to get material");
        }
        Optional<Material> material = materialService.referenceByTemplate(id);
        if (material.isPresent()) {
            return BasicResultVO.success(material.get());
        }
        return BasicResultVO.fail("no such material!") ;
    }
}