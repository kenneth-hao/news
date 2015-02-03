package org.kenneth.ctx.news.entity;

/**
 * Created by Administrator on 2015/1/5.
 */
public class Category extends BaseEntity {

    /**
     * 固定栏目
     */
    public static final Integer S_FIXED = 1;

    /**
     * 可定制栏目
     */
    public static final Integer S_NOT_FIXED = 0;

    /**
     * 特殊栏目 - 推荐
     */
    public static final Integer S_RECOMMAND = 1;

    /**
     * 特殊栏目 - 维权
     */
    public static final Integer S_RIGHTS = 2;

    private Integer isDefault;

    private Integer cid;

    private String alias;

    private String name;

    private Integer isfixed;

    private Integer seqno;

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsfixed() {
        return isfixed;
    }

    public void setIsfixed(Integer isfixed) {
        this.isfixed = isfixed;
    }

    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }
}
