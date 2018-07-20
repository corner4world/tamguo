package com.tamguo.model.vo;

import com.xuxueli.crawler.annotation.PageFieldSelect;
import com.xuxueli.crawler.annotation.PageSelect;
import com.xuxueli.crawler.conf.XxlCrawlerConf;

@PageSelect(cssQuery = ".pic_right li")
public class BookVo {

    @PageFieldSelect(cssQuery = ".text")
    private String name;

    @PageFieldSelect(cssQuery = "a", selectType = XxlCrawlerConf.SelectType.ATTR, selectVal = "abs:href")
    private String bookUrl;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }
}
