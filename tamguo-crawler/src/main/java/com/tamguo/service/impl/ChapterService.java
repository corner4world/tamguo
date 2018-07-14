package com.tamguo.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.dao.ChapterMapper;
import com.tamguo.dao.CourseMapper;
import com.tamguo.dao.CrawlerChapterMapper;
import com.tamguo.dao.CrawlerQuestionMapper;
import com.tamguo.dao.SubjectMapper;
import com.tamguo.model.ChapterEntity;
import com.tamguo.model.CourseEntity;
import com.tamguo.model.CrawlerChapterEntity;
import com.tamguo.model.vo.SubjectVo;
import com.tamguo.service.IChapterService;
import com.xuxueli.crawler.XxlCrawler;
import com.xuxueli.crawler.parser.PageParser;
import com.xuxueli.crawler.rundata.RunData;

@Service
public class ChapterService implements IChapterService{
	
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
	
	private RunData runData;
	
	@Autowired
	private CrawlerChapterMapper crawlerChapterMapper;
	
	@Override
	public void modifyQuestionNum() {
		int page = 1;
		int pageSize = 1000;
		while(true) {
			Page<ChapterEntity> chapterPage = new Page<ChapterEntity>(page , pageSize);
			List<ChapterEntity> chapterList = chapterMapper.queryList(chapterPage);
			for(int i=0 ;i<chapterList.size() ; i++) {
				// 处理数据
				ChapterEntity chapter = chapterList.get(i);
				Integer count = chapterMapper.queryCount(chapter.getUid());
				chapter.setQuestionNum(count);
				chapterMapper.updateById(chapter);
			}
			page++;
			if(chapterList.size() < 1000) {
				break;
			}
		}
	}

	@Override
	public void crawlerChapter() {
		XxlCrawler crawler = new XxlCrawler.Builder()
	            .setUrls("https://tiku.baidu.com/")
	            .setAllowSpread(false)
	            .setFailRetryCount(5)
	            .setThreadCount(20)
	            .setPageParser(new PageParser<SubjectVo>() {
	            	
	                @Override
	                public void parse(Document html, Element pageVoElement, SubjectVo subjectVo) {
	                    // 解析封装 PageVo 对象
	                    String pageUrl = html.baseUri();
	                    if(pageUrl.equals("https://tiku.baidu.com/")) {
	                    	logger.info("开始解析考试分类：{}" , pageUrl);
	                	 	// 加入科目爬取数据
	                    	for(String url : subjectVo.getCourseUrls()) {
	                    		runData.addUrl(url);
	                    	}
	                    }
	                    if(pageUrl.contains("https://tiku.baidu.com/tikupc/homepage/")) {
	                    	logger.info("开始解析科目分类：{}" , pageUrl);
	                    	// 加入科目爬取数据
	                    	for(String url : subjectVo.getChapterUrlsTemp()) {
	                    		runData.addUrl(url);
	                    	}
	                    }
	                    
	                    if(pageUrl.contains("https://tiku.baidu.com/tikupc/chapterlist/")) {
	                    	logger.info("开始解析章节：{}" , pageUrl);
	                    	ChapterEntity chapterCondition = new ChapterEntity();
	                    	chapterCondition.setName(subjectVo.getChapterCurrName());
	                    	ChapterEntity chapterEntity = chapterMapper.selectOne(chapterCondition);
	                    	CourseEntity course = courseMapper.selectById(chapterEntity.getCourseId());
	                    	
	                    	CrawlerChapterEntity crawlerChapter = new CrawlerChapterEntity();
	                    	crawlerChapter.setChapterUid(chapterEntity.getUid());
	                    	crawlerChapter.setChapterUrl(pageUrl);
	                    	crawlerChapter.setCourseUid(chapterEntity.getCourseId());
	                    	crawlerChapter.setSubjectUid(course.getSubjectId());
	                    	crawlerChapterMapper.insert(crawlerChapter);

	                		logger.info("url:{}" ,pageUrl );
	                    	logger.info("subjectVo:{}" ,JSONObject.toJSON(subjectVo) );
	                    	if(subjectVo.getChapterUrls() != null) {
	                    		for(String url : subjectVo.getChapterUrls()) {
	                        		if(url.equals(pageVoElement.getElementsByClass("main-inner").get(0).getElementsByClass("selected").get(0).getElementsByTag("a").attr("abs:href"))) {
	                        			continue;
	                        		}
	                        		if(!urls.contains(url)) {
	                        			crawlerChapter = new CrawlerChapterEntity();
	        	                    	crawlerChapter.setChapterUid(chapterEntity.getUid());
	        	                    	crawlerChapter.setChapterUrl(url);
	        	                    	crawlerChapter.setCourseUid(chapterEntity.getCourseId());
	        	                    	crawlerChapter.setSubjectUid(course.getSubjectId());
	        	                    	crawlerChapterMapper.insert(crawlerChapter);
	                        		}
	                        	}
	                    	}
	                    }
	                }
	        }).build();
			
			runData = crawler.getRunData();
			// 获取科目
			crawler.start(true);
	}

}
