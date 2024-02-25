package com.sadalsuud.push.domain.gateway;

/**
 * @Description 账号信息服务
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 11/12/2023
 * @Package com.sadalsuud.push.domain.gateway
 */
public interface AccountGateway {
    <T> T getAccountById(Integer sendAccountId, Class<T> clazz);

    /**
     * 通过脚本名 匹配到对应的短信账号
     *
     * @param scriptName 脚本名
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getSmsAccountByScriptName(String scriptName, Class<T> clazz);
}
