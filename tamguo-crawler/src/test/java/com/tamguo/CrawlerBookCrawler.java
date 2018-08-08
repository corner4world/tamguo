package com.tamguo;

import com.tamguo.service.ICrawlerBookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerBookCrawler {

	@Autowired
    ICrawlerBookService crawlerBookService;
	
    @Test
    public void crawlerBook() throws Exception {
        crawlerBookService.crawlerBook();
    }
	
}
