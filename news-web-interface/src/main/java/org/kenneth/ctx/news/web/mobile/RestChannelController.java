package org.kenneth.ctx.news.web.mobile;

import com.alibaba.fastjson.JSON;
import org.kenneth.ctx.news.biz.ChannelService;
import org.kenneth.ctx.news.entity.ChannelView;
import org.kenneth.ctx.news.entity.User;
import org.kenneth.ctx.news.entity.UserChannel;
import org.kenneth.ctx.news.utils.constant.HttpCode;
import org.kenneth.ctx.news.utils.constant.LogConstants;
import org.kenneth.ctx.news.vo.QueryCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/5.
 */
@Controller
@RequestMapping("/rest/channel")
public class RestChannelController extends RestBaseController {

    private Logger logger = LoggerFactory.getLogger(RestChannelController.class);

    @Autowired
    private ChannelService channelService;

    @RequestMapping(value = "/custom", method = RequestMethod.POST)
    @ResponseBody
    public String custom(String customChannel) {
        try {
            List<UserChannel> channels = JSON.parseArray(customChannel, UserChannel.class);
            User cu = currentUser();
            if (cu == null) {
                return failure(HttpCode.SERVICE_NO_LOGIN, "用户未登陆, 操作失败!");
            }
            channelService.custom(channels, cu.getUid());
            return success();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(LogConstants.FLAG_ERROR, e.getMessage());
            return error(e);
        }
    }

    @RequestMapping("/query")
    @ResponseBody
    public String query(QueryCondition qc) {
        try {
            User cu = currentUser();

            if (cu != null) {
                qc.setUserId(cu.getUid());
            }

            List<ChannelView> customed = channelService.queryCustom(qc);
            List<ChannelView> uncustomCategory = channelService.queryUncustomCategory(qc);
            List<ChannelView> uncustomCarEnterprise = channelService.queryUncustomCarEnterprise(qc);

            Map<String, Object> channels = new HashMap<String, Object>();
            channels.put("customed", customed);
            channels.put("uncustomCategory", uncustomCategory);
            channels.put("uncustomCarEnterprise", uncustomCarEnterprise);
            return success(channels);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(LogConstants.FLAG_ERROR, e);
            return error(e);
        }
    }

    @RequestMapping("/queryCustom")
    @ResponseBody
    public String queryCustom(QueryCondition qc) {
        try {
            User cu = currentUser();
            if (cu != null) {
                qc.setUserId(cu.getUid());
            }
            List<ChannelView> channels = channelService.queryCustom(qc);
            return success(channels);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(LogConstants.FLAG_ERROR, e);
            return error(e);
        }
    }

    @RequestMapping("/queryUncustomCategory")
    @ResponseBody
    public String queryUncustomCategory(QueryCondition qc) {
        try {
            User cu = currentUser();
            if (cu != null) {
                qc.setUserId(cu.getUid());
            }
            List<ChannelView> channels = channelService.queryUncustomCategory(qc);
            return success(channels);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(LogConstants.FLAG_ERROR, e);
            return error(e);
        }
    }

    @RequestMapping("/queryUncustomCarEnterprise")
    @ResponseBody
    public String queryUncustomCarEnterprise(QueryCondition qc) {
        try {
            User cu = currentUser();
            if (cu != null) {
                qc.setUserId(cu.getUid());
            }
            List<ChannelView> channels = channelService.queryUncustomCategory(qc);
            return success(channels);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(LogConstants.FLAG_ERROR, e);
            return error(e);
        }
    }
}
