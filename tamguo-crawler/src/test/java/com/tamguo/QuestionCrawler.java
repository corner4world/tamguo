package com.tamguo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tamguo.service.IQuestionService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionCrawler {

	@Autowired
	IQuestionService iQuestionService;
	
    @Test
    public void crawlerSubject() throws Exception {
    	iQuestionService.crawlerQuestion();
    }
	
    
}
