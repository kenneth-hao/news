package org.kenneth.ctx.news.entity;


import com.alibaba.fastjson.annotation.JSONField;
import org.kenneth.ctx.news.utils.security.MD5Utils;

import java.util.Date;

/**
 * Created by haoyuewen on 8/28/14.
 */
public class User extends BaseEntity {

    /**
     * 正式用户
     */
    public static Integer S_USERTYPE_ORRICIAL = 2;
    /**
     * 游客
     */
    public static Integer S_USERTYPE_GUEST = 1;

    /**
     * 逻辑删除
     */
    public static final Integer S_VALID_LOGIC_DEL = 0;

    /**
     * 正常用户
     */
    public static final Integer S_VALID_NORMAL = 1;

    private Integer uid;

    private String name;

    private String mobile;

    private String email;

    private String pwd;

    private String nickname;

    private String realname;

    private Integer gender;

    private String portraitImgPath;

    private Integer usertype;

    private Date cdate;

    private Date udate;

    private Integer valid;

    /**
     * 加密后的密码
     *
     * @return
     */
    @JSONField(serialize = false)
    public String getMd5Pwd() {
        return MD5Utils.getMd5(pwd);
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPortraitImgPath() {
        return portraitImgPath;
    }

    public void setPortraitImgPath(String portraitImgPath) {
        this.portraitImgPath = portraitImgPath;
    }

    public String getPwd() {
        return pwd;
    }

    public Integer getUsertype() {
        return usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
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
