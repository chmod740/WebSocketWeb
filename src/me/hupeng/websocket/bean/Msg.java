package me.hupeng.websocket.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * Created by HUPENG on 2017/6/11.
 */
@Table("message")
public class Msg {
    @Id
    private int id;

    @Column("from_user")
    private int from;

    @Column("to_user")
    private int to;

    @Column("send_time")
    private Date sendTime;

    @Column("message")
    private String message;

    @Column("send_result")
    private int sendResult;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSendResult() {
        return sendResult;
    }

    public void setSendResult(int sendResult) {
        this.sendResult = sendResult;
    }
}
