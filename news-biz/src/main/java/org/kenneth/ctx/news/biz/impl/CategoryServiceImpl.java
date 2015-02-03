package org.kenneth.ctx.news.biz.impl;

import org.kenneth.ctx.news.biz.CategoryService;
import org.kenneth.ctx.news.entity.Category;
import org.kenneth.ctx.news.entity.ChannelView;
import org.kenneth.ctx.news.mapper.CategoryMapper;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.QueryCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/13.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> query(QueryCondition qc) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("qc", qc);
        return categoryMapper.query(map);
    }

    @Override
    public void add(Category category) {
        category.setIsDefault(ChannelView.S_DEFAULT_NO);

        categoryMapper.insert(category);
    }

    @Override
    public Category getById(String id) {
        return categoryMapper.getById(id);
    }

    @Override
    public void update(Category category) {
        categoryMapper.update(category);
    }

    @Override
    public boolean delete(Category category) {
        categoryMapper.delete(String.valueOf(category.getCid()));
        return true;
    }

    @Override
    public List<Category> page(QueryCondition qc, PageParameter pageParameter) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("qc", qc);
        map.put("page", pageParameter);
        return categoryMapper.queryByPage(map);
    }
}
