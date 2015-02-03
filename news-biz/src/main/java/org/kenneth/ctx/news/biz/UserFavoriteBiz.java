package org.kenneth.ctx.news.biz;

import org.kenneth.ctx.news.entity.UserFavorite;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.UserFavoriteVo;

import java.util.List;

/**
 * Created by Administrator on 2015/1/30.
 */
public interface UserFavoriteBiz {

    List<UserFavorite> page(UserFavoriteVo vo, PageParameter page);

    void collect(UserFavorite userFavorite);

    void cancelCollect(UserFavorite userFavorite);

    void sync(UserFavoriteVo userFavoriteVo);
}
