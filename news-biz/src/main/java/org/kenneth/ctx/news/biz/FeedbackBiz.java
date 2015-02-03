package org.kenneth.ctx.news.biz;

import org.kenneth.ctx.news.entity.Feedback;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.FeedbackVo;

import java.util.List;

/**
 * Created by Administrator on 2015/1/15.
 */
public interface FeedbackBiz {

    List<Feedback> page(FeedbackVo vo, PageParameter page);

    void add(Feedback feedback);
}
