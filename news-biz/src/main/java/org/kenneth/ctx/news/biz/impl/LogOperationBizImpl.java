package org.kenneth.ctx.news.biz.impl;

import org.kenneth.ctx.news.biz.LogOperationBiz;
import org.kenneth.ctx.news.entity.LogOperation;
import org.kenneth.ctx.news.mapper.LogOperationMapper;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.LogOperationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/15.
 */
@Service
public class LogOperationBizImpl implements LogOperationBiz {

    @Autowired
    private LogOperationMapper logOperationMapper;

    @Override
    public void record(LogOperation logOperation) {
        logOperationMapper.insert(logOperation);
    }


    @Override
    public List<LogOperation> page(LogOperationVo vo, PageParameter page) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("vo", vo);
        map.put("page", page);
        return logOperationMapper.queryByPage(map);
    }
}
