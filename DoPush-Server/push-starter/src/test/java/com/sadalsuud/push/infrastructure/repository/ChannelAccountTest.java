package com.sadalsuud.push.infrastructure.repository;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.sadalsuud.push.PushHttpApplication;
import com.sadalsuud.push.domain.gateway.domain.ChannelAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;
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
        String emailConfig = getConfig("email", "qq");
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
        String tencentSmsConfig = getConfig("sms", "tencent");
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
        String aliSmsConfig = getConfig("sms", "ali");
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

    public static void main(String[] args) {
        System.out.println(getConfig("email", "qq"));
    }

    private static String getConfig(String type, String account) {
        String path = "C:\\Sadalsuud\\Projects\\Graduation-Project\\DoPush\\DoPush-Server\\accountConfig.json";
        FileReader fileReader = new FileReader(path);
        String s = fileReader.readString();
        JSONObject jsonObject = JSONUtil.parseObj(s);
        Object typeConfig = jsonObject.get(type);
        JSONArray objects = JSONUtil.parseArray(typeConfig);
        for (Object object : objects) {
            JSONObject jsonObject1 = JSONUtil.parseObj(object);
            if (account.equals(jsonObject1.get("name"))) {
                Object o = jsonObject1.get("config");
                if (Objects.nonNull(o)) {
                    return o.toString();
                }
                break;
            }
        }
        return null;
    }
}
