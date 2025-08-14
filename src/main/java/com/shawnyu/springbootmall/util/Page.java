package com.shawnyu.springbootmall.util;

import java.util.List;

public class Page<T> {

    private Integer page;
    private Integer limit;
    private Integer total;
    private List<T> Books;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getBooks() {
        return Books;
    }

    public void setBooks(List<T> books) {
        Books = books;
    }
}
