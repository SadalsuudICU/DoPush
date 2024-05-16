package com.sadalsuud.push.client.vo;

import com.sadalsuud.push.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/5/16
 * @Project DoPush-Server
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {

    private List<User> rows;

    private Long count;
}
