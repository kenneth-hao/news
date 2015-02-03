package org.kenneth.ctx.news.biz;

import org.kenneth.ctx.news.entity.User;

/**
 * Created by Administrator on 2015/1/9.
 */
public interface LoginService {

    User login(String loginId, String password) throws Exception;

}
