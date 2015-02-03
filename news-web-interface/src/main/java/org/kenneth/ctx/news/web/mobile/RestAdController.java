package org.kenneth.ctx.news.web.mobile;

import org.kenneth.ctx.news.biz.AdBiz;
import org.kenneth.ctx.news.entity.Ad;
import org.kenneth.ctx.news.vo.QueryCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2015/1/26.
 */
@Controller
@RequestMapping("/rest/ad")
public class RestAdController extends RestBaseController {

    @Autowired
    private AdBiz adBiz;

    @RequestMapping("/query")
    @ResponseBody
    public String query(QueryCondition qc) {
        try {
            List<Ad> adList = adBiz.queryNewestAds(qc);
            return success(adList);
        } catch (Exception e) {
            e.printStackTrace();
            return error(e);
        }
    }

}
