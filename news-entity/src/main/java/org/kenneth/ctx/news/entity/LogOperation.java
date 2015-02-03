package org.kenneth.ctx.news.entity;

import java.util.Date;


@SuppressWarnings("serial")
public class LogOperation extends BaseEntity {

    private int id;
    private String username; // 操作用户
    private String module; // 操作模块
    private String action; // 操作行为
    private Date actionTime; // 操作时间
    private String userIP; // 操作者IP
    private String elapseTime; // 操作耗时

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUserIP() {
        return userIP;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

    public String getElapseTime() {
        return elapseTime;
    }

    public void setElapseTime(String elapseTime) {
        this.elapseTime = elapseTime;
    }
}
