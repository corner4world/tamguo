package com.tamguo.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tamguo.dao.ChapterMapper;
import com.tamguo.dao.CourseMapper;
import com.tamguo.dao.SubjectMapper;
import com.tamguo.model.ChapterEntity;
import com.tamguo.model.CourseEntity;
import com.tamguo.model.SubjectEntity;
import com.tamguo.model.vo.SubjectVo;
import com.tamguo.service.ISubjectService;
import com.xuxueli.crawler.XxlCrawler;
import com.xuxueli.crawler.parser.PageParser;
import com.xuxueli.crawler.rundata.RunData;

@Service
public class SubjectService implements ISubjectService{
	
	@Autowired
	SubjectMapper subjectMapper;
	@Autowired
	CourseMapper courseMapper;
	@Autowired
	ChapterMapper chapterMapper;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private List<String> urls = new ArrayList<>();
	
	private RunData runData;
	
	@Override
	public void crawlerSubject() {
		XxlCrawler crawler = new XxlCrawler.Builder()
            .setUrls("https://tiku.baidu.com/")
            .setWhiteUrlRegexs("https://tiku.baidu.com/tikupc/homepage/\\w+","https://tiku.baidu.com/tikupc/homepage/\\w+" 
            		, "https://tiku.baidu.com/"
            		, "https://tiku.baidu.com/tikupc/chapterlist/.*")
            .setPageParser(new PageParser<SubjectVo>() {
            	
                @Override
                public void parse(Document html, Element pageVoElement, SubjectVo subjectVo) {
                    // 解析封装 PageVo 对象
                    String pageUrl = html.baseUri();
                    if(pageUrl.equals("https://tiku.baidu.com/")) {
                    	logger.info("开始解析考试分类：{}" , pageUrl);
                    	for(int i=0 ; i<subjectVo.getName().size() ; i++) {
                        	String name = subjectVo.getName().get(i);
                        		
                        	SubjectEntity subject = subjectMapper.findByName(name);
                        	if(subject != null) {
                        		continue;
                        	}
                        	SubjectEntity entity = new SubjectEntity();
                        	if(name.equals("国考")) {
                        		name = "公务员（国考）";
                        	}
                        	entity.setName(name);
                        	subjectMapper.insert(entity);
                        }
                    	
                	 	// 加入科目爬取数据
                    	for(String url : subjectVo.getCourseUrls()) {
                    		runData.addUrl(url);
                    	}
                    }
                    
                    if(pageUrl.contains("https://tiku.baidu.com/tikupc/homepage/")) {
                    	logger.info("开始解析科目分类：{}" , pageUrl);
                    	for(int i=0 ; i<subjectVo.getCourseName().size() ; i++) {
                        	logger.info("科目名称:{}" , subjectVo.getCourseName().get(i));
                        	SubjectEntity subject = subjectMapper.findByName(subjectVo.getSubjectName());
                        	if(subject == null) {
                        		continue;
                        	}
                        	CourseEntity course = new CourseEntity();
                        	course.setName(subjectVo.getCourseName().get(i));
                        	CourseEntity courseEntity = courseMapper.selectOne(course);
                        	if(courseEntity != null) {
                        		continue;
                        	}
                        	course.setOrders(i+1);
                        	course.setPointNum(BigInteger.ZERO);
                        	course.setQuestionNum(BigInteger.ZERO);
                        	course.setSeoDescription(subjectVo.getCourseName().get(i));
                        	course.setSeoKeywords(subjectVo.getCourseName().get(i));
                        	course.setSeoTitle(subjectVo.getCourseName().get(i));
                        	course.setSubjectId(subject.getUid());
                        	
                        	courseMapper.insert(course);
                        }
                    	
                    	// 加入科目爬取数据
                    	for(String url : subjectVo.getChapterUrlsTemp()) {
                    		runData.addUrl(url);
                    	}
                    }
                    
                    if(pageUrl.contains("https://tiku.baidu.com/tikupc/chapterlist/")) {
                    	logger.info("开始解析章节：{}" , pageUrl);
                    	CourseEntity courseCondition = new CourseEntity();
                    	courseCondition.setName(subjectVo.getChapterPageCourseName());
                    	CourseEntity c = courseMapper.selectOne(courseCondition);
                    	if(c == null) {
                    		runData.addUrl(pageUrl);
                    		return;
                    	}
                    	ChapterEntity chapterCondition = new ChapterEntity();
                    	chapterCondition.setName(subjectVo.getChapterCurrName());
                    	ChapterEntity chapterEntity = chapterMapper.selectOne(chapterCondition);
                    	if(chapterEntity != null) {
                    		return;
                    	}
                    	
                    	ChapterEntity rootChapter = new ChapterEntity();
                    	rootChapter.setCourseId(c.getUid());
                    	rootChapter.setParentId("-1");
                    	rootChapter.setName(subjectVo.getChapterCurrName());
                    	rootChapter.setQuestionNum(0);
                    	rootChapter.setPointNum(0);
                    	rootChapter.setOrders(0);
                		chapterMapper.insert(rootChapter);
                		
                    	Elements elements = pageVoElement.getElementsByClass("detail-chapter");
                    	for(int n=0 ; n<elements.size() ; n++) {
                    		Element element = elements.get(n);
                    		String chapterName = element.getElementsByClass("detail-chapter-title").get(0).getElementsByTag("h3").text();
                    		logger.info(chapterName);
                    		ChapterEntity chapter = new ChapterEntity();
                    		chapter.setCourseId(c.getUid());
                    		chapter.setParentId(rootChapter.getUid());
                    		chapter.setName(chapterName);
                    		chapter.setQuestionNum(0);
                    		chapter.setPointNum(0);
                    		chapter.setOrders(n+1);
                    		chapterMapper.insert(chapter);
                    		
                    		Elements detailKpoint1s = element.getElementsByClass("detail-kpoint-1");
                    		for(Element detailKpoint1 : detailKpoint1s) {
                    			Elements kpoint1Titles = detailKpoint1.getElementsByClass("kpoint-1-title");
                    			for(int i=0 ; i<kpoint1Titles.size() ; i++) {
                    				Element kpoint1Title = kpoint1Titles.get(i);
                        			String chapterName1 = kpoint1Title.getElementsByTag("h4").text();
                        			logger.info(chapterName1);
                        			
                        			ChapterEntity chapter1 = new ChapterEntity();
                        			chapter1.setCourseId(c.getUid());
                        			chapter1.setParentId(chapter.getUid());
                        			chapter1.setName(chapterName1);
                        			chapter1.setQuestionNum(0);
                        			chapter1.setPointNum(0);
                        			chapter1.setOrders(i+1);
                            		chapterMapper.insert(chapter1);
                        			
                        			
                        			Elements detailKpoint2s =  detailKpoint1.getElementsByClass("detail-kpoint-2");
                        			for(int k=0 ; k<detailKpoint2s.size() ; k++) {
                        				Element detailKpoint = detailKpoint2s.get(k);
                        				String chapterName2 = detailKpoint.getElementsByTag("h5").text();
                        				logger.info(chapterName2);
                        				
                        				ChapterEntity chapter2 = new ChapterEntity();
                        				chapter2.setCourseId(c.getUid());
                        				chapter2.setParentId(chapter1.getUid());
                        				chapter2.setName(chapterName2);
                        				chapter2.setQuestionNum(0);
                        				chapter2.setPointNum(0);
                        				chapter2.setOrders(k+1);
                                		chapterMapper.insert(chapter2);
                        			}
                        		}
                    		}
                    	}
                    	
                    	// 剔除已经爬取的数据
                    	urls.add(pageUrl);
                    	// 加入科目爬取数据
                    	for(String url : subjectVo.getChapterUrls()) {
                    		if(url.equals("https://tiku.baidu.com"+pageVoElement.getElementsByClass("main-inner").get(0).getElementsByClass("selected").get(0).getElementsByTag("a").attr("href"))) {
                    			continue;
                    		}
                    		if(!urls.contains(url)) {
                        		runData.addUrl(url);
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
