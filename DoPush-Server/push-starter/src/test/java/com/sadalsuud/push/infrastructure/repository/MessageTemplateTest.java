package com.sadalsuud.push.infrastructure.repository;

import cn.hutool.core.date.DateUtil;
import com.sadalsuud.push.PushHttpApplication;
import com.sadalsuud.push.domain.gateway.domain.MessageTemplate;
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
 * @Date 13/12/2023
 * @Package com.sadalsuud.push.infrastructure.repository
 */
@SpringBootTest(classes = PushHttpApplication.class)
@RunWith(SpringRunner.class)
public class MessageTemplateTest {
    @Autowired
    private MessageTemplateDao messageTemplateDao;

    @Test
    public void saveTemplate() {
        int intExact = Math.toIntExact(DateUtil.currentSeconds());
        MessageTemplate messageTemplate =
                MessageTemplate.builder()
                        .id(111222333L).name("email test")
                        .auditStatus(20).flowId("123321")
                        .msgStatus(30).idType(50)
                        .sendChannel(40).templateType(20)
                        .msgType(10).shieldType(10)
                        .sendAccount(1).msgContent("dopush test")
                        .creator("sadalsuud").updator("sadalsuud")
                        .auditor("sadalsuud").team("api").proposer("biz")
                        .isDeleted(0).created(intExact)
                        .updated(intExact).build();
        MessageTemplate save = messageTemplateDao.save(messageTemplate);
        Optional<MessageTemplate> saved = messageTemplateDao.findById(save.getId());
        saved.ifPresent(System.out::println);

    }
}
