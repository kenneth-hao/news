package org.kenneth.ctx.news.web.admin;

import org.kenneth.ctx.news.biz.CommentService;
import org.kenneth.ctx.news.entity.Comment;
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
 * Created by Administrator on 2015/1/15.
 */
@Controller
@RequestMapping("/admin/comment")
public class CommentController extends BaseController {

    private final String TEMPLATE_DIR = "comment/";

    @Autowired
    CommentService commentService;

    @RequestMapping(value = "/to_list")
    public ModelAndView toList() {
        ModelAndView mav = new ModelAndView(TEMPLATE_DIR + "list");
        return mav;
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public String list(QueryCondition vo, PageParameter pageParameter) {
        List<Comment> commentList = commentService.page(vo, pageParameter);
        return EasyUIJson.datagrid(commentList, pageParameter);
    }

}
