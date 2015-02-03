package org.kenneth.ctx.news.web.mobile;

import org.kenneth.ctx.news.biz.FeedbackBiz;
import org.kenneth.ctx.news.entity.Feedback;
import org.kenneth.ctx.news.entity.User;
import org.kenneth.ctx.news.utils.constant.LogConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2015/1/26.
 */
@Controller
@RequestMapping("/rest/feedback")
public class RestFeedbackController extends RestBaseController {

    private Logger logger = LoggerFactory.getLogger(RestFeedbackController.class);

    @Autowired
    private FeedbackBiz feedbackBiz;

    @RequestMapping(value = "/propose", method = RequestMethod.POST)
    @ResponseBody
    public String propose(Feedback feedback) {
        try {
            User u = currentUser();
            if (u != null) {
                feedback.setUid(u.getUid());
            }
            feedbackBiz.add(feedback);
            return success();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(LogConstants.FLAG_ERROR, e);
            return error(e);
        }
    }

}
