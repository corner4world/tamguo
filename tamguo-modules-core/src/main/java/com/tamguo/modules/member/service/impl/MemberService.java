package com.tamguo.modules.member.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.common.utils.Result;
import com.tamguo.common.utils.SystemConstant;
import com.tamguo.config.redis.CacheService;
import com.tamguo.modules.book.model.BookEntity;
import com.tamguo.modules.book.service.IBookService;
import com.tamguo.modules.member.dao.MemberMapper;
import com.tamguo.modules.member.model.MemberEntity;
import com.tamguo.modules.member.model.condition.MemberCondition;
import com.tamguo.modules.member.service.IMemberService;
import com.tamguo.modules.sys.service.ISmsService;

@Service
public class MemberService extends ServiceImpl<MemberMapper, MemberEntity> implements IMemberService{
	
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private ISmsService iSmsService;
	@Autowired
	private IBookService iBookService;

	@Override
	public Result login(String username, String password) {
		MemberEntity condition = new MemberEntity();
		condition.setUsername(username);
		MemberEntity member = memberMapper.selectOne(condition);
		if(member == null){
			return Result.result(201, member, "用户名或密码有误，请重新输入或找回密码");
		}
		Integer loginFailureCount = this.getLoginFailureCount(member);		
		if(!new Sha256Hash(password).toHex().equals(member.getPassword())){
			loginFailureCount++;
			this.updateLoginFailureCount(member , loginFailureCount);
			return Result.result(202, member, "用户名或密码有误，请重新输入或找回密码");
		}
		this.updateLoginFailureCount(member , 0);
		return Result.result(200, member, "登录成功");
	}
	
	public void updateLoginFailureCount(MemberEntity member , Integer loginFailureCount){
		cacheService.setObject(SystemConstant.LOGIN_FAILURE_COUNT + member.getId(),  loginFailureCount , 2 * 60 * 60);
	}
	
	public Integer getLoginFailureCount(MemberEntity member){
		if(member == null){
			return 0;
		}
		if(!cacheService.isExist(SystemConstant.LOGIN_FAILURE_COUNT + member.getId())){
			return 0;
		}
		return (Integer)cacheService.getObject(SystemConstant.LOGIN_FAILURE_COUNT + member.getId());
	}

	@Override
	public Result checkUsername(String username) {
		MemberEntity condition = new MemberEntity();
		condition.setUsername(username);
		MemberEntity member = memberMapper.selectOne(condition);
		if(member != null){
			return Result.result(201, null, "该用户名已经存在");
		}
		return Result.result(200, null, "该用户名可用");
	}

	@Override
	public Result checkMobile(String mobile) {
		MemberEntity condition = new MemberEntity();
		condition.setMobile(mobile);
		MemberEntity member = memberMapper.selectOne(condition);
		if(member != null){
			return Result.result(201, null, "该手机号已经存在");
		}
		return Result.result(200, null, "该手机号可用");
	}

	@Transactional(readOnly=false)
	@Override
	public Result register(MemberEntity member) {
		MemberEntity condition = new MemberEntity();
		condition.setUsername(member.getUsername());
		MemberEntity m = memberMapper.selectOne(condition);
		if(m != null){
			return Result.result(201, null, "该用户已经存在");
		}
		condition = new MemberEntity();
		condition.setMobile(member.getMobile());
		m = memberMapper.selectOne(condition);
		if(m != null){
			return Result.result(202, null, "该手机号已经存在");
		}
		if(!cacheService.isExist(SystemConstant.ALIYUN_MOBILE_SMS_PREFIX + member.getMobile())){
			return Result.result(203, null, "验证码错误");
		}
		String code = (String) cacheService.getObject(SystemConstant.ALIYUN_MOBILE_SMS_PREFIX + member.getMobile());
		if(!code.equals(member.getVerifyCode())){
			return Result.result(204, null, "验证码错误");
		}
		MemberEntity entity = new MemberEntity();
		entity.setAvatar(SystemConstant.DEFAULT_MEMBER_AVATAR);
		entity.setMobile(member.getMobile());
		entity.setPassword(new Sha256Hash(member.getPassword()).toHex());
		entity.setUsername(member.getUsername());
		entity.setNickName(member.getUsername());
		entity.setEmail(member.getEmail());
		memberMapper.insert(entity);
		return Result.result(200, entity, "注册成功");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result checkAccount(String account) {
		if(StringUtils.isEmpty(account)){
			return Result.result(201, null, "帐号不存在！");
		}
		List<MemberEntity> members = memberMapper.selectList(Condition.create().eq("user_name", account).or().eq("mobile", account));
		if(members.size() == 0){
			return Result.result(201, null, "帐号不存在！");
		}
		return Result.result(200, members.get(0), "该帐号存在");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result confirmAccount(String account, String veritycode) {
		if(StringUtils.isEmpty(account)){
			return Result.result(201, null, "帐号不存在！");
		}
		
		List<MemberEntity> members = memberMapper.selectList(Condition.create().eq("username", account).or().eq("mobile", account));
		if(members.size() == 0){
			return Result.result(201, null, "帐号不存在！");
		}
		return Result.result(200, members.get(0), "该帐号存在");
	}

	@Override
	public Result securityCheck(String username , String isEmail , String vcode) {
		MemberEntity condition = new MemberEntity();
		condition.setUsername(username);
		MemberEntity member = memberMapper.selectOne(condition);
		if("1".equals(isEmail)){
			if(!cacheService.isExist(SystemConstant.ALIYUN_MAIL_FIND_PASSWORD_PREFIX + member.getEmail())){
				return Result.result(201, member, "验证码错误");
			}
			String code = (String) cacheService.getObject(SystemConstant.ALIYUN_MAIL_FIND_PASSWORD_PREFIX + member.getEmail());
			if(!code.equals(vcode)){
				return Result.result(202, member, "验证码错误");
			}
		}else{
			if(!cacheService.isExist(SystemConstant.ALIYUN_MOBILE_SMS_PREFIX + member.getMobile())){
				return Result.result(203, member, "验证码错误");
			}
			String code = (String) cacheService.getObject(SystemConstant.ALIYUN_MOBILE_SMS_PREFIX + member.getMobile());
			if(!code.equals(vcode)){
				return Result.result(204, member, "验证码错误");
			}
		}
		String key = UUID.randomUUID().toString();
		cacheService.setObject(SystemConstant.SECURITY_CHECK_PREFIX + key,  username , 2 * 60 * 60);
		return Result.result(200, key, "安全验证通过");
	}

	@Override
	public Result resetPassword(String resetPasswordKey , String username , String password, String verifypwd) {
		if(cacheService.isExist(SystemConstant.SECURITY_CHECK_PREFIX + resetPasswordKey)){
			MemberEntity condition = new MemberEntity();
			condition.setUsername(username);
			MemberEntity member = memberMapper.selectOne(condition);
			if(password.equals(verifypwd)){
				member.setPassword(new Sha256Hash(password).toHex());
				memberMapper.updateById(member);
			}
		}
		return Result.result(200, null, "更新成功");
	}

	@Transactional(readOnly=false)
	@Override
	public void updateMember(MemberEntity member) {
		MemberEntity entity = memberMapper.selectById(member.getId());
		entity.setAvatar(member.getAvatar());
		entity.setEmail(member.getEmail());
		entity.setMobile(member.getMobile());
		entity.setNickName(member.getNickName());
		
		memberMapper.updateById(entity);
	}

	@Transactional(readOnly=true)
	@Override
	public MemberEntity findByUid(String uid) {
		return memberMapper.selectById(uid);
	}

	@Transactional(readOnly=true)
	@Override
	public MemberEntity findByUsername(String username) {
		MemberEntity condition = new MemberEntity();
		condition.setUsername(username);
		return memberMapper.selectOne(condition);
	}

	@Transactional(readOnly=false)
	@Override
	public void updateLastLoginTime(String uid) {
		MemberEntity member = memberMapper.selectById(uid);
		member.setLastLoginTime(new Date());
		memberMapper.updateById(member);
	}

	@Override
	public MemberEntity findCurrMember(String id) {
		MemberEntity member = memberMapper.selectById(id);
		member.setPassword(null);
		return member;
	}
	
	@Transactional(readOnly=false)
	@Override
	public Result updatePwd(MemberEntity member) {
		MemberEntity entity = memberMapper.selectById(member.getId());
		if(!entity.getPassword().equals(new Sha256Hash(member.getPassword()).toHex())) {
			return Result.result(501, null, "旧密码错误！");
		}
		if(!cacheService.isExist(SystemConstant.ALIYUN_MOBILE_SMS_PREFIX + member.getMobile())){
			return Result.result(502, null, "验证码错误");
		}
		entity.setPassword(new Sha256Hash(member.getNowPassword()).toHex());
		return Result.result(0, null, "修改成功");
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public Page<MemberEntity> listData(MemberCondition condition) {
		Page<MemberEntity> page = new Page<>(condition.getPageNo(), condition.getPageSize());
		Condition query = Condition.create();
		if(!StringUtils.isEmpty(condition.getMobile())) {
			query.eq("mobile", condition.getMobile());
		}
		if(!StringUtils.isEmpty(condition.getNickName())) {
			query.like("nick_name", condition.getNickName());
		}
		if(!StringUtils.isEmpty(condition.getUsername())) {
			query.eq("username", condition.getUsername());
		}
		return this.selectPage(page, query);
	}

	@Transactional(readOnly=false)
	@Override
	public void reward(String id ,String bookId , Integer rewardPoint, BigDecimal rewardMoney) {
		MemberEntity member = memberMapper.selectById(id);
		
		// 更新记录
		member.setPoint(member.getPoint() + rewardPoint);
		member.setAmount(member.getAmount().add(rewardMoney));
		this.updateById(member);
		
		BookEntity book = iBookService.selectById(bookId);
		
		// 发送短信
		try {
			iSmsService.sendRewardSms(member.getMobile(), member.getUsername(), book.getName(), rewardPoint, rewardMoney);
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}
	
}
