package com.sadalsuud.push.infrastructure.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 14/12/2023
 * @Package com.sadalsuud.push.infrastructure.utils
 */
@Component
@Slf4j
public class LoginUtils {

    //@Autowired
    //private ApplicationContext applicationContext;
    //
    //@Value("${spring.profiles.active}")
    //private String env;
    //
    ///**
    // * 测试环境 使用
    // * 获取 WeChatLoginConfig 对象
    // *
    // * @return
    // */
    //public WeChatLoginConfig getLoginConfig() {
    //    try {
    //        return applicationContext.getBean(OfficialAccountParamConstant.WE_CHAT_LOGIN_CONFIG, WeChatLoginConfig.class);
    //    } catch (Exception e) {
    //        return null;
    //    }
    //}
    //
    ///**
    // * 测试环境使用
    // * 判断是否需要登录
    // *
    // * @return
    // */
    //public boolean needLogin() {
    //    try {
    //        WeChatLoginConfig bean = applicationContext.getBean(OfficialAccountParamConstant.WE_CHAT_LOGIN_CONFIG, WeChatLoginConfig.class);
    //        if (CommonConstant.ENV_TEST.equals(env) && Objects.nonNull(bean)) {
    //            return true;
    //        }
    //    } catch (Exception e) {
    //        log.error("LoginUtils#needLogin fail:{}", Throwables.getStackTraceAsString(e));
    //    }
    //    return false;
    //}
}

