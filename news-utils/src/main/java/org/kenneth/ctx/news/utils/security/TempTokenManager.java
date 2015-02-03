package org.kenneth.ctx.news.utils.security;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2015/1/9.
 */
public class TempTokenManager {

    /**
     * 根据登陆token，获取一个临时token，临时token只能在验证登陆时使用一次（SSOService.isLogonByToken）
     *
     * @param loginToken
     */
    private static TempTokenPool tempTokenPool = new TempTokenPool(); //进群环境下可能存在问题

    /**
     * 生成一个新的临时token
     */
    public static String createTempToken(String loginToken) {
        if (loginToken == null || loginToken.trim().length() == 0 || loginToken.endsWith("T")) return "";
        String uuid = UUID.randomUUID().toString();
        String loginToken2 = loginToken.substring(0, loginToken.length() - 4); //去掉末尾的@sso
        String tempToken = loginToken2 + "_" + uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24) + "T";  //去掉“-”符号
        tempTokenPool.putToken(loginToken, tempToken);
        return tempToken;
    }

    public static TempLoginToken getTempLoginToken(String tempToken) {
        if (tempToken == null || tempToken.trim().length() == 0) return null;
        TempLoginToken tempLoginToken = tempTokenPool.getTempLoginToken(tempToken);
        return tempLoginToken;
    }

    /**
     * 根据临时token，获取真实loginToken
     * 如果临时token无效，则返回空字符串
     * *
     */
    public static String getLoginToken(String tempToken) {
        if (tempToken == null || tempToken.trim().length() == 0) return "";
        String loginToken = "";
        TempLoginToken tempLoginToken = tempTokenPool.getTempLoginToken(tempToken);
        if (tempLoginToken != null) {
            long timespan = new Date().getTime() - tempLoginToken.getCreateTime().getTime();
            if (timespan > 5 * 60 * 1000) { //验证tempToken是否有效
                tempLoginToken = null;
            }
        }
        if (tempLoginToken != null) {
            loginToken = tempLoginToken.getLoginToken();
        }
        return loginToken;
    }

    /**
     * 移出临时token*
     */
    public static void removeTempToken(String tempToken) {
        tempTokenPool.removeToken(tempToken);
    }
}