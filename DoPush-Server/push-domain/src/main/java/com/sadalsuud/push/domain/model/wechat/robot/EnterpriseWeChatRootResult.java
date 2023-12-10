package com.sadalsuud.push.domain.model.wechat.robot;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 企业微信 机器人  返回值
 * https://developer.work.weixin.qq.com/document/path/91770#%E6%96%87%E6%9C%AC%E7%B1%BB%E5%9E%8B
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 * @Package com.sadalsuud.push.domain.model
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class EnterpriseWeChatRootResult {


    @JSONField(name = "errcode")
    private Integer errcode;
    @JSONField(name = "errmsg")
    private String errmsg;
    @JSONField(name = "type")
    private String type;
    @JSONField(name = "media_id")
    private String mediaId;
    @JSONField(name = "created_at")
    private String createdAt;
}
