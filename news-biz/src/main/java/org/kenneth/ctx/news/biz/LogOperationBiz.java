package org.kenneth.ctx.news.biz;

import org.kenneth.ctx.news.entity.LogOperation;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.LogOperationVo;

import java.util.List;

/**
 * Created by Administrator on 2015/1/15.
 */
public interface LogOperationBiz {

    void record(LogOperation logOperation);

    List<LogOperation> page(LogOperationVo vo, PageParameter page);
}
