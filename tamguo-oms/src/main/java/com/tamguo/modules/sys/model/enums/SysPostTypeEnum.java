package com.tamguo.modules.sys.model.enums;

import java.io.Serializable;
import com.baomidou.mybatisplus.enums.IEnum;

/**
 * 用户状态
 */
public enum SysPostTypeEnum implements IEnum {
	GAOGUAN("gaoguan", "高管"),
	ZHONGCENG("zhongceng", "中层"),
	JICENG("jiceng" , "基层"),
	QITA("qita" , "其他");

    private String value;
    private String desc;

    SysPostTypeEnum(final String value, final String desc) {
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
