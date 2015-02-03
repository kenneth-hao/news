package org.kenneth.ctx.news.biz.impl;

import org.kenneth.ctx.news.biz.UserChannelBiz;
import org.kenneth.ctx.news.entity.UserChannel;
import org.kenneth.ctx.news.mapper.UserChannelMapper;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.UserChannelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/30.
 */
@Service
public class UserChannelBizImpl implements UserChannelBiz {

    @Autowired
    private UserChannelMapper userChannelMapper;

    @Override
    public List<UserChannel> page(UserChannelVo vo, PageParameter page) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("vo", vo);
        map.put("page", page);
        return userChannelMapper.queryByPage(map);
    }
}
