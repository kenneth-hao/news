package org.kenneth.ctx.news.mapper;

import org.kenneth.ctx.news.entity.UserChannel;

import java.util.List;

/**
 * Created by Administrator on 2015/1/5.
 */
public interface UserChannelMapper extends BaseMapper<UserChannel> {

    /**
     * 删除用户已定制频道
     *
     * @param uid
     */
    void deleteCustomed(Integer uid);

    /**
     * 查询用户已定制的频道列表
     *
     * @return
     */
    List<UserChannel> queryCustom(Integer uid);

    /**
     * 查询用户未定制的栏目频道
     */
    List<UserChannel> queryUncustomCategory(Integer uid);

    /**
     * 查询用户未定制的车企频道
     */
    List<UserChannel> queryUncustomCarEnterprise(Integer uid);
}
