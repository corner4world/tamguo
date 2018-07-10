package com.tamguo.service;

import java.util.List;
import com.tamguo.model.MenuEntity;

/**
 * Service - 类型
 * 
 * @author candy.tam
 *
 */
public interface IMenuService {

	/** 获取所有头部菜单 */
	public List<MenuEntity> findAllMenus();

}
