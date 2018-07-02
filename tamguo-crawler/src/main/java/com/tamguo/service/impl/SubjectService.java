package com.tamguo.service.impl;

import java.math.BigInteger;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tamguo.dao.CourseMapper;
import com.tamguo.dao.SubjectMapper;
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
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private RunData runData;
	
	@Override
	public void crawlerSubject() {
		XxlCrawler crawler = new XxlCrawler.Builder()
            .setUrls("https://tiku.baidu.com/")
            .setWhiteUrlRegexs("https://tiku\\.baidu\\.com/tikupc/homepage/\\w+" , "https://tiku.baidu.com/")
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
                        	
                        	// 获取Course
                        	Elements elements = pageVoElement.getElementsByClass("all-list-li");
                        	for(int k=0 ; k<elements.size() ; k++) {
                        		Element element = elements.get(k);
                        	 	String url = element.child(0).attr("href");
                        	 	runData.addUrl(url);
                        	}
                        }
                    }
                    
                    if(pageUrl.contains("https://tiku.baidu.com/tikupc/homepage/")) {
                    	logger.info("开始解析科目分类：{}" , pageUrl);
                    	for(int i=0 ; i<subjectVo.getCourseName().size() ; i++) {
                        	logger.info("科目名称:{}" , subjectVo.getCourseName().get(i));
                        	SubjectEntity subject = subjectMapper.findByName(subjectVo.getSubjectName());
                        	
                        	CourseEntity course = new CourseEntity();
                        	course.setIcon(StringUtils.EMPTY);
                        	course.setName(subjectVo.getCourseName().get(i));
                        	course.setOrders(i+1);
                        	course.setPointNum(BigInteger.ZERO);
                        	course.setQuestionNum(BigInteger.ZERO);
                        	course.setSeoDescription(subjectVo.getCourseName().get(i));
                        	course.setSeoKeywords(subjectVo.getCourseName().get(i));
                        	course.setSeoTitle(subjectVo.getCourseName().get(i));
                        	course.setSubjectId(subject.getUid());
                        	
                        	courseMapper.insert(course);
                        }
                    }
                }
                
        }).build();
		
		runData = crawler.getRunData();
		
		
		
		// 获取科目
		crawler.start(true);
	}

}
