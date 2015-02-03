package org.kenneth.ctx.news.biz.impl;

import org.kenneth.ctx.news.biz.LoginBiz;
import org.kenneth.ctx.news.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2015/1/15.
 */
public class LoginBizImpl implements LoginBiz {

    @Autowired
    private AccountMapper accountMapper;


}
