package org.kenneth.ctx.news.biz.impl;

import org.apache.commons.lang.StringUtils;
import org.kenneth.ctx.news.biz.RightsProtectionService;
import org.kenneth.ctx.news.entity.Category;
import org.kenneth.ctx.news.entity.ChannelView;
import org.kenneth.ctx.news.entity.News;
import org.kenneth.ctx.news.entity.RightsProtection;
import org.kenneth.ctx.news.mapper.CategoryMapper;
import org.kenneth.ctx.news.mapper.NewsMapper;
import org.kenneth.ctx.news.mapper.RightsProtectionMapper;
import org.kenneth.ctx.news.utils.exception.BaseServiceException;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.QueryCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/8.
 */
@Service
public class RightsProtectionServiceImpl implements RightsProtectionService {

    @Autowired
    private RightsProtectionMapper rightsProtectionMapper;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<RightsProtection> page(QueryCondition vo, PageParameter pageParameter) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("vo", vo);
        map.put("page", pageParameter);
        return rightsProtectionMapper.queryByPage(map);
    }

    @Override
    public boolean commit(RightsProtection rightsProtection) {
        if (StringUtils.isEmpty(rightsProtection.getTitle())) {
            throw new BaseServiceException("维权标题不能为空!");
        }
        if (StringUtils.isEmpty(rightsProtection.getAuthor())) {
            throw new BaseServiceException("维权发布者不能为空!");
        }
        if (StringUtils.isEmpty(rightsProtection.getMobile())) {
            throw new BaseServiceException("维权发布人的手机号不能为空!");
        }
        if (StringUtils.isEmpty(rightsProtection.getContent())) {
            throw new BaseServiceException("维权内容不能为空!");
        }
        if (StringUtils.isEmpty(rightsProtection.getCarinfo())) {
            throw new BaseServiceException("维权的车辆信息不能为空!");
        }
        if (StringUtils.isEmpty(rightsProtection.getCarno())) {
            throw new BaseServiceException("维权的车牌号不能为空!");
        }

        News news = new News();

        news.setValid(News.S_VALID);
        news.setCdate(new Date());
        Category category = categoryMapper.getById(String.valueOf(Category.S_RIGHTS));
        news.setChannelId(category.getCid());
        news.setChannelName(category.getName());
        news.setChannelType(ChannelView.S_TYPE_CATEGORY);
        news.setPublishTime(new Date());
        // TODO: set S_NOT_AUDIT
        news.setStatus(News.S_AUDITED);
        news.setIsRecommand(News.S_NOT_RECOMMAND);
        news.setIsTop(News.S_NOT_TOP);
        news.setIsCarousel(News.S_NOT_CAROUSEL);
        // TODO: setURL
        news.setLinkUrl("/news/1.html");

        news.setTitle(rightsProtection.getTitle());
        news.setAuthor(rightsProtection.getAuthor());
        news.setContent(rightsProtection.getContent());
        news.setDetailMultiImgPath(rightsProtection.getDetailMultiImgPath());
        news.setListImgPath(rightsProtection.getListImgPath());

        Integer nid = newsMapper.insert(news);

        rightsProtection.setCdate(new Date());
        rightsProtection.setValid(RightsProtection.S_VALID);
        rightsProtection.setNid(nid);

        rightsProtectionMapper.insert(rightsProtection);

        return true;
    }

}
