package com.tamguo.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.tamguo.dao.ChapterMapper;
import com.tamguo.dao.CourseMapper;
import com.tamguo.dao.CrawlerQuestionMapper;
import com.tamguo.dao.SubjectMapper;
import com.tamguo.model.ChapterEntity;
import com.tamguo.model.CrawlerQuestionEntity;
import com.tamguo.model.vo.SubjectVo;
import com.tamguo.service.ISubjectService;
import com.xuxueli.crawler.XxlCrawler;
import com.xuxueli.crawler.parser.PageParser;
import com.xuxueli.crawler.rundata.RunData;

@Service
public class SubjectService implements ISubjectService{
	
	private final static String COURSE_ID = "zhengzhi";
	private final static String BOOK_ID = "1025976567395184649";
	private final static String SUBJECT_ID = "gaokao";
	@Autowired
	SubjectMapper subjectMapper;
	@Autowired
	CourseMapper courseMapper;
	@Autowired
	ChapterMapper chapterMapper;
	@Autowired
	CrawlerQuestionMapper crawlerQuestionMapper;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private Set<String> questionUrls = new HashSet<String>();
	
	private Map<String, Object> chapterQuestionListMap = new HashMap<>();

	private RunData runData;
	
	@Override
	public void crawlerSubject() {
		XxlCrawler crawler = new XxlCrawler.Builder()
            .setUrls("https://tiku.baidu.com/tikupc/chapterlist/1bfd700abb68a98271fefa04-26-knowpoint-11")
            .setAllowSpread(false)
            .setFailRetryCount(5)
            .setThreadCount(20)
            .setPageParser(new PageParser<SubjectVo>() {
            	
                @Override
                public void parse(Document html, Element pageVoElement, SubjectVo subjectVo) {
                    // 解析封装 PageVo 对象
                    String pageUrl = html.baseUri();
                    if(pageUrl.contains("https://tiku.baidu.com/tikupc/chapterlist/1bfd700abb68a98271fefa04-26-knowpoint-11")) {
                    	logger.info("开始解析书籍：{}" , pageUrl);
                    	ChapterEntity chapterCondition = new ChapterEntity();
                    	chapterCondition.setName(subjectVo.getChapterCurrName());
                    	ChapterEntity chapterEntity = chapterMapper.selectOne(chapterCondition);
                    	if(chapterEntity != null) {
                    		return;
                    	}
                    	
                    	ChapterEntity rootChapter = new ChapterEntity();
                    	rootChapter.setCourseId(COURSE_ID);
                    	rootChapter.setParentCode("-1");
                    	rootChapter.setName(subjectVo.getChapterCurrName());
                    	rootChapter.setQuestionNum(0);
                    	rootChapter.setPointNum(0);
                    	rootChapter.setOrders(0);
                    	rootChapter.setTreeLeaf(false);
                    	rootChapter.setTreeLevel(0);
                    	rootChapter.setParentCodes("-1,");
                    	rootChapter.setBookId(BOOK_ID);
                    	rootChapter.setSubjectId(SUBJECT_ID);
                		chapterMapper.insert(rootChapter);
                		
                    	Elements elements = pageVoElement.getElementsByClass("detail-chapter");
                    	for(int n=0 ; n<elements.size() ; n++) {
                    		Element element = elements.get(n);
                    		String chapterName = element.getElementsByClass("detail-chapter-title").get(0).getElementsByTag("h3").text();
                    		logger.info(chapterName);
                    		ChapterEntity chapter = new ChapterEntity();
                    		chapter.setCourseId(COURSE_ID);
                    		chapter.setParentCode(rootChapter.getId());
                    		chapter.setName(chapterName);
                    		chapter.setQuestionNum(0);
                    		chapter.setPointNum(0);
                    		chapter.setOrders(n+1);
                    		chapter.setBookId(BOOK_ID);
                    		chapter.setTreeLeaf(false);
                    		chapter.setTreeLevel(1);
                    		chapter.setSubjectId(SUBJECT_ID);
                    		chapterMapper.insert(chapter);
                    		chapter.setParentCodes(rootChapter.getParentCodes() + chapter.getId() + ",");
                    		chapterMapper.updateById(chapter);
                    		
                    		Elements detailKpoint1s = element.getElementsByClass("detail-kpoint-1");
                    		for(Element detailKpoint1 : detailKpoint1s) {
                    			Elements kpoint1Titles = detailKpoint1.getElementsByClass("kpoint-1-title");
                    			for(int i=0 ; i<kpoint1Titles.size() ; i++) {
                    				Element kpoint1Title = kpoint1Titles.get(i);
                        			String chapterName1 = kpoint1Title.getElementsByTag("h4").text();
                        			logger.info(chapterName1);
                        			
                        			ChapterEntity chapter1 = new ChapterEntity();
                        			chapter1.setCourseId(COURSE_ID);
                        			chapter1.setBookId(BOOK_ID);
                        			chapter1.setParentCode(chapter.getId());
                        			chapter1.setName(chapterName1);
                        			chapter1.setQuestionNum(0);
                        			chapter1.setPointNum(0);
                        			chapter1.setOrders(i+1);
                        			chapter1.setSubjectId(SUBJECT_ID);
                        			
                        			chapter1.setTreeLeaf(false);
                        			chapter1.setTreeLevel(2);
                            		chapterMapper.insert(chapter1);
                        			chapter1.setParentCodes(chapter.getParentCodes() + chapter1.getId() + ",");
                        			chapterMapper.updateById(chapter1);
                        			
                        			
                        			Elements detailKpoint2s =  detailKpoint1.getElementsByClass("detail-kpoint-2");
                        			for(int k=0 ; k<detailKpoint2s.size() ; k++) {
                        				Element detailKpoint = detailKpoint2s.get(k);
                        				String chapterName2 = detailKpoint.getElementsByTag("h5").text();
                        				logger.info(chapterName2);
                        				
                        				ChapterEntity chapter2 = new ChapterEntity();
                        				chapter2.setCourseId(COURSE_ID);
                        				chapter2.setBookId(BOOK_ID);
                        				chapter2.setParentCode(chapter1.getId());
                        				chapter2.setName(chapterName2);
                        				chapter2.setQuestionNum(0);
                        				chapter2.setPointNum(0);
                        				chapter2.setOrders(k+1);
                        				chapter2.setTreeLeaf(true);
                        				chapter2.setTreeLevel(3);
                        				chapter2.setSubjectId(SUBJECT_ID);
                                		chapterMapper.insert(chapter2);
                        				chapter2.setParentCodes(chapter1.getParentCodes() + chapter2.getId() + ",");
                                		chapterMapper.updateById(chapter2);
                                		
                                		Elements maskList = detailKpoint.getElementsByClass("mask");
                                		if(maskList.size() > 0) {
                                			String questionUrl = maskList.get(0).getElementsByTag("a").attr("abs:href");
                                    	//	questionUrl = questionUrl.replace("1-5", "1-20");
                                    		chapterQuestionListMap.put(questionUrl, chapter2);
                                    		
                                    		runData.addUrl(questionUrl);
                                		}
                        			}
                        		}
                    		}
                    	}
                    	
                    }
                    
                    if(pageUrl.contains("https://tiku.baidu.com/tikupc/chapterdetail")) {
                    	// 加入待解析题目列表
                    	logger.info("url : {}" , pageUrl);
                    	logger.info("subjectVo : {}" , JSONObject.toJSON(subjectVo));
                		ChapterEntity chapterEntity = (ChapterEntity) chapterQuestionListMap.get(pageUrl);
                        for(String questionUrl : subjectVo.getQuestionUrls()) {
                        	if(!questionUrls.contains(questionUrl)) {
                        		//  处理URL 
                        	//	runData.addUrl(questionUrl);
                        		questionUrls.add(questionUrl);
                        		
                        		CrawlerQuestionEntity condition = new CrawlerQuestionEntity();
                        		condition.setQuestionUrl(questionUrl);
                        		if(crawlerQuestionMapper.selectOne(condition) == null) {
                            		CrawlerQuestionEntity crawlerQuestion = new CrawlerQuestionEntity();
                            		crawlerQuestion.setQuestionUrl(questionUrl);
                            		crawlerQuestion.setChapterId(chapterEntity.getId());
                            		crawlerQuestion.setStatus("0");
                            		crawlerQuestionMapper.insert(crawlerQuestion);
                        		}else {
                        			logger.info(questionUrl+"已经爬取");
                        		}
                        	}
                        }
                        if(!StringUtils.isEmpty(subjectVo.getNextQuestionPage())) {
                        	runData.addUrl(subjectVo.getNextQuestionPage());
                        	chapterQuestionListMap.put(subjectVo.getNextQuestionPage(), chapterEntity);
                        }
                    }
                    
                }
        }).build();
		
		runData = crawler.getRunData();
		// 获取科目
		crawler.start(true);
	}

}
