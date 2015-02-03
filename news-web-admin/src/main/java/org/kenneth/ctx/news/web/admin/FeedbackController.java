package org.kenneth.ctx.news.web.admin;

import org.kenneth.ctx.news.biz.FeedbackBiz;
import org.kenneth.ctx.news.entity.Feedback;
import org.kenneth.ctx.news.utils.json.EasyUIJson;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.FeedbackVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Administrator on 2015/2/1.
 */
@Controller
@RequestMapping("/admin/feedback")
public class FeedbackController extends BaseController {

    private final String TEMPLATE_DIR = "feedback/";

    @Autowired
    private FeedbackBiz feedbackBiz;

    @RequestMapping(value = "/to_list")
    public ModelAndView toList() {
        ModelAndView mav = new ModelAndView(TEMPLATE_DIR + "list");
        return mav;
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public String list(FeedbackVo vo, PageParameter pageParameter) {
        List<Feedback> feedbackList = feedbackBiz.page(vo, pageParameter);
        return EasyUIJson.datagrid(feedbackList, pageParameter);
    }

}
