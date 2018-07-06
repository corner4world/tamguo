package com.tamguo.service.impl;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.config.redis.CacheService;
import com.tamguo.dao.ChapterMapper;
import com.tamguo.dao.CourseMapper;
import com.tamguo.dao.CrawlerQuestionMapper;
import com.tamguo.dao.QuestionMapper;
import com.tamguo.dao.SubjectMapper;
import com.tamguo.model.ChapterEntity;
import com.tamguo.model.CourseEntity;
import com.tamguo.model.CrawlerQuestionEntity;
import com.tamguo.model.QuestionEntity;
import com.tamguo.model.SubjectEntity;
import com.tamguo.model.vo.QuestionVo;
import com.tamguo.service.IQuestionService;
import com.xuxueli.crawler.XxlCrawler;
import com.xuxueli.crawler.conf.XxlCrawlerConf;
import com.xuxueli.crawler.parser.PageParser;
import com.xuxueli.crawler.parser.strategy.HtmlUnitPageLoader;
import com.xuxueli.crawler.rundata.RunData;
import com.xuxueli.crawler.util.FileUtil;

@Service
public class QuestionService implements IQuestionService{
	
	@Autowired
	QuestionMapper questionMapper;
	@Autowired
	CrawlerQuestionMapper crawlerQuestionMapper;
	@Autowired
	ChapterMapper chapterMapper;
	@Autowired
	CourseMapper courseMapper;
	@Autowired
	SubjectMapper subjectMapper;
	@Autowired
	CacheService cacheService;
	private static final String FILES_NO_FORMAT = "00000";
	private static final String FILES_PREFIX = "FP";
	private static final String DOMAIN = "http://static.tamguo.com";
	
	private RunData runData;

	@Override
	public void crawlerQuestion() {
		
		XxlCrawler crawler = new XxlCrawler.Builder()
	            .setAllowSpread(false)
	            .setThreadCount(10)
	            .setPageLoader(new HtmlUnitPageLoader())
	            .setPageParser(new PageParser<QuestionVo>() {
	            	
	                @Override
	                public void parse(Document html, Element pageVoElement, QuestionVo questionVo) {
                		CrawlerQuestionEntity condition = new CrawlerQuestionEntity();
	                	condition.setQuestionUrl(html.baseUri());
	                	CrawlerQuestionEntity crawlerQuestion = crawlerQuestionMapper.selectOne(condition);
	                	ChapterEntity chapter = chapterMapper.selectById(crawlerQuestion.getChapterId());
	                	CourseEntity course = courseMapper.selectById(chapter.getCourseId());
	                	SubjectEntity subject = subjectMapper.selectById(course.getSubjectId());
	                	
	                	QuestionEntity question = new QuestionEntity();
	                	question.setAnalysis(questionVo.getAnalysis());
	                	question.setAnswer(questionVo.getAnswer());
	                	question.setAuditStatus("1");
	                	question.setChapterId(chapter.getUid());
	                	question.setContent(questionVo.getContent());
	                	question.setCourseId(course.getUid());
	                	question.setPaperId(null);
	                	question.setQuestionType("1");
	                	if(questionVo.getReviewPoint() != null && questionVo.getReviewPoint().size() > 0) {
		                	question.setReviewPoint(StringUtils.join(questionVo.getReviewPoint().toArray(), ","));
	                	}
	                	question.setScore(questionVo.getScore());
	                	question.setSubjectId(subject.getUid());
	                	question.setYear(questionVo.getYear());
	                	
	                	if (questionVo.getAnswerImages()!=null && questionVo.getAnswerImages().size() > 0) {
                            Set<String> imagesSet = new HashSet<>(questionVo.getAnswerImages());
                            for (String img: imagesSet) {

                                // 下载图片文件
                            	String fileName = getFileName(img);
                                File dir = new File(getFilePath());
                				if (!dir.exists())
                					dir.mkdirs();
                                boolean ret = FileUtil.downFile(img, XxlCrawlerConf.TIMEOUT_MILLIS_DEFAULT, getFilePath(), fileName);
                                System.out.println("down images " + (ret?"success":"fail") + "：" + img);
                                
                                // 替换URL
                                questionVo.setAnswer(questionVo.getAnswer().replace(img, DOMAIN + getFilePath() + fileName));
                            }
                        }
	                	
	                	if (questionVo.getAnalysisImages()!=null && questionVo.getAnalysisImages().size() > 0) {
                            Set<String> imagesSet = new HashSet<>(questionVo.getAnalysisImages());
                            for (String img: imagesSet) {

                                // 下载图片文件
                                String fileName = getFileName(img);
                                File dir = new File(getFilePath());
                				if (!dir.exists())
                					dir.mkdirs();
                                boolean ret = FileUtil.downFile(img, XxlCrawlerConf.TIMEOUT_MILLIS_DEFAULT, getFilePath(), fileName);
                                System.out.println("down images " + (ret?"success":"fail") + "：" + img);
                                
                                // 替换URL
                                questionVo.setAnalysis(questionVo.getAnalysis().replace(img, DOMAIN + getFilePath() + fileName));
                            }
                        }
	                	// 处理图片
	                	questionMapper.insert(question);
	                	
	                }
	                
	                public String getFileName(String img) {
	            		return getFileNo() + img.substring(img.lastIndexOf("."));
	            	}
	                
	                private String getFilePath() {
	                	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
	            		String format = sdf.format(new Date());
	            		return "/images/question/" + format + "/";
	                }

	            	private String getFileNo() {
	            		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
	            		String format = sdf.format(new Date());
	            		DecimalFormat df = new DecimalFormat(FILES_NO_FORMAT);
	            		String key = FILES_PREFIX + format;
	            		Long incr = cacheService.incr(key);
	            		String avatorNo = FILES_PREFIX + df.format(incr);
	            		return avatorNo;
	            	}
	        }).build();
			
			runData = crawler.getRunData();
			int page = 1;
			int pageSize = 100;
			while(true) {
				Page<CrawlerQuestionEntity> questionPage = new Page<CrawlerQuestionEntity>(page , pageSize);
				List<CrawlerQuestionEntity> questionList = crawlerQuestionMapper.queryPageOrderUid(questionPage);
				for(int i=0 ;i<questionList.size() ; i++) {
					runData.addUrl(questionList.get(i).getQuestionUrl());
				}
				page++;
				if(questionList.size() < 100) {
					break;
				}
			}
			// 获取科目
			crawler.start(true);
	}
	
	
}
