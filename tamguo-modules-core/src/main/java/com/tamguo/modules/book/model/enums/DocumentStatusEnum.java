package com.tamguo.modules.book.model.enums;

import java.io.Serializable;
import com.baomidou.mybatisplus.enums.IEnum;

/**
 * 用户状态
 */
public enum DocumentStatusEnum implements IEnum {
	NORMAL("normal", "正常"),
	HISTORY("history", "历史版本");

    private String value;
    private String desc;

    DocumentStatusEnum(final String value, final String desc) {
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
