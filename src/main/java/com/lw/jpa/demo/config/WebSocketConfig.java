package com.lw.jpa.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 配置 websocket 的config。
 * @author wei.liuw
 * @date 2019/6/9
 */
@Configuration
public class WebSocketConfig {

    /**
     * 配置
     * @return ServerEndpointExporter
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
