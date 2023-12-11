package com.sadalsuud.push.infrastructure.config;

import com.sadalsuud.push.domain.gateway.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 11/12/2023
 * @Package com.sadalsuud.push.infrastructure.config
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    //private final ChannelAccountDao channelAccountDao;
    //private final StringRedisTemplate redisTemplate;

    @Override
    public <T> T getAccountById(Integer sendAccountId, Class<T> clazz) {
        return null;
    }

    /**
     * 通过脚本名 匹配到对应的短信账号
     *
     * @param scriptName 脚本名
     * @param clazz
     * @return
     */
    @Override
    public <T> T getSmsAccountByScriptName(String scriptName, Class<T> clazz) {
        return null;
    }
}
