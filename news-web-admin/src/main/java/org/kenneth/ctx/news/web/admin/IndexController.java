package org.kenneth.ctx.news.web.admin;

import org.kenneth.ctx.news.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 2015/1/15.
 */
@Controller
@RequestMapping("/admin")
public class IndexController {

    @Autowired
    private RoleMapper roleMapper;

    @RequestMapping("/to_index")
    public ModelAndView toIndex() {
        ModelAndView mav = new ModelAndView("index");

        return mav;
    }

    @RequestMapping("/to_main")
    public ModelAndView toMain() {
        ModelAndView mav = new ModelAndView("main");

        return mav;
    }

}
