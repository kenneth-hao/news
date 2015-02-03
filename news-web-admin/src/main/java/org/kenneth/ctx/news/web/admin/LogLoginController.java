package org.kenneth.ctx.news.web.admin;

import org.kenneth.ctx.news.biz.LogLoginBiz;
import org.kenneth.ctx.news.entity.LogLogin;
import org.kenneth.ctx.news.utils.json.EasyUIJson;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.LogLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Administrator on 2015/1/15.
 */
@Controller
@RequestMapping("/admin/log_login")
public class LogLoginController extends BaseController {

    private final String TEMPLATE_DIR = "log_login/";

    @Autowired
    LogLoginBiz logLoginBiz;

    @RequestMapping(value = "/to_list")
    public ModelAndView toList() {
        ModelAndView mav = new ModelAndView(TEMPLATE_DIR + "list");
        return mav;
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public String list(LogLoginVo vo, PageParameter pageParameter) {
        List<LogLogin> logLoginList = logLoginBiz.page(vo, pageParameter);
        return EasyUIJson.datagrid(logLoginList, pageParameter);
    }

}
