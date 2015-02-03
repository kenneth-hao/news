package org.kenneth.ctx.news.biz.impl;

import org.kenneth.ctx.news.biz.RoleBiz;
import org.kenneth.ctx.news.entity.Role;
import org.kenneth.ctx.news.mapper.RoleMapper;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.RoleVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/16.
 */
@Service
public class RoleBizImpl implements RoleBiz {


    private static final Logger logger = LoggerFactory.getLogger(RoleBizImpl.class);

    @Autowired
    RoleMapper roleMapper;

    @Override
    public Role getById(String id) {
        return roleMapper.getById(id);
    }

    @Override
    public List<Role> page(RoleVo vo, PageParameter page) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("vo", vo);
        map.put("page", page);
        return roleMapper.queryByPage(map);
    }

    @Override
    public void add(Role role) {

        role.setCdate(new Date());
        role.setStatus(Role.S_STATUS_NORMAL);

        roleMapper.insert(role);
    }

    @Override
    public void update(Role role) {
        roleMapper.update(role);
    }

    @Override
    public void delete(Role role) {
        roleMapper.delete(String.valueOf(role.getId()));
    }

}
