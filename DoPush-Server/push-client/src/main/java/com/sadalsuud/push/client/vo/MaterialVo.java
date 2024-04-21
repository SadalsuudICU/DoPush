package com.sadalsuud.push.client.vo;

import com.sadalsuud.push.domain.support.Material;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/4/21
 * @Project DoPush-Server
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaterialVo {
    /**
     * 返回List列表
     */
    private List<Material> rows;

    /**
     * 总条数
     */
    private Long count;
}
