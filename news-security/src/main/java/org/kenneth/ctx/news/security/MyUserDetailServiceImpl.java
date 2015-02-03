package org.kenneth.ctx.news.security;

import org.kenneth.ctx.news.biz.AccountBiz;
import org.kenneth.ctx.news.biz.ResourceBiz;
import org.kenneth.ctx.news.entity.Account;
import org.kenneth.ctx.news.entity.Resource;
import org.kenneth.ctx.news.utils.constant.ConfigPropKey;
import org.kenneth.ctx.news.utils.spring.CustomPropertyConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User userdetail该类实现 UserDetails 接口，该类在验证成功后会被保存在当前回话的principal对象中
 * <p/>
 * 获得对象的方式：
 * WebUserDetails webUserDetails = (WebUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 * <p/>
 * 或在JSP中：
 * <sec:authentication property="principal.username"/>
 * <p/>
 * 如果需要包括用户的其他属性，可以实现 UserDetails 接口中增加相应属性即可
 * 权限验证类
 */
@Service
public class MyUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private AccountBiz accountBiz;
    @Autowired
    private ResourceBiz resourceBiz;

    // 登录验证
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //取得用户的权限
        Account users = accountBiz.querySingleAccount(username);
        if (users == null)
            throw new UsernameNotFoundException(username + " not exist!");
        Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(users);
        // 封装成spring security的user
        User userdetail = new User(
                users.getUserName(),
                users.getPassword(),
                "1".equals(users.getStatus()) ? true : false,  //账号状态  0 表示停用  1表示启用
                true,
                true,
                true,
                grantedAuths    //用户的权限
        );
        return userdetail;
    }

    // 取得用户的权限
    private Set<GrantedAuthority> obtionGrantedAuthorities(Account account) {
        List<Resource> menus = null;
        if (CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_USER_ROOT).equals(account.getUserName())) {//根据账号拥有所有权限
            menus = resourceBiz.queryAll();
        } else {
            menus = resourceBiz.queryAccountResources(String.valueOf(account.getId()));
        }
        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
        for (Resource res : menus) {
            // 用户可以访问的资源名称（或者说用户所拥有的权限） 注意：必须"ROLE_"开头
            // 关联代码：applicationContext-security.xml
            // 关联代码：com.huaxin.security.MySecurityMetadataSource#loadResourceDefine
            authSet.add(new SimpleGrantedAuthority("ROLE_" + res.getAlias()));
        }
        return authSet;
    }
}