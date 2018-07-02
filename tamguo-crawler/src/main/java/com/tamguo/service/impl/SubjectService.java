package com.tamguo.service.impl;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tamguo.dao.SubjectMapper;
import com.tamguo.model.SubjectEntity;
import com.tamguo.model.vo.SubjectVo;
import com.tamguo.service.ISubjectService;
import com.xuxueli.crawler.XxlCrawler;
import com.xuxueli.crawler.parser.PageParser;

@Service
public class SubjectService implements ISubjectService{
	
	@Autowired
	SubjectMapper subjectMapper;
	
	@Override
	public void crawlerSubject() {
		XxlCrawler crawler = new XxlCrawler.Builder()
            .setUrls("https://tiku.baidu.com/")
            .setWhiteUrlRegexs("https://tiku\\.baidu\\.com/")
            .setPageParser(new PageParser<SubjectVo>() {
                @Override
                public void parse(Document html, Element pageVoElement, SubjectVo subjectVo) {
                    // 解析封装 PageVo 对象
                    String pageUrl = html.baseUri();
                    System.out.println(pageUrl + "：" + subjectVo.toString());
                    
                    for(int i=0 ; i<subjectVo.getName().size() ; i++) {
                    	String name = subjectVo.getName().get(i);
                    		
                    	SubjectEntity subject = subjectMapper.findByName(name);
                    	if(subject != null) {
                    		continue;
                    	}
                    	SubjectEntity entity = new SubjectEntity();
                    	entity.setName(name);
                    	subjectMapper.insert(entity);
                    }
                }
            })
            .build();
		
		crawler.start(true);
	}

}
