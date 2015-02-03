package org.kenneth.ctx.news.web.admin;

import com.alibaba.fastjson.JSON;
import org.kenneth.ctx.news.biz.ChannelViewBiz;
import org.kenneth.ctx.news.entity.ChannelView;
import org.kenneth.ctx.news.vo.QueryCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2015/1/21.
 */
@Controller
@RequestMapping("admin/channel_view")
public class ChannelViewController {

    @Autowired
    private ChannelViewBiz channelViewBiz;

    @RequestMapping("query")
    @ResponseBody
    public String query(QueryCondition qc) {
        List<ChannelView> list = channelViewBiz.query(qc);
        return JSON.toJSONString(list);
    }

}
