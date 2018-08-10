package com.tamguo.model.enums;

import java.io.Serializable;
/**
 * 试题类型、、题目类型(1.单选题；2.多选题; 3.解答题)
 * 
 * @author tamguo
 *
 */
public enum QuestionType {
	
	DANXUANTI("1", "单选题"),
	DUOXUANTI("2", "多选题"),
	TIANKONGTI("3", "填空题"),
	PANDUANTI("4", "判断题"),
	WENDATI("5", "问答题");

    private String value;
    private String desc;

    QuestionType(final String value, final String desc) {
        this.value = value;
        this.desc = desc;
    }
    
    public static QuestionType getQuestionType(String value) {
    	if("单选题".equals(value)) {
    		return DANXUANTI;
    	}else if("多选题".equals(value)) {
    		return DUOXUANTI;
    	}else if("填空题".equals(value)) {
    		return TIANKONGTI;
    	}else if("判断题".equals(value)) {
    		return PANDUANTI;
    	}else if("问答题".equals(value)) {
    		return WENDATI;
    	}else if("选择题".equals(value)) {
    		return DANXUANTI;
    	}else if("简答题（综合题）".equals(value)) {
    		return WENDATI;
    	}
    	return WENDATI;
    }

    public Serializable getValue() {
        return this.value;
    }

    public String getDesc(){
        return this.desc;
    }
    
    @Override
    public String toString() {
    	return this.value;
    }
	
}
