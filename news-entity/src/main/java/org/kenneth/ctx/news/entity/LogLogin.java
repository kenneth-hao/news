package org.kenneth.ctx.news.entity;

import java.util.Date;

/**
 * Created by Administrator on 2015/1/15.
 */
public class LogLogin extends BaseEntity {

    private static final long serialVersionUID = 4999821408224216189L;
    private Integer id;
    private Integer userId;
    private String userName;
    private Date loginTime;
    private String loginIP;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIP() {
        return loginIP;
    }

    public void setLoginIP(String loginIP) {
        this.loginIP = loginIP;
    }
}
