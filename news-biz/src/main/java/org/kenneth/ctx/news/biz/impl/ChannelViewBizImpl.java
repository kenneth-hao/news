package org.kenneth.ctx.news.biz.impl;

import org.kenneth.ctx.news.biz.ChannelViewBiz;
import org.kenneth.ctx.news.entity.ChannelView;
import org.kenneth.ctx.news.mapper.ChannelViewMapper;
import org.kenneth.ctx.news.vo.QueryCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/21.
 */
@Service
public class ChannelViewBizImpl implements ChannelViewBiz {

    @Autowired
    private ChannelViewMapper channelViewMapper;

    @Override
    public List<ChannelView> query(QueryCondition qc) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("vo", qc);
        List<ChannelView> list = channelViewMapper.query(map);
        return list;
    }

}
