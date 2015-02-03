package org.kenneth.ctx.news.biz.impl;

import org.apache.commons.lang.StringUtils;
import org.kenneth.ctx.news.biz.FeedbackBiz;
import org.kenneth.ctx.news.entity.Feedback;
import org.kenneth.ctx.news.entity.User;
import org.kenneth.ctx.news.mapper.FeedbackMapper;
import org.kenneth.ctx.news.mapper.UserMapper;
import org.kenneth.ctx.news.utils.exception.BaseServiceException;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.FeedbackVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/2/1.
 */
@Service
public class FeedbackBizImpl implements FeedbackBiz {

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Feedback> page(FeedbackVo vo, PageParameter page) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("vo", vo);
        map.put("page", page);
        return feedbackMapper.query(map);
    }

    @Override
    public void add(Feedback feedback) {
        if (feedback.getUid() == null) {
            feedback.setUid(-1);
            feedback.setUname("匿名用户");
        } else {
            User u = userMapper.getById(String.valueOf(feedback.getUid()));
            if (u != null) {
                feedback.setUid(u.getUid());
                feedback.setUname(u.getName());
            } else {
                feedback.setUid(-1);
                feedback.setUname("匿名用户");
            }
        }
        if (StringUtils.isEmpty(feedback.getContent())) {
            throw new BaseServiceException("反馈内容为空, 提交失败!");
        }

        feedback.setStatus(Feedback.S_STATUS_UNREAD);
        feedback.setCdate(new Date());

        feedbackMapper.insert(feedback);
    }

}
