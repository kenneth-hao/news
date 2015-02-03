package org.kenneth.ctx.news.web.mobile;


import org.kenneth.ctx.news.biz.UserService;
import org.kenneth.ctx.news.utils.constant.HttpCode;
import org.kenneth.ctx.news.utils.exception.BaseServiceException;
import org.kenneth.ctx.news.vo.QueryCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by haoyuewen on 8/30/14.
 */
@Controller
@RequestMapping("/rest/user")
public class RestUserController extends RestBaseController {

    private Logger logger = LoggerFactory.getLogger(RestUserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/isExist")
    @ResponseBody
    public String isExist(QueryCondition qc) {
        try {
            userService.isExist(qc);
            return success();
        } catch (BaseServiceException bse) {
            return failure(HttpCode.SERVICE_FAILURE, bse.getMessage());
        } catch (Exception e) {
            return error(e);
        }

    }
}
