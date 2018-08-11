package com.tamguo;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tamguo.dao.CrawlerPaperMapper;
import com.tamguo.dao.PaperMapper;
import com.tamguo.model.CrawlerPaperEntity;
import com.tamguo.model.PaperEntity;
import com.tamguo.model.enums.QuestionType;
import com.tamguo.model.vo.PaperVo;
import com.xuxueli.crawler.XxlCrawler;
import com.xuxueli.crawler.parser.PageParser;
import com.xuxueli.crawler.parser.strategy.HtmlUnitPageLoader;
import com.xuxueli.crawler.rundata.RunData;

// 北京模拟试卷，真题试卷已经爬取完毕
@RunWith(SpringRunner.class)
@SpringBootTest
public class PaperCrawler {
	
	// 高考
	private final String SUBJECT_ID = "gaokao";
	// 科目
	private final String COURSE_ID = "likeshuxue";
	// 110000 北京
	private final String AREA_ID = "110000";
	// 年份
	private final String YEAR = "2013";
	// 真题试卷             类型(1:真题试卷,2:模拟试卷,3:押题预测,4:名校精品)
	private final String PAPER_TYPE = "2";
	// 开始采集的URL
	private final String START_URL = "https://tiku.baidu.com/tikupc/paperlist/1bfd700abb68a98271fefa04-16-4-2013-37-1-download";
	
	private RunData runData;
	
	@Autowired
	private PaperMapper paperMapper;
	@Autowired
	private CrawlerPaperMapper crawlerPaperMapper;
	
	@Test
	public void crawler() {
		XxlCrawler crawler = new XxlCrawler.Builder()
	            .setUrls(START_URL)
	            .setAllowSpread(false)
	            .setFailRetryCount(5)
	            .setThreadCount(1)
	            .setPageLoader(new HtmlUnitPageLoader())
	            .setPageParser(new PageParser<PaperVo>() {
	            	
	                @Override
	                public void parse(Document html, Element pageVoElement, PaperVo paperVo) {
	                    // 解析封装 PageVo 对象
	                    String pageUrl = html.baseUri();
	                    if(pageUrl.contains("https://tiku.baidu.com/tikupc/paperdetail")) {
	                    	System.out.println(paperVo.getPaperName());
	                    	
	                    	PaperEntity paper = new PaperEntity();
	                    	paper.setSubjectId(SUBJECT_ID);
	                    	paper.setCourseId(COURSE_ID);
	                    	paper.setSchoolId("");
	                    	paper.setAreaId(AREA_ID);
	                    	paper.setCreaterId("system");
	                    	paper.setName(paperVo.getPaperName());
	                    	paper.setYear(YEAR);
	                    	paper.setFree("0");
	                    	paper.setSeoTitle(paperVo.getPaperName());
	                    	paper.setSeoKeywords("");
	                    	paper.setSeoDescription("");
	                    	paper.setType(PAPER_TYPE);
	                    	JSONArray entitys = new JSONArray();
	                    	// 处理类型问题
	                    	for(int i=0 ; i<paperVo.getQuestionInfoTypes().size() ; i++) {
	                    		JSONObject entity = new JSONObject();
	                    		entity.put("id", i+1);
	                    		entity.put("name", paperVo.getQuestionInfoTypes().get(i));
	                    		entity.put("title", paperVo.getQuestionInfoTitles().get(i));
	                    		QuestionType questionType = QuestionType.getQuestionType(paperVo.getQuestionInfoTypes().get(i));
	                    		entity.put("type", questionType.getValue());
	                    		
	                    		
	                    		entitys.add(entity);
	                    	}
	                    	paper.setQuestionInfo(entitys.toJSONString());
	                    	paperMapper.insert(paper);
	                    	
	                    	// 插入
	                    	for(int i=0 ; i<paperVo.getQuestionUrls().size() ; i++) {
	                    		CrawlerPaperEntity cp = new CrawlerPaperEntity();
	                    		cp.setPaperId(paper.getId());
	                    		cp.setQuestionUrl(paperVo.getQuestionUrls().get(i));
	                    		cp.setQueindex(paperVo.getQueindexs().get(i));
	                    	
	                    		crawlerPaperMapper.insert(cp);
	                    	}
	                    }
	                    if(pageUrl.contains("https://tiku.baidu.com/tikupc/paperlist")) {
	                    	for(int i=0 ; i<paperVo.getPaperUrls().size() ; i++) {
		                    	runData.addUrl(paperVo.getPaperUrls().get(i));
	                    	}
	                    }
	                }
	        }).build();
			
			runData = crawler.getRunData();
			// 获取科目
			crawler.start(true);
	}

	
}
