package com.tamguo.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.tamguo.dao.BookMapper;
import com.tamguo.dao.CrawlerBookMapper;
import com.tamguo.model.BookEntity;
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

import java.util.List;

@Service
public class CrawlerBookService implements ICrawlerBookService {


    @Autowired
    CrawlerBookMapper crawlerBookMapper;
    @Autowired
    BookMapper bookMapper;

    @SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(getClass());


    @SuppressWarnings("unchecked")
	@Override
    public void crawlerBook() {
        List<BookEntity> bookEntities = bookMapper.selectList(Condition.EMPTY);
        for (BookEntity bookEntity : bookEntities) {
            String url = bookEntity.getReserveField1();
            String bookId = bookEntity.getId();
            String regexs = url.replaceAll("\\d+", "\\\\d+").replaceAll("\\.","\\\\.");

            XxlCrawler crawler = new XxlCrawler.Builder()
                    .setUrls("http://www.ruiwen.com/jiaocai/")
                    .setWhiteUrlRegexs(regexs)//
                    .setAllowSpread(true)
                    .setFailRetryCount(5)
                    .setThreadCount(20)
                    .setPageParser(new PageParser<CrawlerBookVo>() {
                        @Override
                        public void parse(Document html, Element pageVoElement, CrawlerBookVo crawlerBookVo) {
                        //  String pageUrl = html.baseUri();
                            // 解析封装 PageVo 对象
                            String img = crawlerBookVo.getBookImage();
                            if (StringUtils.isNoneBlank(img)) {
                                CrawlerBookEntity crawlerBookEntity = new CrawlerBookEntity();
                                crawlerBookEntity.setBookUid(bookId);
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

//    public static void main(String[] args) {
//        String url = "http://www.ruiwen.com/jiaocai/yuwen/renjiaoban/yinianjishangce/shangce1.html";
//        System.out.println(url.replaceAll("\\d+", "\\\\d+").replaceAll("\\.","\\\\."));
//    }

}
