package me.hupeng.websocket.module;

import com.google.gson.Gson;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

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
        processMessage(message, session);
//        System.out.println("收到 会话: " + session.getId() + " 的消息（" + message + "）");
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
                    onLine(message.from, session);
                    break;
                case Message.SEND_MESSAGE:
                    sendMessage(message.from, message.to, message.message);
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
        Log log= Logs.get();
        log.info("用户" + userId + "上线了");

        //查看此用户之前是否存在登录状态,有则清除
        try {
            String sessionId = userId2SessionId.get(userId);
            sessionId2Session.remove(sessionId);
            userId2SessionId.remove(userId);
        }catch (Exception e){

        }
        //添加session与用户之间的关联
        try {
            userId2SessionId.put(userId, session.getId());
        }catch (Exception e){

        }
    }

    /**
     * 用户发送消息
     * */
    private void sendMessage(int from, int to, String message){
        Log log= Logs.get();
        log.info("用户" + from + "给用户" +  to + "发送了一条消息,消息内容为:" + message);
        //存放数据库

        //判断有没有session

        //有session且发送成功则修改消息状态

    }


    /**
     * Created by HUPENG on 2017/6/11.
     */
    public static class Message {
        /**
         * 上线
         * */
        public final static int ON_LINE = 0;
        /**
         * 发送消息
         * */
        public final static int SEND_MESSAGE = 1;


        /**
         *  操作
         * */
        private int operate;

        /**
         * 发信方
         * */
        private int from;

        /**
         * 收信方
         * */
        private int to;

        /**
         * 消息内容
         * */
        private String message;

        private String accessKey;

        public int getOperate() {
            return operate;
        }

        public void setOperate(int operate) {
            this.operate = operate;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getAccessKey() {
            return accessKey;
        }

        public void setAccessKey(String accessKey) {
            this.accessKey = accessKey;
        }
    }

}
