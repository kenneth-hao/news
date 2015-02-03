package org.kenneth.ctx.news.utils.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/8.
 */
public class MySession {
    public static final Logger logger = LoggerFactory.getLogger(MySession.class);

    /**
     * Session 超时时间单位: 默认 30分钟
     */
    private static int sessionTimeoutSeconds = 60 * 30;

    public static int getSessionTimeoutSeconds() {
        return sessionTimeoutSeconds;
    }

    public static void setSessionTimeoutSeconds(int sessionTimeoutSeconds) {
        logger.info(String.format("设置超时时间(秒): %s", sessionTimeoutSeconds));
        MySession.sessionTimeoutSeconds = sessionTimeoutSeconds;
    }

    Map<String, String> kvcoll = new HashMap<String, String>();
    private Date lastAccessTime;
    private String token;
    private String loginId;
    private long userOid;

    public MySession(String loginId) {
        setLastAccessTime(new Date());
        this.loginId = loginId;
    }

    public void setValue(String key, String value) {
        kvcoll.put(key, value);
    }

    public Object getValue(String key) {
        Object obj = kvcoll.get(key);
        return obj;
    }

    public void clear() {
        loginId = "";
        kvcoll.clear();
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    /**
     * 检查Session 是否过期
     *
     * @param currTime
     * @return
     */
    public boolean checkAccessTime(Date currTime) {
        long milliseconds = currTime.getTime() - lastAccessTime.getTime();
        double seconds = milliseconds / 1000.0;
        if (seconds > sessionTimeoutSeconds) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 检查Session 是否超期, 如果没有超期, 更新访问时间
     *
     * @param currTime
     * @return
     */
    public boolean checkAndModifyAccessTime(Date currTime) {
        if (checkAccessTime(currTime)) {
            lastAccessTime = currTime;
            return true;
        } else {
            return false;
        }
    }

    /**
     * 更新Session 访问时间
     *
     * @param currTime
     */
    public void modifyAccessTime(Date currTime) {
        lastAccessTime = currTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public long getUserOid() {
        return userOid;
    }

    public void setUserOid(long userOid) {
        this.userOid = userOid;
    }
}
