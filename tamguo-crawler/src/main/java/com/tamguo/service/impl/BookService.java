package com.tamguo.service.impl;

import com.tamguo.dao.ChapterMapper;
import com.tamguo.model.ChapterEntity;
import com.tamguo.model.vo.ChapterVo;
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
import java.util.UUID;

@Service
public class BookService implements IBookService {


    @Autowired
    ChapterMapper chapterMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void crawlerBook() {
        XxlCrawler crawler = new XxlCrawler.Builder()
                .setUrls("https://tiku.baidu.com/tikupc/chapterlist/1bfd700abb68a98271fefa04-27-jiaocai-11")
                .setAllowSpread(false)
                .setFailRetryCount(5)
                .setThreadCount(20)
                .setPageParser(new PageParser<ChapterVo>() {
                    @Override
                    public void parse(Document html, Element pageVoElement, ChapterVo chapterVo) {
                        // 解析封装 PageVo 对象
                        String parentName = chapterVo.getName();
                        ChapterEntity chapterEntity = new ChapterEntity();
                        String uid = UUID.randomUUID().toString().replace("-", "");
                        chapterEntity.setUid(uid);
                        chapterEntity.setName(parentName);
                        chapterEntity.setCourseId("0");
                        chapterEntity.setCourseId("0");
                        chapterEntity.setParentId("-1");
                        chapterEntity.setQuestionNum(0);
                        chapterEntity.setPointNum(0);
                        chapterMapper.insert(chapterEntity);

                        List<String> sonChapters = chapterVo.getSonChapters();
                        sonChapters.forEach(s -> {
                            ChapterEntity sonChapterEntity = new ChapterEntity();
                            sonChapterEntity.setName(s);
                            sonChapterEntity.setCourseId("0");
                            sonChapterEntity.setCourseId("0");
                            sonChapterEntity.setParentId(uid);
                            sonChapterEntity.setQuestionNum(0);
                            sonChapterEntity.setPointNum(0);
                            chapterMapper.insert(sonChapterEntity);
                        });

                    }


//                    }
                }).build();

        // 获取科目
        crawler.start(true);
    }

}
