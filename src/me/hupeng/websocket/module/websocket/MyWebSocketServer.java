//package me.hupeng.websocket.module.websocket;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import me.hupeng.websocket.MainSetup;
//import me.hupeng.websocket.bean.Msg;
//import org.nutz.dao.Cnd;
//import org.nutz.dao.Dao;
//import org.nutz.ioc.loader.annotation.IocBean;
//import org.nutz.log.Log;
//import org.nutz.log.Logs;
//
//import javax.websocket.Session;
//import javax.websocket.server.ServerEndpoint;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//
//@ServerEndpoint(value = "/chat")
//@IocBean(singleton = true)
//public class MyWebSocketServer extends MessageWebSocketServer {
//    Dao dao = MainSetup.dao;
//    Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//
//    private static Map<String,Session>userId2Session = new ConcurrentHashMap<>();
//
//    @Override
//    public Object decode(String s) {
//        return null;
//    }
//
//    @Override
//    public String encode(Object obj) {
//        return null;
//    }
//
//    @Override
//    protected void processMessage(String msg, Session session) {
//        try {
//            Message message =  gson.fromJson(msg, Message.class);
//            switch (message.getOperate()){
//                case Message.ON_LINE:
//                    onLine(message.getFrom(), session);
//                    break;
//                case Message.SEND_MESSAGE:
//                    sendMessage(message.getFrom(), message.getTo(), message.getMessage());
//                    break;
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 用户上线
//     * */
//    private void onLine(int userId, Session session){
//        Log log= Logs.get();
//        log.info("用户" + userId + "上线了");
//
//        //查看此用户之前是否存在登录状态,有则清除
//        try {
//            userIdToSession
//        }catch (Exception e){
//
//        }
//        //添加session与用户之间的关联
//        try {
//            userId2SessionId.put(userId, session.getId());
//        }catch (Exception e){
//
//        }
//
//        //检查用户名下是否有未收到的消息,有则发送之
//        List<Msg> list = dao.query(Msg.class, Cnd.where("to_user", "=", userId).and("send_result", "=", "0").asc("id"));
//        for(Msg msg: list){
//            try {
//                Message message = new Message();
//                message.setFrom(msg.getFrom());
//                message.setTo(msg.getTo());
//                message.setSendTime(msg.getSendTime());
//                message.setMessage(msg.getMessage());
//                session.getAsyncRemote().sendText(gson.toJson(message));
//                msg.setSendResult(1);
//                dao.update(msg);
//            }catch (Exception e){
//
//            }
//        }
//    }
//
//    /**
//     * 用户发送消息
//     * */
//    private void sendMessage(int from, int to, String message){
//        Log log= Logs.get();
//        log.info("用户" + from + "给用户" +  to + "发送了一条消息,消息内容为:" + message);
//        //存放数据库
//        Msg msg = new Msg();
//        msg.setSendTime(new Date(System.currentTimeMillis()));
//        msg.setFrom(from);
//        msg.setTo(to);
//        msg.setMessage(message);
//        msg.setSendResult(0);
//        dao.insert(msg);
//
//        //判断有没有session
//        ////有session且发送成功则修改消息状态
//        String sessionId = userId2Session.get(to);
//        if (sessionId != null){
//            Session session = sessionId2Session.get(sessionId);
//            try {
//                if (session != null){
//                    Message message1 = new Message();
//                    message1.setFrom(from);
//                    message1.setTo(to);
//                    message1.setMessage(message);
//                    message1.setSendTime(new Date(System.currentTimeMillis()));
//                    session.getAsyncRemote().sendText(gson.toJson(message1));
//                    msg.setSendResult(1);
//                    dao.update(msg);
//                }
//            }catch (Exception e){
//
//            }
//        }
//    }
//
//}
