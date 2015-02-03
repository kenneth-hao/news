package org.kenneth.ctx.news.biz;


import org.kenneth.ctx.news.entity.News;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.NewsVo;
import org.kenneth.ctx.news.vo.QueryCondition;

import java.util.List;

/**
 * Created by Administrator on 2015/1/5.
 */
public interface NewsService {

    News getById(String nid);

    News queryPreviewNews(String nid);

    List<News> query(QueryCondition qc);

    List<News> queryCarousel(QueryCondition qc);

    List<News> queryList(QueryCondition qc);

    List<News> pageList(QueryCondition qc, PageParameter pageParameter);

    List<News> page(QueryCondition qc, PageParameter pageParameter);

    List<News> pageCarousel(QueryCondition qc, PageParameter pageParameter) throws Exception;

    boolean update(News news);

    boolean updateCarousel(News news);

    boolean updateStatus(News news);

    boolean updateRecommand(News news);

    boolean updateTop(News news);

    boolean delete(News news);

    boolean revert(News news);

    void add(News news, String webContextPath);

    List<News> queryUserFavoriteByPage(QueryCondition qc, PageParameter page);


    // ************************* Admin START ********************************

    List<News> bgPage(NewsVo vo, PageParameter page);

    // ************************* Admin END ********************************


}
