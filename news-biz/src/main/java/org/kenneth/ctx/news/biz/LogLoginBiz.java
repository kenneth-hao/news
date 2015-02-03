package org.kenneth.ctx.news.biz;

import org.kenneth.ctx.news.entity.Account;
import org.kenneth.ctx.news.entity.LogLogin;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.LogLoginVo;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/15.
 */
public interface LogLoginBiz {
    void record(Account account, Map<String, Object> extraAttr);

    List<LogLogin> page(LogLoginVo vo, PageParameter page);
}
