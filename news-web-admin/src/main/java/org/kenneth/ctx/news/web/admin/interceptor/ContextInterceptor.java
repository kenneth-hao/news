package org.kenneth.ctx.news.web.admin.interceptor;

import org.kenneth.ctx.news.utils.constant.ConfigPropKey;
import org.kenneth.ctx.news.utils.spring.CustomPropertyConfigurer;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2015/1/21.
 */
public class ContextInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        httpServletRequest.setAttribute("ctxImgPath", CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_PATH));
        httpServletRequest.setAttribute("ctx", httpServletRequest.getContextPath());
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
