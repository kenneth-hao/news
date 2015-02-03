package org.kenneth.ctx.news.entity;

import java.util.Date;

/**
 * Created by Administrator on 2015/1/5.
 */
public class News extends BaseEntity {

    /**
     * 置顶
     */
    public static final Integer S_TOP = 1;

    /**
     * 不置顶
     */
    public static final Integer S_NOT_TOP = 0;

    /**
     * 审核状态 - 已审核
     */
    public static final Integer S_AUDITED = 1;

    /**
     * 审核状态 - 已审核
     */
    public static final Integer S_NOT_AUDIT = 0;

    /**
     * 有效记录
     */
    public static final Integer S_VALID = 1;

    /**
     * 逻辑删除
     */
    public static final Integer S_VALID_LOGIC_DEL = 0;

    /**
     * 推荐新闻
     */
    public static final Integer S_RECOMMAND = 1;

    /**
     * 不是推荐新闻
     */
    public static final Integer S_NOT_RECOMMAND = 0;

    /**
     * 轮播显示
     */
    public static final Integer S_CAROUSEL = 1;

    /**
     * 原创
     */
    public static final Integer S_FILE_FORMAT_ORIGINAL = 1;

    /**
     * 转载
     */
    public static final Integer S_FILE_FORMAT_REPRINT = 2;

    /**
     * 非轮播显示
     */
    public static final Integer S_NOT_CAROUSEL = 0;

    private Integer nid;

    private Integer channelType;

    private Integer channelId;

    private String channelName;

    private String title;

    private String source;

    private String author;

    private String keyword;

    private Date publishTime;

    private String listImgPath;

    private String carouselImgPath;

    private String detailMultiImgPath;

    private String summary;

    private String content;

    private Integer fileFormat;

    private Integer isCarousel;

    private Integer isTop;

    private String linkUrl;

    private Integer status;

    private Date cdate;

    private Date udate;

    private Integer isRecommand;

    private Integer valid;

    private Integer commentCount;

    private Integer isfavorate;

    public Integer getIsfavorate() {
        return isfavorate;
    }

    public void setIsfavorate(Integer isfavorate) {
        this.isfavorate = isfavorate;
    }

    public Integer getIsRecommand() {
        return isRecommand;
    }

    public void setIsRecommand(Integer isRecommand) {
        this.isRecommand = isRecommand;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getListImgPath() {
        return listImgPath;
    }

    public void setListImgPath(String listImgPath) {
        this.listImgPath = listImgPath;
    }

    public String getCarouselImgPath() {
        return carouselImgPath;
    }

    public void setCarouselImgPath(String carouselImgPath) {
        this.carouselImgPath = carouselImgPath;
    }

    public String getDetailMultiImgPath() {
        return detailMultiImgPath;
    }

    public void setDetailMultiImgPath(String detailMultiImgPath) {
        this.detailMultiImgPath = detailMultiImgPath;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(Integer fileFormat) {
        this.fileFormat = fileFormat;
    }

    public Integer getChannelType() {
        return channelType;
    }

    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Integer getIsCarousel() {
        return isCarousel;
    }

    public void setIsCarousel(Integer isCarousel) {
        this.isCarousel = isCarousel;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public Date getUdate() {
        return udate;
    }

    public void setUdate(Date udate) {
        this.udate = udate;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }
}
