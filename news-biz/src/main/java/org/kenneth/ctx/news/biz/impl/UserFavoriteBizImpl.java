package org.kenneth.ctx.news.biz.impl;

import org.apache.commons.lang.StringUtils;
import org.kenneth.ctx.news.biz.UserFavoriteBiz;
import org.kenneth.ctx.news.entity.News;
import org.kenneth.ctx.news.entity.User;
import org.kenneth.ctx.news.entity.UserFavorite;
import org.kenneth.ctx.news.mapper.NewsMapper;
import org.kenneth.ctx.news.mapper.UserFavoriteMapper;
import org.kenneth.ctx.news.mapper.UserMapper;
import org.kenneth.ctx.news.utils.exception.BaseServiceException;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.vo.UserFavoriteVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/30.
 */
@Service
public class UserFavoriteBizImpl implements UserFavoriteBiz {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private UserFavoriteMapper userFavoriteMapper;

    @Override
    public void sync(UserFavoriteVo userFavoriteVo) {
        if (userFavoriteVo.getUid() == null) {
            throw new BaseServiceException("用户未登录, 同步失败!");
        }
        if (StringUtils.isEmpty(userFavoriteVo.getLocalFavoriteNidsStr()) == false) {
            List<Integer> favoratedNids = userFavoriteMapper.queryFavoritedNids(userFavoriteVo.getUid());

            String[] nidsStr = userFavoriteVo.getLocalFavoriteNidsStr().split(",");
            for (String nidStr : nidsStr) {
                Integer tmpNid = Integer.parseInt(nidStr.trim());
                if (favoratedNids.contains(tmpNid) == false) {
                    UserFavorite uf = new UserFavorite();
                    uf.setUid(userFavoriteVo.getUid());
                    uf.setNid(tmpNid);

                    this.collect(uf);
                }
            }
        }
    }

    @Override
    public List<UserFavorite> page(UserFavoriteVo vo, PageParameter page) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("vo", vo);
        map.put("page", page);
        return userFavoriteMapper.queryByPage(map);
    }

    @Override
    public void collect(UserFavorite userFavorite) {
        if (userFavorite.getUid() == null) {
            throw new BaseServiceException("未指定收藏咨询的用户");
        }
        if (userFavorite.getNid() == null) {
            throw new BaseServiceException("未指定收藏的咨询");
        }
        User u = userMapper.getById(String.valueOf(userFavorite.getUid()));
        if (u == null) {
            throw new BaseServiceException(String.format("收藏咨询的用户 [id = %s] 不存在", userFavorite.getUid()));
        }
        News n = newsMapper.getById(String.valueOf(userFavorite.getNid()));
        if (n == null) {
            throw new BaseServiceException(String.format("收藏的咨询 [id = %s] 不存在", userFavorite.getNid()));
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("vo", userFavorite);
        UserFavorite uf = userFavoriteMapper.queryOne(map);
        if (uf != null) {
            throw new BaseServiceException(String.format("用户 [id = %s, name = %s] 已收藏的咨询 [id = %s, title = %s]", u.getUid(), u.getName(), n.getNid(), n.getTitle()));
        }

        userFavorite.setUname(u.getName());
        userFavorite.setAuthor(n.getAuthor());
        userFavorite.setCdate(new Date());
        userFavorite.setTitle(n.getTitle());
        userFavorite.setListImgPath(n.getListImgPath());

        userFavoriteMapper.insert(userFavorite);
    }

    @Override
    public void cancelCollect(UserFavorite userFavorite) {
        if (userFavorite.getUid() == null) {
            throw new BaseServiceException("未指定收藏咨询的用户");
        }
        if (userFavorite.getNid() == null) {
            throw new BaseServiceException("未指定收藏的咨询");
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("vo", userFavorite);
        userFavoriteMapper.deleteByVo(map);
        return;
    }
}
