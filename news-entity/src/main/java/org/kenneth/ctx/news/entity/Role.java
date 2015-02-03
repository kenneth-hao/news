package org.kenneth.ctx.news.entity;

import java.util.Date;

/**
 * Created by Administrator on 2015/1/15.
 */
public class Role extends BaseEntity {

    public static final String S_STATUS_NORMAL = "1";
    public static final String S_STATUS_BLOCK = "0";

    private Integer id;
    private String status; //是否禁用角色　1　表示正常　0　表示停用
    private String name;
    private String alias; //唯一,新增时,需要判断
    private Date cdate;
    private Date udate;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
