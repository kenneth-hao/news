package org.kenneth.ctx.news.entity;

import java.util.Date;

/**
 * Created by Administrator on 2015/1/8.
 */
public class RightsProtection extends BaseEntity {

    /**
     * 有效记录
     */
    public static final Integer S_VALID = 1;

    private Integer rpid;

    private Integer nid;

    private String title;

    private String author;

    private String mobile;

    private String carinfo;

    private String carno;

    private String content;

    private String listImgPath;

    private String detailMultiImgPath;

    private Integer status;

    private Date cdate;

    private Date udate;

    private Integer valid;

    public Integer getRpid() {
        return rpid;
    }

    public void setRpid(Integer rpid) {
        this.rpid = rpid;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCarinfo() {
        return carinfo;
    }

    public void setCarinfo(String carinfo) {
        this.carinfo = carinfo;
    }

    public String getCarno() {
        return carno;
    }

    public void setCarno(String carno) {
        this.carno = carno;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getListImgPath() {
        return listImgPath;
    }

    public void setListImgPath(String listImgPath) {
        this.listImgPath = listImgPath;
    }

    public String getDetailMultiImgPath() {
        return detailMultiImgPath;
    }

    public void setDetailMultiImgPath(String detailMultiImgPath) {
        this.detailMultiImgPath = detailMultiImgPath;
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
