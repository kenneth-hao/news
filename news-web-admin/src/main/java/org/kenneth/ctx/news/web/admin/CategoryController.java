package org.kenneth.ctx.news.web.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.kenneth.ctx.news.biz.CategoryService;
import org.kenneth.ctx.news.entity.Category;
import org.kenneth.ctx.news.utils.exception.BaseServiceException;
import org.kenneth.ctx.news.utils.json.EasyUIJson;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.QueryCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/16.
 */
@Controller
@RequestMapping("/admin/category")
public class CategoryController extends BaseController {

    private final String TEMPLATE_DIR = "category/";

    private Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/query")
    @ResponseBody
    public String query(QueryCondition qc) {
        List<Category> list = categoryService.query(qc);
        return JSON.toJSONString(list);
    }

    @RequestMapping("/to_add")
    public String toAdd() {
        return TEMPLATE_DIR + "add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public String add(Category category) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            categoryService.add(category);
            map.put("result", "ok");
        } catch (BaseServiceException e) {
            map.put("msg", e.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping("/to_update")
    public ModelAndView toUpdate(String id) {
        ModelAndView mav = new ModelAndView(TEMPLATE_DIR + "update");
        Category updatingCategory = categoryService.getById(id);
        mav.addObject("updatingCategory", updatingCategory);
        return mav;
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public String update(Category category) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            categoryService.update(category);
            Category updatedCategory = categoryService.getById(String.valueOf(category.getCid()));
            map.put("updatedCategory", updatedCategory);
            map.put("result", "ok");
        } catch (Exception e) {
            map.put("result", "failure");
            map.put("msg", "服务器出现错误");
            e.printStackTrace();
        }
        return JSON.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
    }

    @RequestMapping("/to_list")
    public String toList() {
        return TEMPLATE_DIR + "list";
    }

    @RequestMapping("/list")
    @ResponseBody
    public String list(QueryCondition qc, PageParameter pageParameter) {
        List<Category> list = categoryService.page(qc, pageParameter);
        return EasyUIJson.datagrid(list, pageParameter);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(Category category) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            boolean res = categoryService.delete(category);
            map.put("result", "ok");
        } catch (Exception e) {
            map.put("result", "failure");
            map.put("msg", "服务器出现错误");
            e.printStackTrace();
        }
        return JSON.toJSONString(map);
    }


}
