package com.lw.jpa.demo.controller;

import com.lw.jpa.demo.websocket.MyChartWebSocketDemo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wei.liuw
 * @date 2019/6/9
 */
@Slf4j
@Controller
@RequestMapping("/chart")
public class WebSocketChartController {

    @GetMapping("/login")
    public String login() {
        return "chart";
    }

    @ResponseBody
    @RequestMapping(value = "/send/{sId}")
    public Object sendMessage(@PathVariable("sId") String sId, String message) {
        log.info("sId = " + sId + ", message=" + message);
        MyChartWebSocketDemo.sendInfo(sId, message);
        return "OK";
    }
}
