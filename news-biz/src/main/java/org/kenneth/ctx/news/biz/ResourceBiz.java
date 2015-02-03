package org.kenneth.ctx.news.biz;

import org.kenneth.ctx.news.entity.Resource;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.ResourceVo;

import java.util.List;

/**
 * Created by Administrator on 2015/1/15.
 */
public interface ResourceBiz {

    List<Resource> queryAll();

    List<Resource> queryAccountResources(String accountId);

    List<Resource> page(ResourceVo vo, PageParameter pageParameter);

    void add(Resource resource);
}
