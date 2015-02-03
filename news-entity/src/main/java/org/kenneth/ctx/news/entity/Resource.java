package org.kenneth.ctx.news.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/1/15.
 */
public class Resource {

    public static final Integer TOP_PARENT_ID = -1;

    public static final Integer TOP_LEVEL = 1;

    /**
     * 菜单
     */
    public static final Integer S_TYPE_MENU = 1;
    /**
     * 按钮
     */
    public static final Integer S_TYPE_BUTTON = 2;

    /**
     * 数据
     */
    public static final Integer S_TYPE_DATA = 3;

    private Integer id;
    private String name;
    private Integer parentId; //父类Id
    private String parentName;
    private String alias;//这个权限KEY是唯一的，新增时要注意，
    private String url;//URL地址．例如：/videoType/query　　不需要项目名和http://xxx:8080
    private Integer level;//级别 菜单的顺序
    private Integer type;//类型，目录　菜单  按扭．．在spring security3安全权限中，涉及精确到按扭控制
    private Date cdate;
    private Integer seqno;
    private List<Resource> children = new ArrayList<Resource>();


    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<Resource> getChildren() {
        return children;
    }

    public void setChildren(List<Resource> children) {
        this.children = children;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }
}
