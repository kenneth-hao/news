package org.kenneth.ctx.news.biz;

import org.kenneth.ctx.news.entity.ChannelView;
import org.kenneth.ctx.news.entity.UserChannel;
import org.kenneth.ctx.news.vo.QueryCondition;

import java.util.List;

/**
 * Created by Administrator on 2015/1/5.
 */
public interface ChannelService {

    boolean custom(List<UserChannel> customChannels, Integer userId);

    /**
     * TODO: unimplement
     *
     * @return
     */
    List<UserChannel> queryDefaultChannel();

    /**
     * 查询用户已定制的频道
     *
     * @return
     */
    List<ChannelView> queryCustom(QueryCondition qc);

    /**
     * 查询用户未定制的栏目频道
     */
    List<ChannelView> queryUncustomCategory(QueryCondition qc);

    /**
     * 查询用户未定制的车企频道
     */
    List<ChannelView> queryUncustomCarEnterprise(QueryCondition qc);

}
