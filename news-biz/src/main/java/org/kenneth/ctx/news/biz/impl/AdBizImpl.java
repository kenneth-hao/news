package org.kenneth.ctx.news.biz.impl;

import org.kenneth.ctx.news.biz.AdBiz;
import org.kenneth.ctx.news.entity.Ad;
import org.kenneth.ctx.news.mapper.AdMapper;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.AdVo;
import org.kenneth.ctx.news.vo.QueryCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/26.
 */
@Service
public class AdBizImpl implements AdBiz {

    private final int DEFAULT_AD_NUM = 2;

    @Autowired
    private AdMapper adMapper;

    @Override
    public List<Ad> queryNewestAds(QueryCondition qc) {
        int adNum = 0;
        if (qc == null) {
            adNum = DEFAULT_AD_NUM;
        } else {
            adNum = qc.getAdNum();
        }
        PageParameter page = new PageParameter(adNum);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        return adMapper.queryByPage(map);
    }

    @Override
    public List<Ad> page(AdVo vo, PageParameter pageParameter) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("vo", vo);
        map.put("page", pageParameter);
        return adMapper.queryByPage(map);
    }

    @Override
    public void add(Ad ad) {
        ad.setCdate(new Date());
        adMapper.insert(ad);
    }
}
