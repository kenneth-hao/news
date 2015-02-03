package org.kenneth.ctx.news.web.admin;

import org.kenneth.ctx.news.biz.UserService;
import org.kenneth.ctx.news.entity.User;
import org.kenneth.ctx.news.utils.json.EasyUIJson;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.QueryCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Administrator on 2015/1/20.
 */
@Controller
@RequestMapping("/admin/user")
public class UserController {

    private final String TEMPLATE_DIR = "user/";

    @Autowired
    UserService userService;

    @RequestMapping(value = "/to_list")
    public ModelAndView toList() {
        ModelAndView mav = new ModelAndView(TEMPLATE_DIR + "list");
        return mav;
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public String list(QueryCondition qc, PageParameter pageParameter) {
        List<User> userList = userService.page(qc, pageParameter);
        return EasyUIJson.datagrid(userList, pageParameter);
    }

}
