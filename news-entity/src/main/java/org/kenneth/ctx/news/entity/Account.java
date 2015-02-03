package org.kenneth.ctx.news.entity;

import org.kenneth.ctx.news.utils.security.MD5Utils;

import java.util.Date;

/**
 * Created by Administrator on 2015/1/15.
 */
public class Account extends BaseEntity {

    public static final String S_STATUS_BLOCK = "0";

    public static final String S_STATUS_NORMAL = "1";

    protected Integer id;

    protected String userName;// 账号名

    protected String realName;

    protected String roleName; // 角色名

    protected String password;// 密码

    protected String description;// 说明

    protected String status;// 账号状态  0 表示停用  1表示启用

    protected Date cdate;

    protected Date udate;

    public String getMd5Pwd() {
        return MD5Utils.getMd5(password);
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
