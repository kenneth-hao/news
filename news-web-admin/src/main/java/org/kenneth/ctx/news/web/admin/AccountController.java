package org.kenneth.ctx.news.web.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.kenneth.ctx.news.biz.AccountBiz;
import org.kenneth.ctx.news.entity.Account;
import org.kenneth.ctx.news.utils.exception.BaseServiceException;
import org.kenneth.ctx.news.utils.json.EasyUIJson;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.AccountVo;
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
@RequestMapping("/admin/account")
public class AccountController extends BaseController {

    private final String TEMPLATE_DIR = "account/";

    @Autowired
    AccountBiz accountBiz;

    @RequestMapping("/to_add")
    public String toAdd() {
        return TEMPLATE_DIR + "add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public String add(Account account) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            accountBiz.add(account);
            map.put("result", "ok");
        } catch (BaseServiceException e) {
            map.put("msg", e.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping("/to_update")
    public ModelAndView toUpdate(String id) {
        ModelAndView mav = new ModelAndView(TEMPLATE_DIR + "update");
        Account updatingAccount = accountBiz.getById(id);
        mav.addObject("updatingAccount", updatingAccount);
        return mav;
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public String update(Account account) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            accountBiz.update(account);
            Account updatedAccount = accountBiz.getById(String.valueOf(account.getId()));
            map.put("updatedAccount", updatedAccount);
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
    public String delete(Account account) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            accountBiz.delete(account);
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
    public String list(AccountVo vo, PageParameter pageParameter) {
        List<Account> accountList = accountBiz.page(vo, pageParameter);
        return EasyUIJson.datagrid(accountList, pageParameter);
    }

}
