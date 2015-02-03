package org.kenneth.ctx.news.web.mobile;


import org.kenneth.ctx.news.biz.NewsService;
import org.kenneth.ctx.news.entity.News;
import org.kenneth.ctx.news.entity.User;
import org.kenneth.ctx.news.utils.constant.HttpCode;
import org.kenneth.ctx.news.utils.constant.LogConstants;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.QueryCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2015/1/5.
 */
@Controller
@RequestMapping("/rest/news")
public class RestNewsController extends RestBaseController {

    private Logger logger = LoggerFactory.getLogger(RestNewsController.class);

    // 默认轮播图个数
    public static final Integer DEFAULT_CAROUSEL_SIZE = 4;

    @Autowired
    private NewsService newsService;

    @RequestMapping(value = "/queryByQc", method = RequestMethod.POST)
    @ResponseBody
    public String queryByQc(QueryCondition qc, PageParameter pageParameter) {
        try {
            User u = currentUser();
            if (u != null) {
                qc.setUserId(u.getUid());
            }
            Map<String, Object> map = new HashMap<String, Object>();
            List<News> newsList = newsService.page(qc, pageParameter);
            map.put("newsList", newsList);
            return success(map, pageParameter);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(LogConstants.FLAG_ERROR, e);
            return error(e);
        }
    }

    @RequestMapping("/query")
    @ResponseBody
    public String query(QueryCondition qc, PageParameter pageParameter) {
        try {
            User u = currentUser();
            if (u != null) {
                qc.setUserId(u.getUid());
            }
            if (qc.getCarouselSize() == null) {
                qc.setCarouselSize(DEFAULT_CAROUSEL_SIZE);
            }
            PageParameter tmpPage = new PageParameter(qc.getCarouselSize());
            List<News> carouselNews = newsService.pageCarousel(qc, tmpPage);

            List<News> listNews = newsService.pageList(qc, pageParameter);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("carouselNews", carouselNews);
            map.put("listNews", listNews);
            return success(map, pageParameter);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(LogConstants.FLAG_ERROR, e);
            return error(e);
        }
    }

    @RequestMapping("/queryCarousel")
    @ResponseBody
    public String queryCarousel(QueryCondition qc) {
        try {
            User u = currentUser();
            if (u != null) {
                qc.setUserId(u.getUid());
            }
            if (qc.getCarouselSize() == null) {
                qc.setCarouselSize(DEFAULT_CAROUSEL_SIZE);
            }
            PageParameter tmpPage = new PageParameter(qc.getCarouselSize());
            List<News> carouselNews = newsService.pageCarousel(qc, tmpPage);
            return success(carouselNews);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(LogConstants.FLAG_ERROR, e);
            return error(e);
        }
    }

    @RequestMapping(value = "/queryList")
    @ResponseBody
    public String queryList(QueryCondition qc, PageParameter pageParameter) {
        try {
            User u = currentUser();
            if (u != null) {
                qc.setUserId(u.getUid());
            }
            List<News> newsList = newsService.pageList(qc, pageParameter);
            return success(newsList, pageParameter);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(LogConstants.FLAG_ERROR, e);
            return error(e);
        }
    }


    @RequestMapping("/queryUserFavorite")
    @ResponseBody
    public String queryUserFavorite(QueryCondition qc, PageParameter page) {
        try {
            User cu = currentUser();
            if (cu == null) {
                return failure(HttpCode.SERVICE_NO_LOGIN, "用户未登陆, 操作失败!");
            } else {
                qc.setUserId(cu.getUid());
            }
            List<News> favoriteNews = newsService.queryUserFavoriteByPage(qc, page);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("favoriteNews", favoriteNews);
            return success(map, page);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(LogConstants.FLAG_ERROR, e);
            return error(e);
        }
    }


    @Override
    protected Set<String> getExculdeFields() {
        Set<String> excludeFields = super.getExculdeFields();
        excludeFields.add("content");
        excludeFields.add("cdate");
        excludeFields.add("udate");
        excludeFields.add("valid");
        excludeFields.add("channelId");
        excludeFields.add("channelName");
        excludeFields.add("channelType");
        excludeFields.add("fileFormat");
        excludeFields.add("isCarousel");
        excludeFields.add("isRecommand");
        excludeFields.add("isTop");
        excludeFields.add("status");
        return excludeFields;
    }

}
