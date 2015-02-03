package org.kenneth.ctx.news.utils.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Administrator on 2015/1/8.
 */
public class SessionPool {

    public static final Logger logger = LoggerFactory.getLogger(SessionPool.class);

    private static SessionPool pool = null;
    private static Object lockObject = new Object();
    /**
     * token -> Session
     */
    private HashMap<String, MySession> sessions = new HashMap<String, MySession>();


    public synchronized static SessionPool getInstance() {
        if (pool == null) {
            pool = new SessionPool();
        }
        return pool;
    }

    private SessionPool() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    //删除过期的Session
                    deleteTimeoutSession();
                    try {
                        //Thread.sleep(1000 * 60 * 10); //每10分钟检查一次
                        Thread.sleep(1000 * 60 * 60 * 6); //每6小时检查一次
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }

    /**
     * 检查token是否还有效。
     *
     * @param token
     * @return
     * @throws Exception
     */
    public boolean hasSession(String token, String loginId) throws Exception {
        MySession session = doGetSession(token, loginId, false);
        return session == null ? false : true;
    }

    private void deleteTimeoutSession() {
        synchronized (lockObject) {
            ArrayList<String> delKeys = new ArrayList<String>();

            Date currTime = new Date();
            Iterator<?> it = sessions.entrySet().iterator();
            int validCount = 0;
            int unvalidCount = 0;
            while (it.hasNext()) {
                Map.Entry<?, ?> entey = (Map.Entry<?, ?>) it.next();
                MySession session = (MySession) entey.getValue();
                if (session.checkAccessTime(currTime) == false) {
                    String key = (String) entey.getKey();
                    delKeys.add(key);
                    ++unvalidCount;
                } else {
                    ++validCount;
                }
            }
            for (String key : delKeys) {
                sessions.remove(key);
            }
            int sessionCount = sessions.size();
            logger.info(String.format("有效的Session: %s, 移除失效的Session: %s", sessionCount, unvalidCount));
        }
    }

    public void deleteSession(String token) {
        MySession session = sessions.remove(token);
        if (session != null) {
            session.clear();
        }
    }

    /**
     * 根据登录 Id 获取 token, 如果Session 超时, 则获取不到 token
     *
     * @param loginId
     * @return
     */
    public String getToken(String loginId) {
        if (loginId == null || loginId.length() == 0) {
            return "";
        }
        String token = "";
        Date currTime = new Date();
        Iterator<?> it = sessions.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<?, ?> entry = (Map.Entry<?, ?>) it.next();
            MySession session = (MySession) entry.getValue();
            if (loginId.equals(session.getLoginId())) {
                if (session.checkAndModifyAccessTime(currTime)) {
                    token = (String) entry.getKey();
                }
                break;
            }
        }

        return token;
    }


    /**
     * 获取token的Session，如果没有Session，则创建一个
     *
     * @param token
     * @param loginId 同一个loginId在第一次调用getSession以后，再次调用时，loginId可以为null
     * @return
     * @throws Exception
     */
    public MySession getSession(String token, String loginId) throws Exception {
        //if(token== null || token.length()<1)return null;
        MySession session = doGetSession(token, loginId, true);
        if (loginId != null && loginId.length() > 0) {
            session.setLoginId(loginId);
        }
        return session;
    }

    /**
     * 获取Session
     *
     * @param token
     * @param createSession 如果没有获取到Session，是否创建新的。在登录时用
     * @return
     * @throws Exception
     */
    private MySession doGetSession(String token, String loginId, boolean createSession) throws Exception {
        MySession session = null;
        synchronized (lockObject) {
            if (token == null || token.length() == 0) {
                String uuid = UUID.randomUUID().toString();
                uuid = uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);  //去掉“-”符号
                token = loginId + "_" + uuid + "@sso";
            } else if (token.endsWith("T")) {
                int spIndex = token.lastIndexOf('_');
                token = token.substring(0, spIndex) + "@sso";
            }
            session = sessions.get(token);
            if (null == session) {
                if (createSession) {
                    session = new MySession(loginId);
                    session.setToken(token);
                    sessions.put(token, session);
                } else {
                    throw new Exception("SSO-没有登录信息，token：" + token);
                }
            } else {
                Date currTime = new Date();
                if (!session.checkAccessTime(currTime)) {//Session超时了
                    session.clear();
                    sessions.remove(token);//清除Session
                    session = null;//返回值设为null
                    if (createSession) { //需要创建新的Session
                        session = new MySession(loginId);
                        sessions.put(token, session);
                    } else {
                        throw new Exception("SSO-" + token + " Session超时");
                    }
                } else {//Session有效
                    session.modifyAccessTime(currTime);
                }
            }
        }
        return session;
    }
}
