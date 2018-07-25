package com.tamguo.modules.sys.model.enums;

import java.io.Serializable;
import com.baomidou.mybatisplus.enums.IEnum;

/**
 * 用户状态
 */
public enum SysUserMgrTypeEnum implements IEnum {
	NONEADMIN("0", "非系统管理员"),
	SYSTEMADMIN("1", "系统管理员"),
	SECADMIN("2" , "二级管理员");

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
