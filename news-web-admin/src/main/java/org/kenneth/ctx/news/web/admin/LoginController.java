package org.kenneth.ctx.news.web.admin;

import org.apache.commons.lang.StringUtils;
import org.kenneth.ctx.news.biz.AccountBiz;
import org.kenneth.ctx.news.biz.LogLoginBiz;
import org.kenneth.ctx.news.entity.Account;
import org.kenneth.ctx.news.utils.http.HttpUtils;
import org.kenneth.ctx.news.utils.security.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/15.
 */
@Controller
@RequestMapping("/admin/login")
public class LoginController extends BaseController {

    @Autowired
    private AccountBiz accountBiz;

    @Autowired
    private LogLoginBiz logLoginBiz;

    @Autowired
    private AuthenticationManager myAuthenticationManager;

    @RequestMapping(value = "to_login", method = RequestMethod.GET)
    public ModelAndView toLogin() {
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(String username, String password, HttpServletRequest request) {
        try {
            if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
                request.setAttribute("error", "用户名或密码不能为空！");
                return "";
            }
            // 验证用户账号与密码是否正确
            Account account = accountBiz.querySingleAccount(username);
            if (account == null) {
                request.setAttribute("error", "用户或密码不正确！");
                return "";
            } else if (account != null && StringUtils.isEmpty(account.getUserName()) && !MD5Utils.getMd5(password).equals(account.getPassword())) {
                request.setAttribute("error", "用户或密码不正确！");
                return "";
            }
            Authentication authentication = myAuthenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, account.getPassword()));
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
            // 当验证都通过后，把用户信息放在session里
            request.getSession().setAttribute("userSession", account);
            request.getSession().setAttribute("userSessionId", account.getId());
            System.out.println(authentication.getPrincipal().toString());
            String userId = request.getSession().getAttribute("userSessionId").toString();
            String userName = account.getUserName();

            Map<String, Object> extraAttr = new HashMap<String, Object>();
            extraAttr.put("ip", HttpUtils.toIpAddr(request));
            logLoginBiz.record(account, extraAttr);

            request.getSession().setAttribute("userRole", authentication.getPrincipal());

            request.removeAttribute("error");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "登录异常，请联系管理员！");
            return "";
        }
        return "redirect:/admin/to_index.html";
    }

}
