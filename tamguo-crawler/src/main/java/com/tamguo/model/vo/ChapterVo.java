package com.tamguo.model.vo;

import com.xuxueli.crawler.annotation.PageFieldSelect;
import com.xuxueli.crawler.annotation.PageSelect;

import java.util.List;

@PageSelect(cssQuery = ".out-chapter")
public class ChapterVo {

    @PageFieldSelect(cssQuery = "h3")
    private String name;

    @PageFieldSelect(cssQuery = ".out-list li")
    private List<String> sonChapters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSonChapters() {
        return sonChapters;
    }

    public void setSonChapters(List<String> sonChapters) {
        this.sonChapters = sonChapters;
    }
}
