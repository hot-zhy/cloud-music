package com.zhy.model;

import java.util.List;

/**
 * 分页相关的元数据
 */
public class Meta<T> {
    /**
     * 有多少条
     */
    private Integer total;

    /**
     * 有多少页
     */
    private Integer pages;

    /**
     * 当前每页显示多少条
     */
    private Integer size;

    /**
     * 当前页
     */
    private Integer page;

    /**
     * 下一页
     */
    private Integer next;
    private List<T> data;

    /**
     * 获取下一页
     * @param data
     * @return
     */
    public static int nextPage(Meta data) {
        if(data==null||data.next==null){
            return 1;
        }else{
            return data.next;
        }
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getNext() {
        return next;
    }

    public void setNext(Integer next) {
        this.next = next;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
