package com.tamguo.modules.tiku.service.impl;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.common.utils.SystemConstant;
import com.tamguo.config.redis.CacheService;
import com.tamguo.modules.tiku.dao.MenuMapper;
import com.tamguo.modules.tiku.model.MenuEntity;
import com.tamguo.modules.tiku.service.IMenuService;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements IMenuService{
	
	@Autowired
	private MenuMapper menuMapper;
	@Autowired
	private CacheService cacheService;

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuEntity> findMenus() {
		List<MenuEntity> menuList = ((List<MenuEntity>) cacheService.getObject(SystemConstant.INDEX_MENU));
		if (menuList == null) {
			menuList = menuMapper.selectList(Condition.create().eq("parent_id", 1).eq("is_show", 1).orderDesc(Arrays.asList("orders")));
			for(MenuEntity menu : menuList){
				List<MenuEntity> childSubjects = menuMapper.selectList(Condition.create().eq("parent_id", menu.getId()).orderDesc(Arrays.asList("orders")));
				menu.setChildSubjects(childSubjects);
			}
			cacheService.setObject(SystemConstant.INDEX_MENU, menuList , 2 * 60 * 60);
		}
		return menuList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuEntity> findAllMenus() {
		List<MenuEntity> allMenuList = ((List<MenuEntity>) cacheService.getObject(SystemConstant.ALL_INDEX_MENU));
		if(allMenuList == null){
			allMenuList = menuMapper.selectList(Condition.create().eq("parent_id", 1).orderDesc(Arrays.asList("orders")));
			for(MenuEntity menu : allMenuList){
				List<MenuEntity> childSubjects = menuMapper.selectList(Condition.create().eq("parent_id", menu.getId()).orderDesc(Arrays.asList("orders")));
				menu.setChildSubjects(childSubjects);
			}
			cacheService.setObject(SystemConstant.ALL_INDEX_MENU, allMenuList , 2 * 60 * 60);
		}
		return allMenuList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuEntity> findLeftMenus() {
		List<MenuEntity> leftMenuList = ((List<MenuEntity>) cacheService.getObject(SystemConstant.LEFT_INDEX_MENU));
		if(leftMenuList == null){
			leftMenuList = menuMapper.selectList(Condition.create().eq("parent_id", 2).orderDesc(Arrays.asList("orders")));
			for(MenuEntity menu : leftMenuList){
				List<MenuEntity> childSubjects = menuMapper.selectList(Condition.create().eq("parent_id", menu.getId()).orderDesc(Arrays.asList("orders")));
				menu.setChildSubjects(childSubjects);
			}
			cacheService.setObject(SystemConstant.LEFT_INDEX_MENU, leftMenuList , 2 * 60 * 60);
		}
		return leftMenuList;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuEntity> findChapterMenus() {
		List<MenuEntity> chapterMenuList = ((List<MenuEntity>) cacheService.getObject(SystemConstant.CHAPTER_INDEX_MENU));
		if(chapterMenuList == null){
			chapterMenuList = menuMapper.selectList(Condition.create().eq("parent_id", 4).orderAsc(Arrays.asList("orders")));
			for(MenuEntity menu : chapterMenuList){
				List<MenuEntity> childSubjects = menuMapper.selectList(Condition.create().eq("parent_id", menu.getId()).orderDesc(Arrays.asList("orders")));
				menu.setChildSubjects(childSubjects);
			}
			cacheService.setObject(SystemConstant.CHAPTER_INDEX_MENU, chapterMenuList , 2 * 60 * 60);
		}
		return chapterMenuList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<MenuEntity> findFooterMenus() {
		List<MenuEntity> footerMenuList = ((List<MenuEntity>) cacheService.getObject(SystemConstant.FOOTER_INDEX_MENU));
		if(footerMenuList == null){
			footerMenuList = menuMapper.selectList(Condition.create().eq("parent_id", 3).orderDesc(Arrays.asList("orders")));
			for(MenuEntity menu : footerMenuList){
				List<MenuEntity> childSubjects = menuMapper.selectList(Condition.create().eq("parent_id", menu.getId()).orderDesc(Arrays.asList("orders")));
				menu.setChildSubjects(childSubjects);
			}
			cacheService.setObject(SystemConstant.FOOTER_INDEX_MENU, footerMenuList , 2 * 60 * 60);
		}
		return footerMenuList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuEntity> getMenuTree() {
		return menuMapper.selectList(Condition.EMPTY);
	}

	@Override
	public MenuEntity findById(String uid) {
		return menuMapper.selectById(uid);
	}

}
