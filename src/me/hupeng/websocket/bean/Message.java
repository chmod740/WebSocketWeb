package me.hupeng.websocket.bean;

/**
 * Created by HUPENG on 2017/6/11.
 */
public class Message {
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
}
