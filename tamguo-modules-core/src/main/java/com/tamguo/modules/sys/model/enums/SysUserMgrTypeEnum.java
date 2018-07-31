package com.tamguo.modules.sys.model.enums;

import java.io.Serializable;
import com.baomidou.mybatisplus.enums.IEnum;

/**
 * 用户状态
 */
public enum SysUserMgrTypeEnum implements IEnum {
	NONE_ADMIN("0", "非系统管理员"),
	SYSTEM_ADMIN("1", "系统管理员"),
	SEC_ADMIN("2" , "二级管理员");

    private String value;
    private String desc;

    SysUserMgrTypeEnum(final String value, final String desc) {
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
