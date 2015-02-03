package org.kenneth.ctx.news.biz.impl;

import org.kenneth.ctx.news.biz.LogLoginBiz;
import org.kenneth.ctx.news.entity.Account;
import org.kenneth.ctx.news.entity.LogLogin;
import org.kenneth.ctx.news.mapper.LogLoginMapper;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.LogLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/15.
 */
@Service
public class LogLoginBizImpl implements LogLoginBiz {

    @Autowired
    LogLoginMapper logLoginMapper;

    @Override
    public void record(Account account, Map<String, Object> extraAttr) {
        LogLogin logLogin = new LogLogin();
        logLogin.setUserId(account.getId());
        logLogin.setUserName(account.getUserName());
        logLogin.setLoginIP(extraAttr.get("ip").toString());
        logLoginMapper.insert(logLogin);
    }

    @Override
    public List<LogLogin> page(LogLoginVo vo, PageParameter page) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("vo", vo);
        map.put("page", page);
        return logLoginMapper.queryByPage(map);
    }
}
