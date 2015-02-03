package org.kenneth.ctx.news.biz.impl;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.kenneth.ctx.news.biz.ChannelService;
import org.kenneth.ctx.news.entity.*;
import org.kenneth.ctx.news.mapper.*;
import org.kenneth.ctx.news.utils.exception.BaseServiceException;
import org.kenneth.ctx.news.vo.QueryCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2015/1/5.
 */
@Service
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private UserChannelMapper userChannelDao;

    @Autowired
    private CarEnterpriseMapper carEnterpriseMapper;

    @Autowired
    private CategoryMapper categoryDao;

    @Autowired
    private ChannelViewMapper channelViewMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean custom(List<UserChannel> customChannels, Integer userId) {
        userChannelDao.deleteCustomed(userId);

        User u = null;
        if (userId == null) {
            throw new BaseServiceException(String.format("频道定制 - 未指定用户."));
        } else {
            u = userMapper.getById(String.valueOf(userId));
        }

        if (customChannels != null) {
            for (UserChannel tmpUserChannel : customChannels) {
                Integer channelId = tmpUserChannel.getChannelId();
                if (channelId == null) {
                    throw new BaseServiceException(String.format("频道[ ID ] 不能为空."));
                } else {
                    if (ChannelView.S_TYPE_CATEGORY.equals(tmpUserChannel.getChannelType())) {
                        Category category = categoryDao.getById(String.valueOf(channelId));
                        if (category == null) {
                            throw new BaseServiceException(String.format("新闻栏目[ ID = %s ] 不存在.", channelId));
                        }
                        if (Category.S_FIXED.equals(category.getIsfixed())) {
                            throw new BaseServiceException(String.format("新闻栏目[ NAME = %s ] 是不可定制的.", category.getName()));
                        }
                        tmpUserChannel.setChannelName(category.getName());
                        tmpUserChannel.setChannelAlias(category.getAlias());
                        tmpUserChannel.setChannelId(category.getCid());
                    } else if (ChannelView.S_TYPE_CAR_ENTERPRISE.equals(tmpUserChannel.getChannelType())) {
                        CarEnterprise carEnterprise = carEnterpriseMapper.getById(String.valueOf(channelId));
                        if (carEnterprise == null) {
                            throw new BaseServiceException(String.format("车企频道[ ID = %s ] 不存在.", channelId));
                        }
                        tmpUserChannel.setChannelName(carEnterprise.getName());
                        tmpUserChannel.setChannelId(carEnterprise.getCeid());
                    } else {
                        new BaseServiceException(String.format("非法的频道类型%s", tmpUserChannel.getChannelType()));
                    }

                    tmpUserChannel.setUid(userId);
                    tmpUserChannel.setUname(u.getName());
                    tmpUserChannel.setCdate(new Date());

                    userChannelDao.insert(tmpUserChannel);
                }
            }
        }
        return true;
    }

    @Override
    public List<UserChannel> queryDefaultChannel() {
        Map<String, Object> map = new HashMap<String, Object>();
        QueryCondition qc = new QueryCondition();
        map.put("vo", qc);
        return userChannelDao.query(map);
    }

    @Override
    public List<ChannelView> queryCustom(QueryCondition qc) {
        List<ChannelView> customedChannels = new ArrayList<ChannelView>();
        if (qc.getUserId() != null) {
            List<UserChannel> customChannels = userChannelDao.queryCustom(qc.getUserId());
            customedChannels = convert(customChannels);
        } else if (StringUtils.isEmpty(qc.getCustomedChannel()) == false) {
            // 查询固定频道
            Map<String, Object> nmap = new HashMap<String, Object>();
            QueryCondition vo = new QueryCondition();
            vo.setIsfixed(Category.S_FIXED);
            nmap.put("vo", vo);
            customedChannels = channelViewMapper.query(nmap);
            // 查询已定制频道
            List<ChannelView> localCustomedChannels = JSON.parseArray(qc.getCustomedChannel(), ChannelView.class);
            Map<String, Object> map = new HashMap<String, Object>();
            for (ChannelView uc : localCustomedChannels) {
                map.put("vo", uc);
                ChannelView view = channelViewMapper.queryOne(map);
                if (view != null) {
                    view.setSeqno(uc.getSeqno());
                    customedChannels.add(view);
                }
            }

        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            QueryCondition vo = new QueryCondition();
            vo.setIsfixed(Category.S_FIXED);
            map.put("vo", vo);
            customedChannels = channelViewMapper.query(map);
        }

        return customedChannels;
    }

    @Override
    public List<ChannelView> queryUncustomCategory(QueryCondition qc) {
        List<ChannelView> uncustomChannelCategory = new ArrayList<ChannelView>();
        if (qc.getUserId() != null) {
            List<UserChannel> customChannels = userChannelDao.queryUncustomCategory(qc.getUserId());
            uncustomChannelCategory = convert(customChannels);
        } else if (StringUtils.isEmpty(qc.getCustomedChannel()) == false) {
            List<ChannelView> localCustomedChannels = JSON.parseArray(qc.getCustomedChannel(), ChannelView.class);
            List<Integer> localCustomedChannelIds = new ArrayList<Integer>();
            for (ChannelView view : localCustomedChannels) {
                if (ChannelView.S_TYPE_CATEGORY.equals(view.getChannelType())) {
                    localCustomedChannelIds.add(view.getChannelId());
                }
            }

            QueryCondition nqc = new QueryCondition();
            nqc.setChannelType(ChannelView.S_TYPE_CATEGORY);
            nqc.setChannelIdList(localCustomedChannelIds);
            nqc.setIsfixed(Category.S_NOT_FIXED);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("vo", nqc);

            uncustomChannelCategory = channelViewMapper.query(map);
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            QueryCondition vo = new QueryCondition();
            vo.setChannelType(ChannelView.S_TYPE_CATEGORY);
            vo.setIsfixed(Category.S_NOT_FIXED);
            map.put("vo", vo);
            uncustomChannelCategory = channelViewMapper.query(map);
        }
        return uncustomChannelCategory;
    }

    @Override
    public List<ChannelView> queryUncustomCarEnterprise(QueryCondition qc) {
        List<ChannelView> uncustomChannelCarEnterprise = new ArrayList<ChannelView>();
        if (qc.getUserId() != null) {
            List<UserChannel> customChannels = userChannelDao.queryUncustomCarEnterprise(qc.getUserId());
            uncustomChannelCarEnterprise = convert(customChannels);
        } else if (StringUtils.isEmpty(qc.getCustomedChannel()) == false) {
            List<ChannelView> localCustomedChannels = JSON.parseArray(qc.getCustomedChannel(), ChannelView.class);
            List<Integer> localCustomedChannelIds = new ArrayList<Integer>();
            for (ChannelView view : localCustomedChannels) {
                if (ChannelView.S_TYPE_CAR_ENTERPRISE.equals(view.getChannelType())) {
                    localCustomedChannelIds.add(view.getChannelId());
                }
            }

            QueryCondition nqc = new QueryCondition();
            nqc.setChannelType(ChannelView.S_TYPE_CAR_ENTERPRISE);
            nqc.setIsfixed(Category.S_NOT_FIXED);
            nqc.setChannelIdList(localCustomedChannelIds);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("vo", nqc);

            uncustomChannelCarEnterprise = channelViewMapper.query(map);
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            QueryCondition vo = new QueryCondition();
            vo.setChannelType(ChannelView.S_TYPE_CAR_ENTERPRISE);
            vo.setIsfixed(Category.S_NOT_FIXED);
            map.put("vo", vo);
            uncustomChannelCarEnterprise = channelViewMapper.query(map);
        }
        return uncustomChannelCarEnterprise;
    }

    private List<ChannelView> convert(List<UserChannel> userChannelList) {
        List<ChannelView> channelViewList = new ArrayList<ChannelView>();
        for (UserChannel userChannel : userChannelList) {
            channelViewList.add(convert(userChannel));
        }
        return channelViewList;
    }

    private ChannelView convert(UserChannel userChannel) {
        ChannelView view = new ChannelView();
        view.setIsfixed(userChannel.getIsfixed());
        view.setChannelId(userChannel.getChannelId());
        view.setChannelType(userChannel.getChannelType());
        view.setChannelAlias(userChannel.getChannelAlias());
        view.setChannelName(userChannel.getChannelName());
        view.setSeqno(userChannel.getSeqno());
        return view;
    }
}
