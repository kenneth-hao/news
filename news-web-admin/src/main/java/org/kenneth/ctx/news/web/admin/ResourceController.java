package org.kenneth.ctx.news.web.admin;

import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.PropertyUtils;
import org.kenneth.ctx.news.biz.ResourceBiz;
import org.kenneth.ctx.news.entity.Resource;
import org.kenneth.ctx.news.utils.exception.BaseServiceException;
import org.kenneth.ctx.news.utils.json.EasyUIJson;
import org.kenneth.ctx.news.utils.json.TreeObject;
import org.kenneth.ctx.news.utils.json.TreeUtil;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.ResourceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/15.
 */
@Controller
@RequestMapping("/admin/resource")
public class ResourceController extends BaseController {

    private final String TEMPLATE_DIR = "resource/";

    @Autowired
    ResourceBiz resourceBiz;

    @RequestMapping(value = "/to_list")
    public ModelAndView toList() {
        ModelAndView mav = new ModelAndView(TEMPLATE_DIR + "list");
        return mav;
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public String list(ResourceVo vo, PageParameter pageParameter) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<Resource> resourceList = resourceBiz.queryAll();
        List<TreeObject> treeObjects = new ArrayList<TreeObject>();
        for (Resource res : resourceList) {//转换为树对象
            TreeObject t = new TreeObject();
            PropertyUtils.copyProperties(t, res);
            treeObjects.add(t);
        }
        List<TreeObject> ns = TreeUtil.getChildResourcess(treeObjects, -1);
        return EasyUIJson.treegrid(ns);
    }

    @RequestMapping(value = "/to_add")
    public ModelAndView toAdd() {
        ModelAndView mav = new ModelAndView(TEMPLATE_DIR + "add");
        return mav;
    }

    @RequestMapping(value = "/tree")
    @ResponseBody
    public String tree() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
            List<Resource> resourceList = resourceBiz.queryAll();
            List<TreeObject> treeObjects = new ArrayList<TreeObject>();
            for (Resource res : resourceList) {//转换为树对象
                TreeObject t = new TreeObject();
                PropertyUtils.copyProperties(t, res);
                treeObjects.add(t);
            }
            List<TreeObject> ns = TreeUtil.getChildResourcess(treeObjects, -1);
            return EasyUIJson.tree(ns, "id", "name", "children");
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public String add(Resource resource) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            resourceBiz.add(resource);
            map.put("result", "ok");
        } catch (BaseServiceException e) {
            map.put("msg", e.getMessage());
        }
        return JSON.toJSONString(map);
    }
}
