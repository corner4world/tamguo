package com.tamguo.modules.sys.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.tamguo.modules.sys.dao.SysUserDataScopeMapper;
import com.tamguo.modules.sys.dao.SysUserMapper;
import com.tamguo.modules.sys.dao.SysUserPostMapper;
import com.tamguo.modules.sys.dao.SysUserRoleMapper;
import com.tamguo.modules.sys.model.SysUserDataScopeEntity;
import com.tamguo.modules.sys.model.SysUserEntity;
import com.tamguo.modules.sys.model.SysUserPostEntity;
import com.tamguo.modules.sys.model.SysUserRoleEntity;
import com.tamguo.modules.sys.model.condition.SysUserCondition;
import com.tamguo.modules.sys.model.enums.SysUserMgrTypeEnum;
import com.tamguo.modules.sys.model.enums.SysUserStatusEnum;
import com.tamguo.modules.sys.model.enums.SysUserTypeEnum;
import com.tamguo.modules.sys.service.ISysRoleService;
import com.tamguo.modules.sys.service.ISysUserService;
import com.tamguo.modules.sys.utils.Result;
import com.tamguo.modules.sys.utils.ShiroUtils;
import com.tamguo.modules.sys.utils.TamguoConstant;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements ISysUserService{
	
	@Autowired
	public SysUserMapper sysUserMapper;
	@Autowired
	public SysUserPostMapper sysUserPostMapper;
	@Autowired
	public SysUserRoleMapper sysUserRoleMapper;
	@Autowired
	public ISysRoleService iSysRoleService;
	@Autowired
	public SysUserDataScopeMapper sysUserDataScopeMapper;
	
	@Transactional(readOnly=false)
	@Override
	public SysUserEntity queryByLoginCode(String loginCode) {
		SysUserEntity condition = new SysUserEntity();
		condition.setLoginCode(loginCode);
		return sysUserMapper.selectOne(condition);
	}

	@Transactional(readOnly=false)
	@Override
	public Page<SysUserEntity> listData(SysUserCondition condition) {
		Page<SysUserEntity> page = new Page<>(condition.getPageNo() , condition.getPageSize());
		return page.setRecords(sysUserMapper.listData(condition , page));
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryUserPostByUserCode(String userCode) {
		Condition condition = Condition.create();
		condition.eq("user_code", userCode);
		List<SysUserPostEntity> userPostList = sysUserPostMapper.selectList(condition);
		
		List<String> postCodes  = new LinkedList<>();
		for(int i=0 ; i<userPostList.size() ; i++) {
			postCodes.add(userPostList.get(i).getPostCode());
		}
		return StringUtils.join(postCodes, ",");
	}

	@Transactional(readOnly=false)
	@SuppressWarnings("unchecked")
	@Override
	public Boolean checkLoginCode(String oldLoginCode, String loginCode) {
		Condition condition = Condition.create();
		condition.eq("login_code", loginCode);
		condition.ne("login_code", oldLoginCode);
		List<SysUserEntity> userList = sysUserMapper.selectList(condition);
		return CollectionUtils.isEmpty(userList);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=false)
	@Override
	public void update(SysUserEntity user) {
		SysUserEntity entity = sysUserMapper.selectById(user.getUserCode());
		entity.setOfficeCode(user.getOfficeCode());
		entity.setOfficeName(user.getOfficeName());
		entity.setCompanyCode(user.getCompanyCode());
		entity.setCompanyName(user.getCompanyName());
		entity.setLoginCode(user.getLoginCode());
		entity.setUserName(user.getUserName());
		entity.setEmail(user.getEmail());
		entity.setMobile(user.getMobile());
		entity.setPhone(user.getPhone());
		entity.setUserWeight(user.getUserWeight());
		entity.setRefName(user.getRefName());
		entity.setUserNameEn(user.getUserNameEn());
		entity.setRemarks(user.getRemarks());
		sysUserMapper.updateById(entity);

		// 删除记录
		sysUserPostMapper.delete(Condition.create().eq("user_code", user.getUserCode()));
		// 处理岗位
		List<String> employeePosts = user.getEmployeePosts();
		for(int i=0 ; i<employeePosts.size() ; i++) {
			SysUserPostEntity userPost = new SysUserPostEntity();
			userPost.setPostCode(employeePosts.get(i));
			userPost.setUserCode(user.getUserCode());
			sysUserPostMapper.insert(userPost);
		}
	}

	@Transactional(readOnly=false)
	@Override
	public void save(SysUserEntity user) {
		user.setCreateBy(ShiroUtils.getUserCode());
		user.setCreateDate(new Date());
		user.setStatus(SysUserStatusEnum.NORMAL);
		// 设置初始密码
		user.setPassword(TamguoConstant.INIT_PASSWORD);
		user.setUserType(SysUserTypeEnum.EMPLOYEE);
		user.setMgrType(SysUserMgrTypeEnum.NONE_ADMIN);
		user.setUpdateBy(ShiroUtils.getUserCode());
		user.setUpdateDate(new Date());
		sysUserMapper.insert(user);
		
		// 处理岗位
		List<String> employeePosts = user.getEmployeePosts();
		for(int i=0 ; i<employeePosts.size() ; i++) {
			SysUserPostEntity userPost = new SysUserPostEntity();
			userPost.setPostCode(employeePosts.get(i));
			userPost.setUserCode(user.getUserCode());
			sysUserPostMapper.insert(userPost);
		}
		
		// 处理角色
		if(!StringUtils.isEmpty(user.getUserRoleString())) {
			String[] roleCodes = user.getUserRoleString().split(",");
			for(String roleCode : roleCodes) {
				SysUserRoleEntity role = new SysUserRoleEntity();
				role.setRoleCode(roleCode);
				role.setUserCode(user.getUserCode());
				sysUserRoleMapper.insert(role);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=false)
	@Override
	public void allowUserRole(SysUserEntity user) {
		// 删除角色记录
		sysUserRoleMapper.delete(Condition.create().eq("user_code", user.getUserCode()));
		
		// 处理角色
		if(!StringUtils.isEmpty(user.getUserRoleString())) {
			String[] roleCodes = user.getUserRoleString().split(",");
			for(String roleCode : roleCodes) {
				SysUserRoleEntity role = new SysUserRoleEntity();
				role.setRoleCode(roleCode);
				role.setUserCode(user.getUserCode());
				sysUserRoleMapper.insert(role);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<SysUserRoleEntity> findUserRole(String userCode) {
		return sysUserRoleMapper.selectList(Condition.create().eq("user_code", userCode));
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<SysUserDataScopeEntity> selectUserDataScope(String userCode) {
		return sysUserDataScopeMapper.selectList(Condition.create().eq("user_code", userCode));
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=false)
	@Override
	public void saveUserDataScope(SysUserEntity user) {
		// 删除之前的数据权限
		sysUserDataScopeMapper.delete(Condition.create().eq("user_code", user.getUserCode()));
		
		if(!StringUtils.isEmpty(user.getUserDataScopeListJson())) {
			JSONArray dataScopeList = JSONArray.parseArray(user.getUserDataScopeListJson());
			for(int i=0 ; i<dataScopeList.size() ; i++) {
				SysUserDataScopeEntity entity = new SysUserDataScopeEntity();
				entity.setCtrlData(dataScopeList.getJSONObject(i).getString("ctrlData"));
				entity.setCtrlType(dataScopeList.getJSONObject(i).getString("ctrlType"));
				entity.setUserCode(user.getUserCode());
				entity.setCtrlPermi("2");
				sysUserDataScopeMapper.insert(entity);
			}
		}
	}

	@Transactional(readOnly=false)
	@Override
	public Result disable(String userCode) {
		SysUserEntity user = sysUserMapper.selectById(userCode);
		user.setStatus(SysUserStatusEnum.DISABLED);
		sysUserMapper.updateById(user);
		return Result.result(0, null, "停用成功！");
	}

	@Transactional(readOnly=false)
	@Override
	public Result enable(String userCode) {
		SysUserEntity user = sysUserMapper.selectById(userCode);
		user.setStatus(SysUserStatusEnum.NORMAL);
		sysUserMapper.updateById(user);
		return Result.result(0, null, "激活成功！");
	}

	@Transactional(readOnly=false)
	@Override
	public Result delete(String userCode) {
		SysUserEntity user = sysUserMapper.selectById(userCode);
		user.setStatus(SysUserStatusEnum.DELETE);
		sysUserMapper.updateById(user);
		return Result.result(0, null, "删除成功！");
	}

	@Transactional(readOnly=false)
	@Override
	public void saveUserDataScope(SysUserEntity user, SysUserMgrTypeEnum mgrType) {
		this.saveUserDataScope(user);
		
		SysUserEntity entity = sysUserMapper.selectById(user.getUserCode());
		entity.setMgrType(mgrType);
		sysUserMapper.updateById(entity);
	}

}
