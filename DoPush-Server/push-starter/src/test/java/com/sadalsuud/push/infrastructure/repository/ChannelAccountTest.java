package com.sadalsuud.push.infrastructure.repository;

import cn.hutool.core.date.DateUtil;
import com.sadalsuud.push.PushHttpApplication;
import com.sadalsuud.push.domain.gateway.domain.ChannelAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 11/12/2023
 * @Package com.sadalsuud.push.infrastructure.repository
 */
@SpringBootTest(classes = PushHttpApplication.class)
@RunWith(SpringRunner.class)
public class ChannelAccountTest {

    @Autowired
    private ChannelAccountDao channelAccountDao;

    @Test
    public void insert() {
        ChannelAccount channelAccount =
                ChannelAccount.builder()
                        .id(123123123L).name("test")
                        .sendChannel(10).accountConfig("test")
                        .creator("admin").created(Math.toIntExact(DateUtil.currentSeconds()))
                        .updated(Math.toIntExact(DateUtil.currentSeconds()))
                        .isDeleted(0)
                        .build();
        ChannelAccount save = channelAccountDao.save(channelAccount);
        System.out.println(channelAccountDao.findById(save.getId()).get());
    }

    @Test
    public void saveTestAccounts() {
        // 创建测试邮箱账号配置
        String emailConfig = "{\"host\":\"smtp.qq.com\",\"port\":465,\"user\":\"2094176918@qq.com\"," +
                "\"pass\":\"nivyfpseaanjbhjj\",\"from\":\"2094176918@qq.com\",\"starttlsEnable\":\"true\"," +
                "\"auth\":true,\"sslEnable\":true}";
        int intExact = Math.toIntExact(DateUtil.currentSeconds());
        ChannelAccount email =
                ChannelAccount.builder()
                        .id(123123123L).name("2094176918@qq.com")
                        .sendChannel(10).accountConfig(emailConfig)
                        .creator("test").created(intExact)
                        .updated(intExact)
                        .isDeleted(0)
                        .build();
        ChannelAccount save = channelAccountDao.save(email);
        Optional<ChannelAccount> saved = channelAccountDao.findById(save.getId());
        saved.ifPresent(System.out::println);
    }
}
