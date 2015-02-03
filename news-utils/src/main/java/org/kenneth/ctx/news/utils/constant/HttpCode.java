package org.kenneth.ctx.news.utils.constant;

/**
 * Created by Administrator on 2015/1/5.
 */
public final class HttpCode {

    private HttpCode() {
    }

    /**
     * 请求成功
     */
    public static final int OK = 200;

    /**
     * 服务器错误
     */
    public static final int SERVER_ERROR = 500;

    /**
     * 业务操作失败
     */
    public static final int SERVICE_FAILURE = 1001;

    /**
     * 用户未登陆, 操作失败
     */
    public static final int SERVICE_NO_LOGIN = 2000;

}
