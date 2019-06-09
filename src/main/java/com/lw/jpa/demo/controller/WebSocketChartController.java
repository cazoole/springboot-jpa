package com.lw.jpa.demo.controller;

import com.lw.jpa.demo.websocket.MyChartWebSocketDemo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author wei.liuw
 * @date 2019/6/9
 */
@Slf4j
@RestController
@RequestMapping("/chart")
public class WebSocketChartController {

    @GetMapping("/login/{sId}")
    public String login(@PathVariable("sId") String sId) {
        log.info("sId:" + sId);
        MyChartWebSocketDemo myChartWebSocketDemo = new MyChartWebSocketDemo();
        myChartWebSocketDemo.setSId(sId);
        return "ok";
    }

    @ResponseBody
    @RequestMapping(value = "/send/{sId}")
    public Object sendMessage(@PathVariable("sId") String sId, String message) {
        log.info("sId = " + sId + ", message=" + message);
        MyChartWebSocketDemo.sendInfo(sId, message);
        return "OK";
    }
}
