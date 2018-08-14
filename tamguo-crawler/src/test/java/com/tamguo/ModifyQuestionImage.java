package com.tamguo;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.model.QuestionEntity;
import com.tamguo.service.IQuestionService;

/**
 * Test - 修改用户图片
 * 
 * @author tamguo
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ModifyQuestionImage {
	
	@Autowired
	private IQuestionService iQuestionService;

	@SuppressWarnings("unchecked")
	@Test
	public void modify() {
		Integer current = 98 ; 
		Integer size = 100;
		
		while(true) {
			Page<QuestionEntity> page = new Page<>(current , size);
			Page<QuestionEntity> entitys = iQuestionService.selectPage(page , Condition.create().orderAsc(Arrays.asList("id")));
			if(entitys.getCurrent() > 759) {
				break;
			}
			// 处理数据
			for(int i=0 ; i<entitys.getSize() ; i++) {
				QuestionEntity question = entitys.getRecords().get(i);
				question.setAnalysis(question.getAnalysis().replaceAll("http://www.tamguo.com/", ""));
				question.setContent(question.getContent().replaceAll("http://www.tamguo.com/", ""));
				if(question.getAnswer() == null) {
					question.setAnswer("");
				}
				question.setAnswer(question.getAnswer().replaceAll("http://www.tamguo.com/", ""));
			}
			iQuestionService.updateAllColumnBatchById(entitys.getRecords());
			current = current + 1;
			System.out.println("当前Current" + current);
		}
	}
}
