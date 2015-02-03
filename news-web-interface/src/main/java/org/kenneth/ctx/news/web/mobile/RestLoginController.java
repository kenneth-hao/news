package org.kenneth.ctx.news.web.mobile;


import org.kenneth.ctx.news.biz.LoginService;
import org.kenneth.ctx.news.biz.UserService;
import org.kenneth.ctx.news.entity.User;
import org.kenneth.ctx.news.utils.constant.HttpCode;
import org.kenneth.ctx.news.utils.exception.BaseServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/8.
 */
@Controller
@RequestMapping("/rest/login")
public class RestLoginController extends RestBaseController {

    private static final Logger logger = LoggerFactory.getLogger(RestLoginController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    /**
     * 根据用户名，密码进行系统登录
     * 功能描述：
     *
     * @param loginId
     * @param password
     * @return 登录成功则返回loginToken，否则返回空字符串
     * @throws Exception 修改描述
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(String loginId, String password, HttpServletRequest req) throws Exception {
        try {
            Map<String, Object> map = new HashMap<String, Object>();

            User user = loginService.login(loginId, password);

            if (user != null) {
                HttpSession session = req.getSession();
                logout(req);//登录之后，要清除session，否则session里面可能会带上上次登录用户的信息。清除session之后要重新登录，在session中记录登录信息。
                session = req.getSession();
                session.setAttribute("user", user);
                map.put("user", user);
                logger.info("LOGIN", loginId, "成功登录系统");
                return success(map);
            }

            return failure(HttpCode.SERVICE_FAILURE, "登录失败, 用户名或密码错误!");
        } catch (BaseServiceException se) {
            logger.info("LOGIN", loginId, "登录系统失败");
            return failure(HttpCode.SERVICE_FAILURE, se.getMessage());
        } catch (Exception e) {
            return error(e);
        }
    }

    @RequestMapping("/islogon")
    @ResponseBody
    public String islogon() {
        Map<String, Object> map = new HashMap<String, Object>();
        User u = currentUser();
        if (u != null) {
            map.put("user", u);
            map.put("islogon", true);
        } else {
            map.put("islogon", false);
        }
        return success(map);
    }

    /**
     * 注销当前登录用户的所有信息
     * 清空session
     *
     * @throws Exception
     * @版本 1.0
     */
    @RequestMapping("/logout")
    @ResponseBody
    public String logout(HttpServletRequest req) throws Exception {
        try {
            HttpSession session = req.getSession();
            session.invalidate();
            req.getSession(true);
            return success();
        } catch (Exception e) {
            return error(e);
        }
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public String register(User user) throws Exception {
        try {
            User newUser = userService.register(user);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("newUser", newUser);
            return success(map);
        } catch (BaseServiceException bse) {
            return failure(HttpCode.SERVICE_FAILURE, bse.getMessage());
        } catch (Exception e) {
            return error(e);
        }
    }

}
