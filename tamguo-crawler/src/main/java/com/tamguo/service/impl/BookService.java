package com.tamguo.service.impl;

import com.tamguo.dao.ChapterMapper;
import com.tamguo.dao.CourseMapper;
import com.tamguo.dao.CrawlerQuestionMapper;
import com.tamguo.dao.SubjectMapper;
import com.tamguo.model.vo.BookVo;
import com.tamguo.service.IBookService;
import com.xuxueli.crawler.XxlCrawler;
import com.xuxueli.crawler.parser.PageParser;
import com.xuxueli.crawler.rundata.RunData;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class BookService implements IBookService {


    @Autowired
    SubjectMapper subjectMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    ChapterMapper chapterMapper;
    @Autowired
    CrawlerQuestionMapper crawlerQuestionMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Set<String> urls = new HashSet<>();

    private Set<String> questionUrls = new HashSet<String>();

    private Map<String, Object> chapterQuestionListMap = new HashMap<>();

    private RunData runData;

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
                        if (pageUrl.equals("https://tiku.baidu.com/")) {
                            logger.info("开始解析考试分类：{}", pageUrl);
//                            for (int i = 0; i < subjectVo.getName().size(); i++) {
//                                String name = subjectVo.getName().get(i);
//
//                                SubjectEntity subject = subjectMapper.findByName(name);
//                                if (subject != null) {
//                                    continue;
//                                }
//                                SubjectEntity entity = new SubjectEntity();
//                                if (name.equals("高考")) {
//                                    name = "高考";
//                                    entity.setName(name);
//                                    subjectMapper.insert(entity);
//                                    // 加入科目爬取数据
//                                    for (String url : subjectVo.getCourseUrls()) {
//                                        runData.addUrl(url);
//                                    }
//
//                                }
//
//
//                            }


                        }


                    }
                }).build();

        runData = crawler.getRunData();
        // 获取科目
        crawler.start(true);
    }

}
