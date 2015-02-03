package org.kenneth.ctx.news.mapper;

import org.kenneth.ctx.news.entity.UserFavorite;

import java.util.List;

/**
 * Created by Administrator on 2015/1/30.
 */
public interface UserFavoriteMapper extends BaseMapper<UserFavorite> {

    List<Integer> queryFavoritedNids(Integer userId);

}
