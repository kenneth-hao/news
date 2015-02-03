package org.kenneth.ctx.news.vo;

import org.kenneth.ctx.news.utils.security.MD5Utils;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/1/5.
 */
public class QueryCondition {
    // 用户登录ID
    private String loginId;
    // 用户登录密码
    private String pwd;
    // 用户ID
    private Integer userId;

    private String mobile;

    private String email;

    private String title;
    // 新闻ID
    private Integer newsId;

    private List<Integer> channelIdList;

    private List<Integer> newsIdList;
    // 频道ID, 逗号分隔
    private String strNewsIds;

    // 已定制频道字符串
    private String customedChannel;

    // 频道ID
    private Integer channelId;
    // 频道类型
    private Integer channelType;
    // 是否为推荐的新闻
    private Integer isRecommand;

    // 是否轮播显示
    private Integer isCarousel;

    // 轮播新闻个数
    private Integer carouselSize;

    // 新闻审核状态
    private Integer status;

    // 广告数
    private Integer adNum;

    private Integer isDefault;

    private Date dateStart;

    private Date dateEnd;

    private Integer isfixed;

    private Integer valid;
    
    //发布人
    private String author;
    public List<Integer> getNewsIdList() {
        return newsIdList;
    }

    public void setNewsIdList(List<Integer> newsIdList) {
        this.newsIdList = newsIdList;
    }

    public String getStrNewsIds() {
        return strNewsIds;
    }

    public void setStrNewsIds(String strNewsIds) {
        this.strNewsIds = strNewsIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Integer getIsfixed() {
        return isfixed;
    }

    public void setIsfixed(Integer isfixed) {
        this.isfixed = isfixed;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public List<Integer> getChannelIdList() {
        return channelIdList;
    }

    public void setChannelIdList(List<Integer> channelIdList) {
        this.channelIdList = channelIdList;
    }

    public String getCustomedChannel() {
        return customedChannel;
    }

    public void setCustomedChannel(String customedChannel) {
        this.customedChannel = customedChannel;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Integer getAdNum() {
        return adNum;
    }

    public void setAdNum(Integer adNum) {
        this.adNum = adNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMd5Pwd() {
        return MD5Utils.getMd5(pwd);
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getCarouselSize() {
        return carouselSize;
    }

    public void setCarouselSize(Integer carouselSize) {
        this.carouselSize = carouselSize;
    }

    public Integer getIsRecommand() {
        return isRecommand;
    }

    public void setIsRecommand(Integer isRecommand) {
        this.isRecommand = isRecommand;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getChannelType() {
        return channelType;
    }

    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    public Integer getIsCarousel() {
        return isCarousel;
    }

    public void setIsCarousel(Integer isCarousel) {
        this.isCarousel = isCarousel;
    }

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}
