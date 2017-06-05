package me.hupeng.websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/websocket")
public class MessageWebSocket {
    /**
     * 存放Websocket Session Id --> Session 的映射关系
     */
    protected static ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.put(session.getId(), session);
        System.out.println("会话：" + session.getId() + " 连入服务器");
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason){
        sessions.remove(session.getId());
        System.out.println("会话："+ session.getId() + " 离开服务器");
    }

    /**
     * WebSocket会话出错时调用,默认调用onClose.
     */
    public void onError(Session session, java.lang.Throwable throwable) {
        System.out.println("会话："+ session.getId() + " 发生错误");
        onClose(session, null);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException,
            InterruptedException {
        System.out.println("收到 会话: " + session.getId() + " 的消息（" + message + "）");
       session.getAsyncRemote().sendText("回复:" + message);
    }
}
