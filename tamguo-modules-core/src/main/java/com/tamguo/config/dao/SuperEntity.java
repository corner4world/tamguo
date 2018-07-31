package com.tamguo.config.dao;

import java.io.Serializable;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;

/**
 * 实体父类
 */
public class SuperEntity<T extends Model<?>> extends Model<T> {

	private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @Override
    protected Serializable pkVal() {
        return this.getId();
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
}
