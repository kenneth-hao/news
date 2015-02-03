package org.kenneth.ctx.news.utils.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Administrator on 2015/1/9.
 */
public class TempTokenPool {
    public static final Logger logger = LoggerFactory.getLogger(TempTokenPool.class);

    private Map<String, TempLoginToken> tempTokens = new HashMap<String, TempLoginToken>();

    public TempLoginToken putToken(String loginToken, String tempToken) {
        Date now = new Date();
        TempLoginToken tempLoginToken = new TempLoginToken();
        tempLoginToken.setCreateTime(now);
        tempLoginToken.setLoginToken(loginToken);
        tempLoginToken.setTempToken(tempToken);
        tempTokens.put(tempToken, tempLoginToken);

        ///移出超时的临时token
        List<String> rmKeys = new ArrayList<String>();
        for (String key : tempTokens.keySet()) {
            TempLoginToken tempLoginToken2 = tempTokens.get(tempToken);
            long timespan = now.getTime() - tempLoginToken2.getCreateTime().getTime();
            if (timespan > 10 * 60 * 1000) { //超过10分钟
                rmKeys.add(key);
            }
        }
        for (String key : rmKeys) {
            tempTokens.remove(key);
        }
        logger.info("TempTokenPool Size: " + tempTokens.size());
        return tempLoginToken;
    }

    /**
     * 根据临时token获取登录信息
     */
    public TempLoginToken getTempLoginToken(String tempToken) {
        TempLoginToken tempLoginToken = tempTokens.get(tempToken);
        return tempLoginToken;
    }

    /**
     * 移出临时token
     */
    public void removeToken(String tempToken) {
        tempTokens.remove(tempToken);
    }
}
