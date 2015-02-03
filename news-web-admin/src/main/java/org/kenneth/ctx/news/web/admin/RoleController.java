package org.kenneth.ctx.news.web.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.kenneth.ctx.news.biz.RoleBiz;
import org.kenneth.ctx.news.entity.Role;
import org.kenneth.ctx.news.utils.exception.BaseServiceException;
import org.kenneth.ctx.news.utils.json.EasyUIJson;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.RoleVo;
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
 * Created by Administrator on 2015/1/15.
 */
@Controller
@RequestMapping("/admin/role")
public class RoleController extends BaseController {

    private final String TEMPLATE_DIR = "role/";

    @Autowired
    RoleBiz roleBiz;

    @RequestMapping("/to_add")
    public String toAdd() {
        return TEMPLATE_DIR + "add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public String add(Role role) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            roleBiz.add(role);
            map.put("result", "ok");
        } catch (BaseServiceException e) {
            map.put("msg", e.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping("/to_update")
    public ModelAndView toUpdate(String id) {
        ModelAndView mav = new ModelAndView(TEMPLATE_DIR + "update");
        Role updatingRole = roleBiz.getById(id);
        mav.addObject("updatingRole", updatingRole);
        return mav;
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public String update(Role role) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            roleBiz.update(role);
            Role updatedRole = roleBiz.getById(String.valueOf(role.getId()));
            map.put("updatedRole", updatedRole);
            map.put("result", "ok");
        } catch (Exception e) {
            map.put("result", "failure");
            map.put("msg", "服务器出现错误");
            e.printStackTrace();
        }
        return JSON.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(Role role) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            roleBiz.delete(role);
            map.put("result", "ok");
        } catch (Exception e) {
            map.put("result", "failure");
            map.put("msg", "服务器出现错误");
            e.printStackTrace();
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/to_list")
    public ModelAndView toList() {
        ModelAndView mav = new ModelAndView(TEMPLATE_DIR + "list");
        return mav;
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public String list(RoleVo vo, PageParameter pageParameter) {
        List<Role> roleList = roleBiz.page(vo, pageParameter);
        return EasyUIJson.datagrid(roleList, pageParameter);
    }

}
