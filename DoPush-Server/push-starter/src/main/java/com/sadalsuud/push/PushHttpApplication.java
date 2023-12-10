package com.sadalsuud.push;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description 传统hTTP web应用启动类
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 * @Package com.sadalsuud.push.starter
 */
@SpringBootApplication
//@Slf4j
public class PushHttpApplication {

    @Value("${server.port}")
    private String serverPort;
    public static void main(String[] args) {
        SpringApplication.run(PushHttpApplication.class);
    }
}
