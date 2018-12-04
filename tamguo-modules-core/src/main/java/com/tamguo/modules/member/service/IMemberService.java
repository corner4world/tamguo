package com.tamguo.modules.member.service;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.tamguo.common.utils.Result;
import com.tamguo.modules.member.model.MemberEntity;
import com.tamguo.modules.member.model.condition.MemberCondition;

public interface IMemberService extends IService<MemberEntity>{

	/** 登录*/
	public Result login(String username , String password);
	
	/** 检查用户名*/
	public Result checkUsername(String username);

	/** 检查手机号*/
	public Result checkMobile(String mobile);
	
	/** 注册*/
	public Result register(MemberEntity member);

	/** 检查账号*/
	public Result checkAccount(String account);
	
	/** 确认账号*/
	public Result confirmAccount(String account , String veritycode);

	/** 安全检查*/
	public Result securityCheck(String username , String isEmail , String vcode);

	/** 重置密码*/
	public Result resetPassword(String resetPasswordKey , String username , String password, String verifypwd);
	
	/** 获得登录错误次数*/
	public Integer getLoginFailureCount(MemberEntity member);
	
	/** 修改会员*/
	public void updateMember(MemberEntity member);
	
	/** 获取会员信息*/
	public MemberEntity findByUid(String uid);
	
	/** 获取用户*/
	public MemberEntity findByUsername(String username);
	
	/** 更新错误次数*/
	public void updateLoginFailureCount(MemberEntity member , Integer loginFailureCount);

	/** 更新最后登录时间*/
	public void updateLastLoginTime(String string);

	/** 获取当前用户*/
	public MemberEntity findCurrMember(String id);

	/** 修改密码 */
	public Result updatePwd(MemberEntity member);

	/** 公司列表*/
	public Page<MemberEntity> listData(MemberCondition condition);

	/** 奖励*/
	public void reward(String id , String bookId , Integer rewardPoint, BigDecimal rewardMoney);
	
}
