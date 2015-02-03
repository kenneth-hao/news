package org.kenneth.ctx.news.biz;

import org.kenneth.ctx.news.entity.Comment;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.QueryCondition;

import java.util.List;

/**
 * Created by Administrator on 2015/1/8.
 */
public interface CommentService {

    Integer count(QueryCondition qc);

    Comment commit(Comment comment);

    List<Comment> page(QueryCondition qc, PageParameter pageParameter);

}
