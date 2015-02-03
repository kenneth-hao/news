package org.kenneth.ctx.news.biz;

import org.kenneth.ctx.news.entity.ChannelView;
import org.kenneth.ctx.news.vo.QueryCondition;

import java.util.List;

/**
 * Created by Administrator on 2015/1/21.
 */
public interface ChannelViewBiz {


    List<ChannelView> query(QueryCondition qc);


}
