package org.kenneth.ctx.news.biz.impl;

import org.kenneth.ctx.news.biz.LoginService;
import org.kenneth.ctx.news.entity.User;
import org.kenneth.ctx.news.mapper.UserMapper;
import org.kenneth.ctx.news.utils.exception.BaseServiceException;
import org.kenneth.ctx.news.vo.QueryCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/9.
 */
@Service
public class LoginServiceImpl implements LoginService {

    public static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    /**
     * 登录
     *
     * @param loginId
     * @param password
     * @throws Exception
     */
    @Override
    public User login(String loginId, String password) throws Exception {
        if (loginId != null && loginId.length() > 0) {
            loginId = loginId.toLowerCase();/*不区分大小写*/
            QueryCondition qc = new QueryCondition();
            qc.setLoginId(loginId);
            qc.setPwd(password);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("vo", qc);
            List<User> users = userMapper.query(map);
            if (users.size() == 1) {
                User user = users.get(0);
                if (user.getValid() == null || false == User.S_VALID_LOGIC_DEL.equals(user.getValid())) {
                    return user;
                } else {
                    throw new BaseServiceException("用户已经被删除，不能再登录");
                }
            } else {
                throw new BaseServiceException("用户名或者密码不正确");
            }
        } else {
            throw new BaseServiceException("用户名不能为空, 登录失败");
        }
    }

}
