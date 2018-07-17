package com.tamguo.model.vo;

import com.xuxueli.crawler.annotation.PageFieldSelect;
import com.xuxueli.crawler.annotation.PageSelect;
import com.xuxueli.crawler.conf.XxlCrawlerConf;

import java.util.List;

@PageSelect(cssQuery = "body")
public class BookVo {

	@PageFieldSelect(cssQuery = ".ih3")
	private List<String> name;

	public List<String> getName() {
		return name;
	}

	public void setName(List<String> name) {
		this.name = name;
	}
}
