package com.tamguo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tamguo.service.IChapterService;

/**
 * Num - 修改章节题目数量
 * 
 * @author tamguo
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ModifyChpaterQuestionNum {

	@Autowired
	IChapterService iChapterService;
	
    @Test
    public void crawlerSubject() throws Exception {
    	iChapterService.modifyQuestionNum();
    }
    
}
