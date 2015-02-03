package org.kenneth.ctx.news.utils.mybatis;

/**
 * 分页参数类
 */
public class PageParameter {

    public static final Integer DEFAULT_PAGE_SIZE = 10;

    private Integer pageSize;
    private Integer currentPage;
    private Integer prePage;
    private Integer nextPage;
    private Integer totalPage;
    private Integer totalCount;

    public PageParameter() {
        this.currentPage = 1;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    public PageParameter(int pageSize) {
        this.currentPage = 1;
        this.pageSize = pageSize;
    }

    /**
     * @param currentPage
     * @param pageSize
     */
    public PageParameter(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    /**
     * 适配 easyUI datagrid
     *
     * @param rows
     */
    public void setRows(Integer rows) {
        this.pageSize = rows;
    }

    public void setPage(Integer page) {
        this.currentPage = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPrePage() {
        return prePage;
    }

    public void setPrePage(Integer prePage) {
        this.prePage = prePage;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
