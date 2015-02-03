package org.kenneth.ctx.news.web.admin;

import org.kenneth.ctx.news.biz.RightsProtectionService;
import org.kenneth.ctx.news.entity.RightsProtection;
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
@RequestMapping("/admin/rights_protection")
public class RightsProtectionController extends BaseController {

    private final String TEMPLATE_DIR = "rights_protection/";

    @Autowired
    private RightsProtectionService rightsProtectionService;

    @RequestMapping(value = "/to_list")
    public ModelAndView toList() {
        ModelAndView mav = new ModelAndView(TEMPLATE_DIR + "list");
        return mav;
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public String list(QueryCondition vo, PageParameter pageParameter) {
        List<RightsProtection> list = rightsProtectionService.page(vo, pageParameter);
        return EasyUIJson.datagrid(list, pageParameter);
    }

}
