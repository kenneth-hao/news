package org.kenneth.ctx.news.biz.impl;

import org.apache.commons.lang.StringUtils;
import org.kenneth.ctx.news.biz.UserService;
import org.kenneth.ctx.news.entity.User;
import org.kenneth.ctx.news.mapper.UserMapper;
import org.kenneth.ctx.news.utils.exception.BaseServiceException;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.QueryCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by haoyuewen on 8/28/14.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userDao;

    @Override
    public List<User> page(QueryCondition vo, PageParameter pageParameter) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("vo", vo);
        map.put("page", pageParameter);
        return userDao.queryByPage(map);
    }

    @Override
    public User register(User user) throws BaseServiceException {
        if (StringUtils.isEmpty(user.getMobile())) {
            throw new BaseServiceException("手机号不能为空!");
        }
        if (StringUtils.isEmpty(user.getEmail())) {
            throw new BaseServiceException("邮箱不能为空!");
        }
        if (StringUtils.isEmpty(user.getMd5Pwd())) {
            throw new BaseServiceException("密码不能为空!");
        }
        QueryCondition qc = new QueryCondition();
        qc.setMobile(user.getMobile());
        qc.setEmail(user.getEmail());
        this.isExist(qc);

        if (StringUtils.isEmpty(user.getNickname())) {
            user.setNickname(user.getMobile());
        }
        if (StringUtils.isEmpty(user.getName())) {
            user.setName(user.getMobile());
        }

        user.setCdate(new Date());
        user.setUsertype(User.S_USERTYPE_ORRICIAL);
        user.setValid(User.S_VALID_NORMAL);

        userDao.insert(user);

        return user;
    }

    @Override
    public boolean isExist(QueryCondition qc) {
        QueryCondition tqc = new QueryCondition();
        tqc.setLoginId(qc.getMobile());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("vo", tqc);
        List<User> users = userDao.query(map);
        if (users.size() > 0) {
            throw new BaseServiceException("手机号已被注册!");
        }
        tqc = new QueryCondition();
        tqc.setEmail(qc.getEmail());
        map.put("vo", tqc);
        users = userDao.query(map);
        if (users.size() > 0) {
            throw new BaseServiceException("邮箱已被注册!");
        }

        return users.size() > 0;
    }
}
