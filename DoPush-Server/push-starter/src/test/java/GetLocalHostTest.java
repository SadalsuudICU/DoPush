import com.sadalsuud.push.PushHttpApplication;
import com.sadalsuud.push.domain.gateway.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 14/12/2023
 * @Package PACKAGE_NAME
 */
@SpringBootTest(classes = PushHttpApplication.class)
@RunWith(SpringRunner.class)
public class GetLocalHostTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void getLocalHostByInetAddress() throws UnknownHostException {
        long start = System.currentTimeMillis();
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println("localHost.getHostAddress() = " + localHost.getHostAddress());
        System.out.println("localHost.getCanonicalHostName() = " + localHost.getCanonicalHostName());
        System.out.println("localHost.getAddress() = " + Arrays.toString(localHost.getAddress()));
        System.out.println("localHost.getHostName() = " + localHost.getHostName());
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    public static void main(String[] args) throws UnknownHostException {
        new GetLocalHostTest().getLocalHostByInetAddress();
    }

}
