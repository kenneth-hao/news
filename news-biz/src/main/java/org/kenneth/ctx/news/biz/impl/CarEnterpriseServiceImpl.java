package org.kenneth.ctx.news.biz.impl;

import org.kenneth.ctx.news.biz.CarEnterpriseService;
import org.kenneth.ctx.news.entity.CarEnterprise;
import org.kenneth.ctx.news.entity.ChannelView;
import org.kenneth.ctx.news.mapper.CarEnterpriseMapper;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.QueryCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/5.
 */
@Service
public class CarEnterpriseServiceImpl implements CarEnterpriseService {

    @Autowired
    private CarEnterpriseMapper carEnterpriseMapper;

    @Override
    public List<CarEnterprise> query(QueryCondition qc) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("qc", qc);
        return carEnterpriseMapper.query(map);
    }

    @Override
    public List<CarEnterprise> page(QueryCondition qc, PageParameter pageParameter) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("qc", qc);
        map.put("page", pageParameter);
        List<CarEnterprise> list = carEnterpriseMapper.queryByPage(map);
        return list;
    }

    @Override
    public boolean delete(CarEnterprise carEnterprise) {

        carEnterpriseMapper.delete(String.valueOf(carEnterprise.getCeid()));

        return true;
    }

    @Override
    public void add(CarEnterprise carEnterprise) {
        carEnterprise.setCdate(new Date());
        carEnterprise.setValid(CarEnterprise.S_VALID_NORMAL);
        carEnterprise.setIsDefault(ChannelView.S_DEFAULT_NO);

        carEnterpriseMapper.insert(carEnterprise);
    }

    @Override
    public CarEnterprise getById(String id) {
        return carEnterpriseMapper.getById(id);
    }

    @Override
    public void update(CarEnterprise carEnterprise) {
        carEnterpriseMapper.update(carEnterprise);
    }
}
