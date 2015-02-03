package org.kenneth.ctx.news.biz;

import org.kenneth.ctx.news.entity.UserChannel;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.UserChannelVo;

import java.util.List;

/**
 * Created by Administrator on 2015/1/30.
 */
public interface UserChannelBiz {
    List<UserChannel> page(UserChannelVo vo, PageParameter pageParameter);
}
