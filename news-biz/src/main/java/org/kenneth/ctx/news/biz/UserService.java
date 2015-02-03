package org.kenneth.ctx.news.biz;

import org.kenneth.ctx.news.entity.User;
import org.kenneth.ctx.news.utils.exception.BaseServiceException;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.QueryCondition;

import java.util.List;

/**
 * Created by haoyuewen on 8/28/14.
 */
public interface UserService {

    User register(User user) throws BaseServiceException;

    boolean isExist(QueryCondition qc);

    List<User> page(QueryCondition vo, PageParameter pageParameter);
}