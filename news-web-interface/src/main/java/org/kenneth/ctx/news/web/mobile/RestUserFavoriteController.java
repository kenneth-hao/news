package org.kenneth.ctx.news.web.mobile;

import org.kenneth.ctx.news.biz.UserFavoriteBiz;
import org.kenneth.ctx.news.entity.User;
import org.kenneth.ctx.news.entity.UserFavorite;
import org.kenneth.ctx.news.utils.constant.HttpCode;
import org.kenneth.ctx.news.utils.constant.LogConstants;
import org.kenneth.ctx.news.utils.exception.BaseServiceException;
import org.kenneth.ctx.news.vo.UserFavoriteVo;
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
@RequestMapping("/rest/user_favorite")
public class RestUserFavoriteController extends RestBaseController {

    private Logger logger = LoggerFactory.getLogger(RestUserFavoriteController.class);

    @Autowired
    private UserFavoriteBiz userFavoriteBiz;

    @RequestMapping(method = RequestMethod.POST, value = "sync")
    @ResponseBody
    public String sync(UserFavoriteVo vo) {
        try {
            User cu = currentUser();
            if (cu == null) {
                return failure(HttpCode.SERVICE_NO_LOGIN, "用户未登陆, 操作失败!");
            } else {
                vo.setUid(cu.getUid());
            }
            userFavoriteBiz.sync(vo);
            return success();
        } catch (BaseServiceException bse) {
            bse.printStackTrace();
            logger.error(LogConstants.FLAG_ERROR, bse);
            return failure(HttpCode.SERVICE_FAILURE, bse.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(LogConstants.FLAG_ERROR, e);
            return error(e);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "collect")
    @ResponseBody
    public String collect(UserFavorite userFavorite) {
        try {
            User cu = currentUser();
            if (cu == null) {
                return failure(HttpCode.SERVICE_NO_LOGIN, "用户未登陆, 操作失败!");
            } else {
                userFavorite.setUid(cu.getUid());
            }
            userFavoriteBiz.collect(userFavorite);
            return success();
        } catch (BaseServiceException bse) {
            bse.printStackTrace();
            logger.error(LogConstants.FLAG_ERROR, bse);
            return failure(HttpCode.SERVICE_FAILURE, bse.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(LogConstants.FLAG_ERROR, e);
            return error(e);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "cancel_collect")
    @ResponseBody
    public String cancelCollect(UserFavorite userFavorite) {
        try {
            User cu = currentUser();
            if (cu == null) {
                return failure(HttpCode.SERVICE_NO_LOGIN, "用户未登陆, 操作失败!");
            } else {
                userFavorite.setUid(cu.getUid());
            }
            userFavoriteBiz.cancelCollect(userFavorite);
            return success();
        } catch (BaseServiceException bse) {
            bse.printStackTrace();
            logger.error(LogConstants.FLAG_ERROR, bse);
            return failure(HttpCode.SERVICE_FAILURE, bse.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(LogConstants.FLAG_ERROR, e);
            return error(e);
        }
    }

}
