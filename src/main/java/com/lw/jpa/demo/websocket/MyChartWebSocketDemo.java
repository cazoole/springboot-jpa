package com.lw.jpa.demo.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lw.jpa.demo.global.ResponseCodeEnum;
import com.lw.jpa.demo.global.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 实现一个简单的webSocket 的网页聊天室(使用原型模式)
 * @author wei.liuw
 * @date 2019/6/9
 */
@ServerEndpoint("/websocketChart/{sId}")
@Component
@Slf4j
public class MyChartWebSocketDemo {

    /**
     *  用于计数当前的聊天室登陆人数
      */
    private static AtomicInteger atomicOnlineCount = new AtomicInteger(0);

    /**
     * 存放当前用户的信息等
     */
    private static ConcurrentHashMap<String, MyChartWebSocketDemo> currentUserMap = new ConcurrentHashMap<>();

    /**
     * 用来存储登陆信息
     */
    private Session session;

    /**
     * 用户唯一标识
     */
    private String sId = null;

    /**
     * 要发送给的用户ID的别名对象
     */
    private static final String TO_USER_SID = "toUserId";
    /**
     * 要发送i洗脑洗的别名
     */
    private static final String CONTENT_TEXT = "contentText";

    @OnOpen
    public void onOpen(Session session, @PathParam("sId") String sId) {
        // 如果用户不存在，则增加一条用户记录，并将记录数据增加1
        this.sId = sId;
        this.session = session;
        if(null == currentUserMap.putIfAbsent(sId, this)) {
            log.info("当前登陆人数：" + atomicOnlineCount.incrementAndGet() + "，用户ID：" + sId);
        }

        // 发送通知消息
        try {
            sendMessage(JSON.toJSONString(ResponseUtil.buildResponse(ResponseCodeEnum.SUCCESS, "用户：" + sId + "，连接成功！")));
        } catch (IOException e) {
            log.error("发送通知消息异常！", e);
        }
    }

    @OnClose
    public void onClose() {
        if(null == currentUserMap.remove(this.sId)) {
            log.info("用户：" + this.sId + "，已经登出，当前用户人数：" + atomicOnlineCount.decrementAndGet());
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到用户：" + this.sId + "发来的一条消息，消息内容：" + message);
        if(StringUtils.isNoneBlank(message)) {
            // 消息不为空，发送消息
            JSONArray jsonArray = JSONArray.parseArray(message);
            for (int i = 0, total = jsonArray.size(); i < total; i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String toUserId = object.getString(TO_USER_SID);

                // 如果目标用户不在线，则发送终止
                MyChartWebSocketDemo socketDemo = currentUserMap.get(toUserId);
                if(null == socketDemo) {
                    log.warn("目标用户：" + toUserId + "，当前未上线！发送取消！");
                    continue;
                }

                // 如果发送消息体为空，则发送终止
                String contentText = object.getString(CONTENT_TEXT);
                if(StringUtils.isBlank(contentText)) {
                    log.warn("发送给用户：" + toUserId + "，消息体为空，发送取消！");
                    continue;
                }

                // 校验通过，则发送消息
                try {
                    socketDemo.sendMessage(JSON.toJSONString(object));
                } catch (IOException e) {
                    log.error("发送通知消息异常，异常对象：", e);
                }
            }
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
