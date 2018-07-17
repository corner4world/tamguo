package com.tamguo.service.impl;

import com.tamguo.config.redis.CacheService;
import com.tamguo.dao.CrawlerBookMapper;
import com.tamguo.model.CrawlerBookEntity;
import com.tamguo.model.vo.CrawlerBookVo;
import com.tamguo.service.ICrawlerBookService;
import com.xuxueli.crawler.XxlCrawler;
import com.xuxueli.crawler.parser.PageParser;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrawlerBookService implements ICrawlerBookService {


    @Autowired
    CrawlerBookMapper crawlerBookMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String FILES_NO_FORMAT = "000000";
    private static final String FILES_PREFIX = "FPIMAGE";
    private static final String DOMAIN = "http://www.tamguo.com";
    @Autowired
    CacheService cacheService;


    //一年级语文上册
    @Override
    public void crawlerBook() {
        XxlCrawler crawler = new XxlCrawler.Builder()
                .setUrls("http://www.ruiwen.com/jiaocai/")
                .setWhiteUrlRegexs("http://www\\.ruiwen\\.com/jiaocai/yuwen/bubianban/yinianjishangce/shangce\\d+\\.html")
                .setAllowSpread(true)
                .setFailRetryCount(5)
                .setThreadCount(20)
                .setPageParser(new PageParser<CrawlerBookVo>() {
                    @Override
                    public void parse(Document html, Element pageVoElement, CrawlerBookVo crawlerBookVo) {
                        // 解析封装 PageVo 对象
                        String img = crawlerBookVo.getBookImage();
                        if (StringUtils.isNoneBlank(img)) {
                            CrawlerBookEntity crawlerBookEntity = new CrawlerBookEntity();
                            crawlerBookEntity.setBookUid("1019238600753074178");
                            crawlerBookEntity.setBookUrl(crawlerBookVo.getBookImage());
                            crawlerBookEntity.setOrders(Integer.parseInt(img.substring(img.lastIndexOf("/") + 1, img.lastIndexOf("."))));
                            crawlerBookMapper.insert(crawlerBookEntity);
                        }
                    }
                }).build();

        // 获取科目
        crawler.start(true);
    }

}
