package com.sadalsuud.push.client.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 素材上传成功后返回id
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/3/6
 * @Project DoPush-Server
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadResponseVo {
    private String id;
}
