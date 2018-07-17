package com.tamguo;

import com.tamguo.service.IBookService;
import com.tamguo.service.ISubjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookCrawler {

	@Autowired
    IBookService bookService;
	
    @Test
    public void crawlerBook() throws Exception {
        bookService.crawlerBook();
    }
	
}
