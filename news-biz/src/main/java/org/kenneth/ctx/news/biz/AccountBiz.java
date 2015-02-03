package org.kenneth.ctx.news.biz;

import org.kenneth.ctx.news.entity.Account;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.AccountVo;

import java.util.List;

/**
 * Created by Administrator on 2015/1/15.
 */
public interface AccountBiz {

    List<Account> page(AccountVo vo, PageParameter page);

    Account querySingleAccount(String username);

    void add(Account account);

    Account getById(String id);

    void update(Account account);

    void delete(Account account);
}
