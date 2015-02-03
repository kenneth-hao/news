package org.kenneth.ctx.news.web.mobile;

import org.kenneth.ctx.news.biz.CommentService;
import org.kenneth.ctx.news.biz.NewsService;
import org.kenneth.ctx.news.entity.Comment;
import org.kenneth.ctx.news.entity.User;
import org.kenneth.ctx.news.utils.constant.LogConstants;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
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
@RequestMapping("/rest/comment")
public class RestCommentController extends RestBaseController {

    private Logger logger = LoggerFactory.getLogger(RestCommentController.class);

    // 默认列表页每次请求的个数
    public static final Integer DEFAULT_PAGE_SIZE = 10;

    @Autowired
    private NewsService newsService;

    @Autowired
    private CommentService commentService;

    @RequestMapping("/query")
    @ResponseBody
    public String query(QueryCondition qc, PageParameter pageParameter) {
        try {
            List<Comment> comments = commentService.page(qc, pageParameter);
            return success(comments, pageParameter);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(LogConstants.FLAG_ERROR, e);
            return error(e);
        }
    }

    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    @ResponseBody
    public String commit(Comment comment) {
        try {
            User cu = currentUser();
            if (cu != null) {
                comment.setUid(cu.getUid());
            }
            Comment insertedComment = commentService.commit(comment);
            QueryCondition qc = new QueryCondition();
            qc.setNewsId(insertedComment.getNid());
            Integer count = commentService.count(qc);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("newComment", insertedComment);
            map.put("commentCount", count);
            return success(map);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(LogConstants.FLAG_ERROR, e.getMessage());
            return error(e);
        }
    }

}
