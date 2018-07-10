package com.tamguo.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.dao.MenuMapper;
import com.tamguo.dao.redis.CacheService;
import com.tamguo.model.MenuEntity;
import com.tamguo.service.IMenuService;

@Service
public class MenuService extends ServiceImpl<MenuMapper, MenuEntity> implements IMenuService{
	
	@Autowired
	private MenuMapper menuMapper;
	@Autowired
	private CacheService cacheService;


	@SuppressWarnings("unchecked")
	@Override
	public List<MenuEntity> findAllMenus() {
		List<MenuEntity> allMenuList = ((List<MenuEntity>) cacheService.getObject("all_index_menu"));
		if(allMenuList == null || allMenuList.isEmpty()){
			allMenuList = menuMapper.findAllFatherMenus();
			for(MenuEntity menu : allMenuList){
				List<MenuEntity> childSubjects = menuMapper.findMenuByParentId(menu.getUid());
				menu.setChildSubjects(childSubjects);
			}
			cacheService.setObject("all_index_menu", allMenuList , 2 * 60 * 60);
		}
		return allMenuList;
	}


}
