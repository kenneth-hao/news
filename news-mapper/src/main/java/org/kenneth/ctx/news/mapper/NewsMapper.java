package org.kenneth.ctx.news.mapper;

import org.kenneth.ctx.news.entity.News;

import java.util.List;
import java.util.Map;

/**
 * Created by haoyuewen on 8/28/14.
 */
public interface NewsMapper extends BaseMapper<News> {

    List<News> queryUserFavoriteByPage(Map<String, Object> map);

}

