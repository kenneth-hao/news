package org.kenneth.ctx.news.biz;

import org.kenneth.ctx.news.entity.Role;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.RoleVo;

import java.util.List;

/**
 * Created by Administrator on 2015/1/16.
 */
public interface RoleBiz {

    List<Role> page(RoleVo vo, PageParameter page);

    void add(Role role);

    Role getById(String id);

    void update(Role role);

    void delete(Role role);

}
