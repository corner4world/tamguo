package com.tamguo.modules.book.model.enums;

import java.io.Serializable;
import com.baomidou.mybatisplus.enums.IEnum;

/**
 * 用户状态
 */
public enum BizTypeEnum implements IEnum {
	DOCUMENT("document", "文档");

    private String value;
    private String desc;

    BizTypeEnum(final String value, final String desc) {
        this.value = value;
        this.desc = desc;
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
