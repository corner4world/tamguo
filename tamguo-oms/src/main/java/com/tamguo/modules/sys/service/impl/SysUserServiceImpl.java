package com.tamguo.modules.sys.service.impl;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.modules.sys.dao.SysUserMapper;
import com.tamguo.modules.sys.model.SysUserEntity;
import com.tamguo.modules.sys.service.ISysUserService;
import com.tamguo.modules.sys.utils.DateUtil;
import com.tamguo.modules.sys.utils.Result;
import com.tamguo.modules.sys.utils.ShaEncrypt;
import com.tamguo.modules.sys.utils.ShiroUtils;

@Service
public class SysUserServiceImpl implements ISysUserService{
	
	@Autowired
	public SysUserMapper sysUserMapper;

	@Override
	public SysUserEntity queryByUserName(String username) {
		return sysUserMapper.queryByUserName(username);
	}

	@Override
	public SysUserEntity queryByUid(String uid) {
		return sysUserMapper.selectById(uid);
	}

	@Override
	public void updateUserInfo(SysUserEntity user) {
		SysUserEntity entity = sysUserMapper.selectById(user.getUid());
		entity.setRoleIds(user.getRoleIds());
		entity.setNickName(user.getNickName());
		entity.setMobile(user.getMobile());
		entity.setEmail(user.getEmail());
		entity.setStatus(user.getStatus());
		sysUserMapper.updateById(entity);
	}

	@Transactional(readOnly=false)
	@Override
	public Result updatePassword(String oldPassword, String password, String repassword) {
		String userId = ShiroUtils.getUid();
		SysUserEntity user = sysUserMapper.selectById(userId);
		if(!new Sha256Hash(oldPassword).toHex().equals(user.getPassword())) {
			return Result.result(1, null, "旧密码错误");
		}
		if(!password.equals(repassword)) {
			return Result.result(2, null, "两次密码输入不一致");
		}
		user.setPassword(new Sha256Hash(password).toHex());
		sysUserMapper.updateById(user);
		return Result.successResult(null);
	}

	@Transactional(readOnly=true)
	@Override
	public Page<SysUserEntity> queryPage(String userName ,Page<SysUserEntity> page) {
		if(!StringUtils.isEmpty(userName)) {
			userName = "%" + userName + "%";
		}
		page.setRecords(sysUserMapper.queryPage(userName , page));
		return page;
	}

	@Override
	public SysUserEntity selectById(String userId) {
		return sysUserMapper.selectById(userId);
	}

	@Transactional(readOnly=false)
	@Override
	public void save(SysUserEntity user) {
		user.setCreateTime(DateUtil.getTime());
		// sha256加密
		user.setPassword(ShaEncrypt.SHA256(user.getPassword()));
		// 处理角色
		if(!CollectionUtils.isEmpty(user.getRoleIdList())) {
			user.setRoleIds(StringUtils.join(user.getRoleIdList(), ","));
		}
		sysUserMapper.insert(user);
	}

	@Override
	@Transactional(readOnly=false)
	public void update(SysUserEntity user) {
		if (StringUtils.isEmpty(user.getPassword())) {
			user.setPassword(null);
		} else {
			user.setPassword(ShaEncrypt.SHA256(user.getPassword()));
		}
		sysUserMapper.updateById(user);
	}

	@Transactional(readOnly=false)
	@Override
	public void deleteBatch(String[] userIds) {
		sysUserMapper.deleteBatchIds(Arrays.asList(userIds));
	}
}