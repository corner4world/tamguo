package com.tamguo.service.impl;

import com.tamguo.dao.BookMapper;
import com.tamguo.model.BookEntity;
import com.tamguo.model.vo.BookVo;
import com.tamguo.service.IBookService;
import com.xuxueli.crawler.XxlCrawler;
import com.xuxueli.crawler.parser.PageParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements IBookService {


    @Autowired
    BookMapper bookMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void crawlerBook() {
        XxlCrawler crawler = new XxlCrawler.Builder()
                .setUrls("http://www.dzkbw.com")
                .setAllowSpread(false)
                .setFailRetryCount(5)
                .setThreadCount(20)
                .setPageParser(new PageParser<BookVo>() {
                    @Override
                    public void parse(Document html, Element pageVoElement, BookVo bookVo) {
                        // 解析封装 PageVo 对象
                        String pageUrl = html.baseUri();
                        if (pageUrl.equals("http://www.dzkbw.com")) {
                            logger.info("开始解析书本信息：{}", pageUrl);
                            List<String> books = bookVo.getName();
                            books.forEach(item -> {
                                BookEntity bookEntity = new BookEntity();
                                bookEntity.setName(item);
                                bookEntity.setQuestionNum(0);
                                bookEntity.setPointNum(0);
                                bookMapper.insert(bookEntity);
                            });
                        }


                    }
                }).build();

//        runData = crawler.getRunData();
        // 获取科目
        crawler.start(true);
    }

}
