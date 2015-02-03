package org.kenneth.ctx.news.security;

import org.apache.commons.lang.StringUtils;
import org.kenneth.ctx.news.biz.AccountBiz;
import org.kenneth.ctx.news.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 这个类主要是用户登录验证
 */
public class MyAuthenticationFilter extends
        UsernamePasswordAuthenticationFilter {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    /**
     * 登录成功后跳转的地址
     */
    private String successUrl = "/index.html";
    /**
     * 登录失败后跳转的地址
     */
    private String errorUrl = "/admin/login/to_login.html";

    @Autowired
    private AccountBiz accountBiz;

    /**
     * 自定义表单参数的name属性，默认是 j_username 和 j_password
     * 定义登录成功和失败的跳转地址
     */
    public void init() {
        this.setUsernameParameter(USERNAME);
        this.setPasswordParameter(PASSWORD);
        // 验证成功，跳转的页面
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setDefaultTargetUrl(successUrl);
        this.setAuthenticationSuccessHandler(successHandler);

        // 验证失败，跳转的页面
        SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
        failureHandler.setDefaultFailureUrl(errorUrl);
        this.setAuthenticationFailureHandler(failureHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: "
                            + request.getMethod());
        }

        String username = obtainUsername(request).trim();
        String password = obtainPassword(request).trim();

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            BadCredentialsException exception = new BadCredentialsException(
                    "账号或密码不能为空！");// 在界面输出自定义的信息！！
            throw exception;
        }

        // 验证用户账号与密码是否正确
        Account account = accountBiz.querySingleAccount(username);
        if (account == null || (account != null && !account.getPassword().equals(password))) {
            BadCredentialsException exception = new BadCredentialsException(
                    "账号名或密码不匹配！");// 在界面输出自定义的信息！！
            request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
            throw exception;
        }
        // 当验证都通过后，把用户信息放在session里
        request.getSession().setAttribute("accountSession", account);

        // 实现 Authentication
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);

        // 允许子类设置详细属性
        setDetails(request, authRequest);

        // 运行UserDetailsService的loadUserByUsername 再次封装Authentication
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }
}
