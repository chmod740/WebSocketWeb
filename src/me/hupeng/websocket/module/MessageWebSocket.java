package me.hupeng.websocket.module;

import com.google.gson.Gson;
import me.hupeng.websocket.bean.Message;
import org.nutz.ioc.loader.annotation.IocBean;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/websocket")
@IocBean(singleton = true)
public class MessageWebSocket {
    /**
     * 存放Websocket Session Id --> Session 的映射关系
     */
    protected static ConcurrentHashMap<String, Session> sessionId2Session = new ConcurrentHashMap<>();

    /**
     * 用户Id --> SessionId的映射关系
     */
    protected static ConcurrentHashMap<Object, String>userId2SessionId = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        /***/
        sessionId2Session.put(session.getId(), session);
        System.out.println("会话：" + session.getId() + " 连入服务器");

    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason){
        sessionId2Session.remove(session.getId());
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

    /**
     * 处理用户消息
     * */
    private void processMessage(String msg, Session session){
        try {
            Message message = new Gson().fromJson(msg, Message.class);
            switch (message.getOperate()){
                case Message.ON_LINE:
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 用户上线
     * */
    private void onLine(int userId, Session session){
        //查看此用户之前是否存在登录状态,有则清除
        try {
            String sessionId = userId2SessionId.get(userId);
            sessionId2Session.remove(sessionId);
            userId2SessionId.remove(userId);

        }catch (Exception e){

        }
    }

    /**
     * 用户发送消息
     * */
    private void sendMessage(){

    }
}
