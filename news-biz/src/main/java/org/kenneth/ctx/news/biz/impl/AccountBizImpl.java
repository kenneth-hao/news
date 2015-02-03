package org.kenneth.ctx.news.biz.impl;

import org.kenneth.ctx.news.biz.AccountBiz;
import org.kenneth.ctx.news.entity.Account;
import org.kenneth.ctx.news.mapper.AccountMapper;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.AccountVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/15.
 */
@Service
public class AccountBizImpl implements AccountBiz {

    private static final Logger logger = LoggerFactory.getLogger(AccountBizImpl.class);

    @Autowired
    AccountMapper accountMapper;

    @Override
    public Account getById(String id) {
        return accountMapper.getById(id);
    }

    @Override
    public List<Account> page(AccountVo vo, PageParameter page) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("vo", vo);
        map.put("page", page);
        return accountMapper.queryByPage(map);
    }

    @Override
    public Account querySingleAccount(String username) {
        AccountVo vo = new AccountVo();
        vo.setUserName(username);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("vo", vo);

        Account ac = accountMapper.queryOne(map);

        return ac;
    }

    @Override
    public void add(Account account) {

        account.setCdate(new Date());
        account.setStatus(Account.S_STATUS_NORMAL);

        accountMapper.insert(account);
    }

    @Override
    public void update(Account account) {
        accountMapper.update(account);
    }

    @Override
    public void delete(Account account) {
        accountMapper.delete(String.valueOf(account.getId()));
    }
}
