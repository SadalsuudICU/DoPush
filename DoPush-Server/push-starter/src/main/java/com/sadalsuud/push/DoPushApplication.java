package com.sadalsuud.push;

import com.sadalsuud.push.common.constant.DoPushConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.ansi.AnsiStyle;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description 传统hTTP web应用启动类
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 * @Package com.sadalsuud.push.starter
 */
@SpringBootApplication
@Slf4j
public class DoPushApplication implements CommandLineRunner {

    @Value("${server.port}")
    private String serverPort;
    public static void main(String[] args) {
        SpringApplication.run(DoPushApplication.class);
    }

    @Override
    public void run(String... args) {
        log.info(AnsiOutput.toString(DoPushConstant.PROJECT_BANNER, "\n", AnsiColor.GREEN, DoPushConstant.PROJECT_NAME, AnsiColor.DEFAULT, AnsiStyle.FAINT));
        log.info("Austin start succeeded, Index >> http://127.0.0.1:{}/", serverPort);
        log.info("Austin start succeeded, Swagger Url >> http://127.0.0.1:{}/swagger-ui/index.html", serverPort);
    }
}
