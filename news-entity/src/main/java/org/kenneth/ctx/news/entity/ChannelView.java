package org.kenneth.ctx.news.entity;

/**
 * Created by Administrator on 2015/1/16.
 */
public class ChannelView extends BaseEntity {

    /**
     * 正常的新闻栏目
     */
    public static final Integer S_TYPE_CATEGORY = 1;

    /**
     * 车企栏目
     */
    public static final Integer S_TYPE_CAR_ENTERPRISE = 2;

    public static final Integer S_DEFAULT = 1;

    public static final Integer S_DEFAULT_NO = 0;

    private Integer channelType;

    private Integer channelId;

    private String channelAlias;

    private String channelName;

    private Integer isDefault;

    private Integer seqno;

    private Integer isfixed;

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
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

    public String getChannelAlias() {
        return channelAlias;
    }

    public void setChannelAlias(String channelAlias) {
        this.channelAlias = channelAlias;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }

    public Integer getIsfixed() {
        return isfixed;
    }

    public void setIsfixed(Integer isfixed) {
        this.isfixed = isfixed;
    }
}
