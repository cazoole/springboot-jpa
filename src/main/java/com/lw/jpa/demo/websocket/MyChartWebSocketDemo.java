package com.lw.jpa.demo.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lw.jpa.demo.global.ResponseCodeEnum;
import com.lw.jpa.demo.global.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * simple web chart room by websocket
 * @author wei.liuw
 * @date 2019/6/9
 */
@ServerEndpoint("/websocketChart/{sId}")
@Component
@Slf4j
public class MyChartWebSocketDemo {

    /**
     *  count the current person in this chart room
      */
    private static AtomicInteger atomicOnlineCount = new AtomicInteger(0);

    /**
     * store current online users
     */
    private static ConcurrentHashMap<String, MyChartWebSocketDemo> currentUserMap = new ConcurrentHashMap<>();

    /**
     * user's session info
     */
    private Session session;

    /**
     * user unique id
     */
    private String sId = null;

    /**
     * customer id list for sending message
     */
    private static final String TO_USER_SID = "toUserId";
    /**
     * the message key which contains the message we want to send.
     */
    private static final String CONTENT_TEXT = "message";

    @OnOpen
    public void onOpen(Session session, @PathParam("sId") String sId) {
        // if user not exists, then add new user and person count ++
        this.sId = sId;
        this.session = session;
        if(null == currentUserMap.putIfAbsent(sId, this)) {
            log.info("Current logon user count：" + atomicOnlineCount.incrementAndGet() + "，Customer ID：" + sId);
        }

        // send notification information
        try {
            sendMessage(JSON.toJSONString(ResponseUtil.buildResponse(ResponseCodeEnum.SUCCESS, "Customer：" + sId + "，connect succeed！")));
        } catch (IOException e) {
            log.error("Error while try to send message!", e);
        }
    }

    @OnClose
    public void onClose() {
        if(null != currentUserMap.remove(this.sId)) {
            log.info("Customer：" + this.sId + "，has logout, current logon user count is：" + atomicOnlineCount.decrementAndGet());
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("Receive ：" + this.sId + "'s message with info of ：" + message);
        if(StringUtils.isNoneBlank(message)) {
            // ignore empty message
            JSONObject jsonObject = JSON.parseObject(message);
            String users = jsonObject.getString(TO_USER_SID);
            String msgText = jsonObject.getString(CONTENT_TEXT);
            Arrays.stream(users.split(",")).forEach(userId -> {
                MyChartWebSocketDemo socketDemo = currentUserMap.get(userId);
                if(null != socketDemo) {
                    try {
                        socketDemo.sendMessage(buildMessage(userId, msgText));
                    } catch (IOException e) {
                        log.error("Send message with exception, the userId is：{}", userId, e);
                    }
                }
            });
            if(!users.contains(sId)) {
                try {
                    this.sendMessage(buildMessage(sId, msgText));
                } catch (IOException e) {
                    log.error("Send message with exception：", e);
                }
            }
        }
    }

    private String buildMessage(String userId, String message) {
        if(this.sId.equals(userId)) {
            return message;
        } else {
            return this.sId.concat(": ").concat(message);
        }
    }

    @OnError
    public void onError(Session session, Throwable e) {
        log.error("发送时出现异常：", e);
    }

    private void sendMessage(String text) throws IOException {
        this.session.getBasicRemote().sendText(text);
    }

    public static void sendInfo(String message, String sId) {
        Collection<MyChartWebSocketDemo> chartWebSocketDemos = currentUserMap.values();
        for(MyChartWebSocketDemo socketDemo : chartWebSocketDemos) {
            try {
                if(null == sId || socketDemo.sId.equals(sId)) {
                    socketDemo.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSId(String userId){
        this.sId = userId;
    }

}
