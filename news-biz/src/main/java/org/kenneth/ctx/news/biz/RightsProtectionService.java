package org.kenneth.ctx.news.biz;

import org.kenneth.ctx.news.entity.RightsProtection;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.QueryCondition;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2015/1/8.
 */
@Service
public interface RightsProtectionService {
    boolean commit(RightsProtection rightsProtection);

    List<RightsProtection> page(QueryCondition vo, PageParameter pageParameter);
}
