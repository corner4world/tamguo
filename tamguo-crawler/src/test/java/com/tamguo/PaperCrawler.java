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
	private final String COURSE_ID = "wuli";
	// 110000 北京 | 310000 上海 | 500000 重庆 | 120000 天津 | 370000 山东 | 410000 河南 | 420000 湖北 | 320000 江苏 | 330000 浙江
	// 140000 山西 | 350000 福建 | 340000 安徽 | 220000 吉林 | 150000 内蒙古 | 640000 宁夏 | 650000 新疆 | 广西 450000 | 210000 辽宁
	// 230000 黑龙江 | 610000 陕西 | 360000 江西 | 440000 广东 | 430000 湖南 | 460000 海南 | 530000 云南 | 510000 四川 | 630000 青海
	// 620000 甘肃  | 130000 河北 | 540000 西藏 | 贵州 520000
	private final String AREA_ID = "150000";
	// 年份
	private final String YEAR = "2016";
	// 真题试卷             类型(1:真题试卷,2:模拟试卷,3:押题预测,4:名校精品)
	private final String PAPER_TYPE = "2";
	// 开始采集的URL
	private final String START_URL = "https://tiku.baidu.com/tikupc/paperlist/1bfd700abb68a98271fefa04-18-4-2016-965-1-download";
	
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
