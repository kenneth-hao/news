package org.kenneth.ctx.news.vo;

import org.kenneth.ctx.news.entity.UserFavorite;

/**
 * Created by Administrator on 2015/1/30.
 */
public class UserFavoriteVo extends UserFavorite {

    private String localFavoriteNidsStr;

    public String getLocalFavoriteNidsStr() {
        return localFavoriteNidsStr;
    }

    public void setLocalFavoriteNidsStr(String localFavoriteNidsStr) {
        this.localFavoriteNidsStr = localFavoriteNidsStr;
    }
}
