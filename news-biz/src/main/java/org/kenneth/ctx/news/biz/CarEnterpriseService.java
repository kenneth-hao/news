package org.kenneth.ctx.news.biz;

import org.kenneth.ctx.news.entity.CarEnterprise;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.QueryCondition;

import java.util.List;

/**
 * Created by Administrator on 2015/1/7.
 */
public interface CarEnterpriseService {

    List<CarEnterprise> query(QueryCondition qc);

    List<CarEnterprise> page(QueryCondition qc, PageParameter pageParameter);

    boolean delete(CarEnterprise carEnterprise);

    void add(CarEnterprise carEnterprise);

    CarEnterprise getById(String id);

    void update(CarEnterprise carEnterprise);
}
