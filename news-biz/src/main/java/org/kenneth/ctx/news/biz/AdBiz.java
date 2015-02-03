package org.kenneth.ctx.news.biz;

import org.kenneth.ctx.news.entity.Ad;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.AdVo;
import org.kenneth.ctx.news.vo.QueryCondition;

import java.util.List;

/**
 * Created by Administrator on 2015/1/26.
 */
public interface AdBiz {

    public List<Ad> queryNewestAds(QueryCondition qc);

    List<Ad> page(AdVo vo, PageParameter pageParameter);

    public void add(Ad ad);
}
