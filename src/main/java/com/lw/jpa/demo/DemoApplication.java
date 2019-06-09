package com.lw.jpa.demo;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author wei.liuw
 * @date 2019-6-5
 */
@SpringBootApplication
@EnableSwagger2
@EnableWebSocket
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication app  = new SpringApplication(DemoApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

}
