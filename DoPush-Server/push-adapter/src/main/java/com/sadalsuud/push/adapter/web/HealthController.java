package com.sadalsuud.push.adapter.web;

import com.sadalsuud.push.adapter.facade.annotation.DoPushAspect;
import com.sadalsuud.push.adapter.facade.annotation.DoPushResult;
import com.sadalsuud.push.common.vo.BasicResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 12/12/2023
 * @Package com.sadalsuud.push.adapter.web
 */
@Slf4j
@DoPushAspect
@DoPushResult
@RestController
@Api(tags = {"健康检测"})
public class HealthController {
    @GetMapping("/")
    @ApiOperation("/健康检测")
    public BasicResultVO<String> health() {
        return BasicResultVO.success("healthy");
    }
}