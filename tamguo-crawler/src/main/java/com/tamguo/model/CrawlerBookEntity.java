package com.tamguo.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.tamguo.config.dao.SuperEntity;

import java.io.Serializable;

/**
 * The persistent class for the crawler_book database table.
 */
@TableName(value = "crawler_book")
public class CrawlerBookEntity extends SuperEntity<CrawlerBookEntity> implements Serializable {
    private static final long serialVersionUID = 1L;

    private String bookUrl;

    private String bookUid;

    private Integer orders;

    public CrawlerBookEntity() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public String getBookUid() {
        return bookUid;
    }

    public void setBookUid(String bookUid) {
        this.bookUid = bookUid;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }
}