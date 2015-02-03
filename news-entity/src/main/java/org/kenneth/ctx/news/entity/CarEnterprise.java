package org.kenneth.ctx.news.entity;

import java.util.Date;

/**
 * Created by Administrator on 2015/1/5.
 */
public class CarEnterprise extends BaseEntity {

    public static final Integer S_VALID_NORMAL = 1;

    public static final Integer S_VALID_BLOCK = 0;

    private Integer isDefault;

    private Integer ceid;

    private String name;

    private String brandImgPath;

    private String appurl;

    private Integer seqno;

    private Integer valid;

    private Date cdate;

    private Date udate;

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public String getBrandImgPath() {
        return brandImgPath;
    }

    public void setBrandImgPath(String brandImgPath) {
        this.brandImgPath = brandImgPath;
    }

    public Integer getCeid() {
        return ceid;
    }

    public void setCeid(Integer ceid) {
        this.ceid = ceid;
    }

    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppurl() {
        return appurl;
    }

    public void setAppurl(String appurl) {
        this.appurl = appurl;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
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
}
