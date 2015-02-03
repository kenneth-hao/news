package org.kenneth.ctx.news.biz;

import org.kenneth.ctx.news.entity.Category;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.QueryCondition;

import java.util.List;

/**
 * Created by Administrator on 2015/1/13.
 */
public interface CategoryService {

    List<Category> query(QueryCondition qc);

    List<Category> page(QueryCondition qc, PageParameter pageParameter);

    boolean delete(Category category);

    void add(Category category);

    Category getById(String id);

    void update(Category category);
}
