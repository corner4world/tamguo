package com.tamguo.model.vo;

import com.xuxueli.crawler.annotation.PageFieldSelect;
import com.xuxueli.crawler.annotation.PageSelect;
import com.xuxueli.crawler.conf.XxlCrawlerConf;

@PageSelect(cssQuery = "body")
public class CrawlerBookVo {

    @PageFieldSelect(cssQuery = ".con .pic img", selectType = XxlCrawlerConf.SelectType.ATTR, selectVal = "abs:src")
    private String bookImage;

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }
}
