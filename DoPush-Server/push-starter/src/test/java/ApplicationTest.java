import com.sadalsuud.push.domain.gateway.SendMqService;
import com.sadalsuud.push.PushHttpApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 * @Package PACKAGE_NAME
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PushHttpApplication.class)
public class ApplicationTest {
    @Resource
    private SendMqService sendMqService;

    @Test
    public void test() {
        System.out.println(sendMqService);
    }
}
