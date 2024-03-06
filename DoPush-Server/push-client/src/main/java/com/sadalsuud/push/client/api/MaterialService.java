package com.sadalsuud.push.client.api;

import com.sadalsuud.push.common.vo.BasicResultVO;
import org.springframework.web.multipart.MultipartFile;

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
    BasicResultVO dingDingMaterialUpload(MultipartFile file, String sendAccount, String fileType);
}
