package org.kenneth.ctx.news.web.mobile;

import org.kenneth.ctx.news.biz.CategoryService;
import org.kenneth.ctx.news.entity.Category;
import org.kenneth.ctx.news.utils.constant.LogConstants;
import org.kenneth.ctx.news.vo.QueryCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2015/1/13.
 */
@Controller
@RequestMapping("/rest/category")
public class RestCategoryController extends RestBaseController {

    private Logger logger = LoggerFactory.getLogger(RestCategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/query")
    @ResponseBody
    public String query(QueryCondition qc) {
        try {
            List<Category> list = categoryService.query(qc);
            return success(list);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(LogConstants.FLAG_ERROR, e);
            return error(e);
        }
    }

}
