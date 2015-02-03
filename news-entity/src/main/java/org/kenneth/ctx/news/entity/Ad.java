package org.kenneth.ctx.news.entity;

import java.util.Date;

/**
 * Created by Administrator on 2015/1/26.
 */
public class Ad extends BaseEntity {

    private Integer id;

    private String title;

    private String imgPath;

    private Date cdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }
}
