package org.kenneth.ctx.news.aop;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.kenneth.ctx.news.biz.LogOperationBiz;
import org.kenneth.ctx.news.biz.impl.*;
import org.kenneth.ctx.news.entity.LogOperation;
import org.kenneth.ctx.news.utils.http.HttpUtils;
import org.kenneth.ctx.news.utils.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * AOP注解方法实现日志管理 利用spring AOP 切面技术记录日志
 * <p/>
 * 定义切面类（这个是切面类和切入点整天合在一起的),这种情况是共享切入点情况;
 */
@Aspect
// 该注解标示该类为切面类
@Component
public class LogOperationAop {

    @Autowired
    private LogOperationBiz logOperationBiz;

    public Object logOperation(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Object result = null;
        // 执行方法名
        String methodName = point.getSignature().getName();
        Class<?> targetClass = point.getTarget().getClass();
        String className = point.getTarget().getClass().getSimpleName();
        String user = null;
        Long start = 0L;
        Long end = 0L;
        String ip = null;

        // 当前用户
        // 执行方法所消耗的时间
        start = System.currentTimeMillis();
        result = point.proceed();
        end = System.currentTimeMillis();

        try {
            ip = HttpUtils.toIpAddr(request);
        } catch (Exception e) {
            ip = "无法获取登录用户Ip";
        }
        try {
            // 登录名
            user = SecurityUtils.findAuthenticatedUsername();
            if (StringUtils.isEmpty(user)) {
                user = "无法获取登录用户信息！";
            }
        } catch (Exception e) {
            user = "无法获取登录用户信息！";
        }
        String name = className;
        // 操作类型
        String opertype = methodName;

        boolean b = false;
        String[] clazzMethod = {"add", "insert", "save", "update", "modify", "delete"};
        for (String string : clazzMethod) {
            if (methodName.indexOf(string) > -1) {
                b = true;
            }
        }
        if (b) {
            Long time = end - start;
            LogOperation logOperation = new LogOperation();
            logOperation.setUsername(user);
            logOperation.setModule(MAP_MODULE_NAME.get(targetClass));
            logOperation.setAction(opertype);
            logOperation.setActionTime(new Date());
            logOperation.setUserIP(ip);
            logOperation.setElapseTime(time.toString());
            logOperationBiz.record(logOperation);
        }

        return result;
    }

    private static Map<Class<?>, String> MAP_MODULE_NAME = new HashMap<Class<?>, String>();

    static {
        MAP_MODULE_NAME.put(AccountBizImpl.class, "账号管理");
        MAP_MODULE_NAME.put(CarEnterpriseServiceImpl.class, "车企管理");
        MAP_MODULE_NAME.put(CategoryServiceImpl.class, "栏目管理");
        MAP_MODULE_NAME.put(ChannelServiceImpl.class, "频道管理");
        MAP_MODULE_NAME.put(CommentServiceImpl.class, "评论管理");
        MAP_MODULE_NAME.put(LogLoginBizImpl.class, "登录日志管理");
        MAP_MODULE_NAME.put(LogOperationBizImpl.class, "操作日志管理");
        MAP_MODULE_NAME.put(NewsServiceImpl.class, "新闻管理");
        MAP_MODULE_NAME.put(ResourceBizImpl.class, "资源管理");
        MAP_MODULE_NAME.put(RightsProtectionServiceImpl.class, "维权管理");
        MAP_MODULE_NAME.put(RoleBizImpl.class, "角色管理");
        MAP_MODULE_NAME.put(UserServiceImpl.class, "用户管理");
    }

}
