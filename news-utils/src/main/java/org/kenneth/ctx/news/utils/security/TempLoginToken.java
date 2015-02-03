package org.kenneth.ctx.news.utils.security;

import java.util.Date;

/**
 * Created by Administrator on 2015/1/9.
 */
public class TempLoginToken {

    private String loginToken;
    private String tempToken;
    private Date createTime;
    private int accessCount = 0;

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getTempToken() {
        return tempToken;
    }

    public void setTempToken(String tempToken) {
        this.tempToken = tempToken;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getAccessCount() {
        return accessCount;
    }

    public void setAccessCount(int accessCount) {
        this.accessCount = accessCount;
    }

    public void incAccessCount() {
        accessCount++;
    }
}