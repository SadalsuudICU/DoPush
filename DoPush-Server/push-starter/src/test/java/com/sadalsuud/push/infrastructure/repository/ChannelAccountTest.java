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
        Optional<ChannelAccount> saved = channelAccountDao.findById(save.getId());
        saved.ifPresent(System.out::println);
    }

    @Test
    public void saveQQEmailTestAccounts() {
        // 创建测试邮箱账号配置
        // QQ邮箱密钥 nivyfpseaanjbhjj
        String emailConfig = "{\"host\":\"smtp.qq.com\",\"port\":465,\"user\":\"2094176918@qq.com\"," +
                "\"pass\":\"nivyfpseaanjbhjj\",\"from\":\"2094176918@qq.com\",\"starttlsEnable\":\"true\"," +
                "\"auth\":true,\"sslEnable\":true}";
        int intExact = Math.toIntExact(DateUtil.currentSeconds());
        ChannelAccount email =
                ChannelAccount.builder()
                        .id(123123123L).name("2094176918@qq.com")
                        .sendChannel(40).accountConfig(emailConfig)
                        .creator("test").created(intExact)
                        .updated(intExact)
                        .isDeleted(0)
                        .build();
        ChannelAccount save = channelAccountDao.save(email);
        Optional<ChannelAccount> saved = channelAccountDao.findById(save.getId());
        saved.ifPresent(System.out::println);
    }

    @Test
    public void saveSmsTestAccounts() {
        //SecretId:AKIDTrBOdr9XzRwU6lWBkJi1CFD2pQvIi1IQ
        //SecretKey:BMZmpkTzHMDqGRM1Tj1CD2hCUYSCj28D
        //smsSdkAppId:1400876387
        String tencentSmsConfig = "{\n" +
                "    \"url\": \"sms.tencentcloudapi.com\", " +
                "    \"region\": \"ap-guangzhou\",  " +
                "    \"secretId\": \"AKIDTrBOdr9XzRwU6lWBkJi1CFD2pQvIi1IQ\", " +
                "    \"secretKey\": \"BMZmpkTzHMDqGRM1Tj1CD2hCUYSCj28D\", " +
                "    \"smsSdkAppId\": \"1400876387\", " +
                "    \"templateId\": \"2019523\",  " +
                "    \"signName\": \"Sadalsuud公众号\", " +
                "    \"supplierId\": 10, " +
                "    \"supplierName\": \"腾讯云\", " +
                "    \"scriptName\": \"TencentSmsScript\" " +
                "}";
        int intExact = Math.toIntExact(DateUtil.currentSeconds());
        ChannelAccount sms =
                ChannelAccount.builder()
                        .id(123213123L).name("Sadalsuud")
                        .sendChannel(30).accountConfig(tencentSmsConfig)
                        .creator("test").created(intExact)
                        .updated(intExact).isDeleted(0)
                        .build();
        ChannelAccount save = channelAccountDao.save(sms);
        Optional<ChannelAccount> saved = channelAccountDao.findById(save.getId());
        saved.ifPresent(System.out::println);
    }

    @Test
    public void saveAliSmsTestAccounts() {
        // accessKeyId: LTAI5tGym1AXxRYXJrZdebfY
        // accessKeySecret: 8cviYaQcjB8ixTwGJngYUPn2CLgZaL
        String aliSmsConfig = "{" +
                "\"endpoint\": \"dysmsapi.aliyuncs.com\",  " +
                "\"accessKeyId\": \"LTAI5tGym1AXxRYXJrZdebfY\", " +
                "\"secretKey\": \"8cviYaQcjB8ixTwGJngYUPn2CLgZaL\", " +
                "\"templateId\": \"SMS_464385075\",  " +
                "\"signName\": \"Sadalsuud\", " +
                "\"supplierId\": 20, " +
                "\"supplierName\": \"阿里云\", " +
                "\"scriptName\": \"AliSmsScript\" " +
                "}";
        int intExact = Math.toIntExact(DateUtil.currentSeconds());
        ChannelAccount sms =
                ChannelAccount.builder()
                        .id(123213123L).name("Sadalsuud")
                        .sendChannel(30).accountConfig(aliSmsConfig)
                        .creator("test").created(intExact)
                        .updated(intExact).isDeleted(0)
                        .build();
        ChannelAccount save = channelAccountDao.save(sms);
        Optional<ChannelAccount> saved = channelAccountDao.findById(save.getId());
        saved.ifPresent(System.out::println);
    }
}
