package com.sadalsuud.push.adapter.web;

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
@RestController
@Api(tags = {"健康检测"})
public class HealthController {
    @GetMapping("/")
    @ApiOperation("/健康检测")
    public String health() {
        return "success";
    }
}