package org.kenneth.ctx.news.web.admin;

import org.kenneth.ctx.news.biz.UserChannelBiz;
import org.kenneth.ctx.news.entity.UserChannel;
import org.kenneth.ctx.news.utils.json.EasyUIJson;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.UserChannelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Administrator on 2015/1/30.
 */
@Controller
@RequestMapping("/admin/user_channel")
public class UserChannelController {

    private final String TEMPLATE_DIR = "user_channel/";

    @Autowired
    private UserChannelBiz userChannelBiz;

    @RequestMapping(value = "/to_list")
    public ModelAndView toList() {
        ModelAndView mav = new ModelAndView(TEMPLATE_DIR + "list");
        return mav;
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public String list(UserChannelVo vo, PageParameter pageParameter) {
        List<UserChannel> userChannelList = userChannelBiz.page(vo, pageParameter);
        return EasyUIJson.datagrid(userChannelList, pageParameter);
    }

}
