package org.kenneth.ctx.news.biz.impl;

import org.kenneth.ctx.news.biz.ResourceBiz;
import org.kenneth.ctx.news.entity.Resource;
import org.kenneth.ctx.news.mapper.ResourceMapper;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.ResourceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/15.
 */
@Service
public class ResourceBizImpl implements ResourceBiz {

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public List<Resource> queryAll() {
        return resourceMapper.queryAll();
    }

    @Override
    public List<Resource> queryAccountResources(String accountId) {
        return resourceMapper.queryAccountResources(accountId);
    }

    @Override
    public List<Resource> page(ResourceVo vo, PageParameter pageParameter) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("vo", vo);
        map.put("page", pageParameter);
        return resourceMapper.queryByPage(map);
    }

    @Override
    public void add(Resource resource) {
        if (resource.getParentId() != null) {
            Resource parentRes = resourceMapper.getById(String.valueOf(resource.getParentId()));
            resource.setParentId(parentRes.getId());
            resource.setParentName(parentRes.getName());
            resource.setLevel(parentRes.getLevel() + 1);
        } else {
            resource.setLevel(Resource.TOP_LEVEL);
            resource.setParentId(Resource.TOP_PARENT_ID);
        }

        resource.setCdate(new Date());
        resource.setType(Resource.S_TYPE_MENU);

        resourceMapper.insert(resource);
    }
}
