package com.sadalsuud.push.client.api;

import com.sadalsuud.push.client.dto.MaterialParam;
import com.sadalsuud.push.client.dto.MessageTemplateParam;
import com.sadalsuud.push.common.vo.BasicResultVO;
import com.sadalsuud.push.domain.support.Material;
import com.sadalsuud.push.domain.template.MessageTemplate;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * @Description 素材上传接口
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/3/6
 * @Project DoPush-Server
 */


public interface MaterialService {


    /**
     * 钉钉素材上传
     *
     * @param file
     * @param sendAccount
     * @param fileType
     * @return
     */
    //BasicResultVO dingDingMaterialUpload(MultipartFile file, String sendAccount, String fileType);
    BasicResultVO dingDingMaterialUpload(MultipartFile file, String sendAccount, String fileType, String creator, String name);

    Page<Material> queryList(MaterialParam param);

    Material upload(MultipartFile file, String name, Integer type, String creator) throws Exception;

    void deleteByIds(List<Long> idList);

    Optional<Material> referenceByTemplate(String id);
}
