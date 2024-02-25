package com.sadalsuud.push.infrastructure.gatewayImpl.config;

import cn.hutool.setting.dialect.Props;
import com.sadalsuud.push.domain.gateway.ConfigGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * 读取配置实现类
 *
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 11/12/2023
 * @Package com.sadalsuud.push.infrastructure.config
 */
@Service
@RequiredArgsConstructor
public class ConfigGatewayImpl implements ConfigGateway {

    /**
     * 本地配置
     */
    private static final String PROPERTIES_PATH = "local.properties";
    private final Props props = new Props(PROPERTIES_PATH, StandardCharsets.UTF_8);

    /**
     * apollo配置
     */
    @Value("${apollo.bootstrap.enabled}")
    private Boolean enableApollo;
    @Value("${apollo.bootstrap.namespaces}")
    private String namespaces;
    /**
     * nacos配置
     */
    @Value("${dopush.nacos.enabled}")
    private Boolean enableNacos;

    //private final NacosUtils nacosUtils;


    @Override
    public String getProperty(String key, String defaultValue) {
        //if (Boolean.TRUE.equals(enableApollo)) {
        //    Config config = com.ctrip.framework.apollo.ConfigService.getConfig(namespaces.split(StrPool.COMMA)[0]);
        //    return config.getProperty(key, defaultValue);
        //} else if (Boolean.TRUE.equals(enableNacos)) {
        //    return nacosUtils.getProperty(key, defaultValue);
        //} else {
        //    return props.getProperty(key, defaultValue);
        //}
        return props.getProperty(key, defaultValue);
    }
}
