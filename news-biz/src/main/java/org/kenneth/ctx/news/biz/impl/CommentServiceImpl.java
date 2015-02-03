package org.kenneth.ctx.news.biz.impl;

import org.kenneth.ctx.news.biz.CommentService;
import org.kenneth.ctx.news.entity.Comment;
import org.kenneth.ctx.news.entity.News;
import org.kenneth.ctx.news.entity.User;
import org.kenneth.ctx.news.mapper.CommentMapper;
import org.kenneth.ctx.news.mapper.NewsMapper;
import org.kenneth.ctx.news.mapper.UserMapper;
import org.kenneth.ctx.news.utils.exception.BaseServiceException;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.QueryCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/8.
 */
@Service
public class CommentServiceImpl implements CommentService {

    private Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NewsMapper newsMapper;

    @Override
    public Integer count(QueryCondition qc) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("vo", qc);
        return commentMapper.count(map);
    }

    @Override
    public Comment commit(Comment comment) {
        if (comment.getNid() == null) {
            throw new BaseServiceException("未设置评论的新闻");
        }

        News news = newsMapper.getById(String.valueOf(comment.getNid()));
        if (news == null) {
            throw new BaseServiceException(String.format("新闻[ id = %s ] 不存在.", comment.getNid()));
        }
        User user = userMapper.getById(String.valueOf(comment.getUid()));
        if (user == null) {
            comment.setUid(-1);
            comment.setUname("匿名用户");
            logger.warn(String.format("评论者[ id = %s ] 不存在.", comment.getUid()));
        } else {
            comment.setUname(user.getName());
        }

        comment.setNtitle(news.getTitle());
        comment.setCdate(new Date());
        comment.setValid(Comment.S_VALID);
        commentMapper.insert(comment);
        return comment;
    }

    @Override
    public List<Comment> page(QueryCondition qc, PageParameter page) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("vo", qc);
        map.put("page", page);
        return commentMapper.queryByPage(map);
    }

}
