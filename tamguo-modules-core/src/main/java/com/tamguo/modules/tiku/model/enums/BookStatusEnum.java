package com.tamguo.modules.tiku.model.enums;

import java.io.Serializable;
import com.baomidou.mybatisplus.enums.IEnum;

/**
 * 类型状态
 */
public enum BookStatusEnum implements IEnum {
	NORMAL("normal", "正常"),
	DELETE("delete", "删除"),
	DISABLED("disabled" , "停用");

    private String value;
    private String desc;

    BookStatusEnum(final String value, final String desc) {
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
