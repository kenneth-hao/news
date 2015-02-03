package org.kenneth.ctx.news.biz.impl;

import org.apache.commons.lang.StringUtils;
import org.kenneth.ctx.news.biz.NewsService;
import org.kenneth.ctx.news.entity.CarEnterprise;
import org.kenneth.ctx.news.entity.Category;
import org.kenneth.ctx.news.entity.ChannelView;
import org.kenneth.ctx.news.entity.News;
import org.kenneth.ctx.news.mapper.CarEnterpriseMapper;
import org.kenneth.ctx.news.mapper.CategoryMapper;
import org.kenneth.ctx.news.mapper.NewsMapper;
import org.kenneth.ctx.news.utils.constant.ConfigPropKey;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.utils.spring.CustomPropertyConfigurer;
import org.kenneth.ctx.news.vo.NewsVo;
import org.kenneth.ctx.news.vo.QueryCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2015/1/5.
 */
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    NewsMapper newsMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    CarEnterpriseMapper carEnterpriseMapper;

    @Override
    public News getById(String nid) {
        News news = newsMapper.getById(nid);
        return news;
    }

    private QueryCondition generQueryCondition(QueryCondition qc) {
        if (StringUtils.isEmpty(qc.getStrNewsIds()) == false) {
            String[] ids = qc.getStrNewsIds().split(",");
            List<Integer> idList = new ArrayList<Integer>();
            for (String id : ids) {
                idList.add(Integer.parseInt(id.trim()));
            }
            qc.setNewsIdList(idList);
        }
        return qc;
    }

    @Override
    public News queryPreviewNews(String nid) {
        News previewNews = newsMapper.getById(nid);
        previewNews = resetLinkUrl(previewNews);
        return previewNews;
    }

    @Override
    public List<News> query(QueryCondition qc) {
        QueryCondition gqc = this.generQueryCondition(qc);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("vo", qc);
        List<News> newsList = resetLinkUrl(newsMapper.query(map));
        return newsList;
    }

    private List<News> resetLinkUrl(List<News> newsList) {
        for (News n : newsList) {
            n = resetLinkUrl(n);
        }
        return newsList;
    }

    private News resetLinkUrl(News n) {
        n.setLinkUrl("http://" + CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_WEBSITE) + "/" + CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_WEBSITE_PAGE_PROJECT) + "/admin/news/to_preview.html?nid=" + n.getNid());
        return n;
    }

    @Override
    public List<News> page(QueryCondition qc, PageParameter page) {
        QueryCondition gqc = this.generQueryCondition(qc);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("vo", gqc);
        map.put("page", page);
        List<News> newsList = newsMapper.queryByPage(map);
        newsList = resetLinkUrl(newsList);
        return newsList;
    }

    @Override
    public List<News> queryCarousel(QueryCondition qc) {
        qc.setStatus(News.S_AUDITED);
        qc.setIsCarousel(News.S_CAROUSEL);
        return this.query(qc);
    }

    @Override
    public List<News> queryList(QueryCondition qc) {
        qc.setStatus(News.S_AUDITED);
        qc.setIsCarousel(News.S_NOT_CAROUSEL);
        return this.query(qc);
    }

    @Override
    public List<News> pageList(QueryCondition qc, PageParameter page) {
        qc.setStatus(News.S_AUDITED);
        qc.setIsCarousel(News.S_NOT_CAROUSEL);
        return this.page(qc, page);
    }

    @Override
    public List<News> pageCarousel(QueryCondition qc, PageParameter page) throws Exception {
        qc.setStatus(News.S_AUDITED);
        qc.setIsCarousel(News.S_CAROUSEL);
        return this.page(qc, page);
    }

    @Override
    public boolean update(News news) {
        News upNews = newsMapper.getById(String.valueOf(news.getNid()));

        if (news.getChannelId() != null && news.getChannelType() != null) {
            if (ChannelView.S_TYPE_CATEGORY.equals(news.getChannelType())) {
                Category category = categoryMapper.getById(String.valueOf(news.getChannelId()));
                upNews.setChannelId(category.getCid());
                upNews.setChannelName(category.getName());
                upNews.setChannelType(ChannelView.S_TYPE_CATEGORY);
            } else {
                CarEnterprise carEnterprise = carEnterpriseMapper.getById(String.valueOf(news.getChannelId()));
                upNews.setChannelId(carEnterprise.getCeid());
                upNews.setChannelName(carEnterprise.getName());
                upNews.setChannelType(ChannelView.S_TYPE_CAR_ENTERPRISE);
            }
        }
        upNews.setPublishTime(news.getPublishTime());
        upNews.setListImgPath(news.getListImgPath());
        upNews.setCarouselImgPath(news.getCarouselImgPath());
        upNews.setTitle(news.getTitle());
        upNews.setContent(news.getContent());
        upNews.setFileFormat(news.getFileFormat());
        upNews.setUdate(new Date());

        newsMapper.update(upNews);

        return true;
    }

    @Override
    public boolean updateCarousel(News news) {
        News upNews = newsMapper.getById(String.valueOf(news.getNid()));

        upNews.setIsCarousel(news.getIsCarousel());
        upNews.setUdate(new Date());

        newsMapper.update(upNews);
        return true;
    }

    @Override
    public boolean updateStatus(News news) {
        News upNews = newsMapper.getById(String.valueOf(news.getNid()));

        upNews.setStatus(news.getStatus());
        upNews.setUdate(new Date());

        newsMapper.update(upNews);
        return true;
    }

    @Override
    public boolean updateRecommand(News news) {
        News upNews = newsMapper.getById(String.valueOf(news.getNid()));

        upNews.setIsRecommand(news.getIsRecommand());
        upNews.setUdate(new Date());

        newsMapper.update(upNews);
        return true;
    }

    @Override
    public boolean updateTop(News news) {
        News upNews = newsMapper.getById(String.valueOf(news.getNid()));

        upNews.setIsTop(news.getIsTop());
        upNews.setUdate(new Date());

        newsMapper.update(upNews);
        return true;
    }

    @Override
    public boolean delete(News news) {
        News upNews = newsMapper.getById(String.valueOf(news.getNid()));

        upNews.setValid(news.getValid());

        newsMapper.update(upNews);
        return true;
    }

    @Override
    public boolean revert(News news) {
        News upNews = newsMapper.getById(String.valueOf(news.getNid()));

        upNews.setValid(News.S_VALID);

        newsMapper.update(upNews);
        return true;
    }

    @Override
    public void add(News news, String webContextPath) {

        if (news.getChannelId() != null && news.getChannelType() != null) {
            if (ChannelView.S_TYPE_CATEGORY.equals(news.getChannelType())) {
                Category category = categoryMapper.getById(String.valueOf(news.getChannelId()));
                news.setChannelId(category.getCid());
                news.setChannelName(category.getName());
            } else {
                CarEnterprise carEnterprise = carEnterpriseMapper.getById(String.valueOf(news.getChannelId()));
                news.setChannelId(carEnterprise.getCeid());
                news.setChannelName(carEnterprise.getName());
            }
        }

        news.setValid(News.S_VALID);
        news.setStatus(News.S_NOT_AUDIT);

        newsMapper.insert(news);
    }

    @Override
    public List<News> queryUserFavoriteByPage(QueryCondition qc, PageParameter page) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("vo", qc);
        map.put("page", page);
        List<News> newsList = newsMapper.queryUserFavoriteByPage(map);
        newsList = resetLinkUrl(newsList);
        return newsList;
    }

    // ************************* Admin START ********************************

    @Override
    public List<News> bgPage(NewsVo vo, PageParameter page) {

        return null;
    }

    // ************************* Admin END ********************************


}
