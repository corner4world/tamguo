package com.tamguo.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.common.utils.DateUtil;
import com.tamguo.common.utils.Result;
import com.tamguo.common.utils.SystemConstant;
import com.tamguo.config.redis.CacheService;
import com.tamguo.modules.sys.dao.TeacherMapper;
import com.tamguo.modules.sys.model.TeacherEntity;
import com.tamguo.modules.sys.model.enums.TeacherStatus;
import com.tamguo.modules.sys.service.ITeacherService;

@Service
public class TeacherService extends ServiceImpl<TeacherMapper, TeacherEntity> implements ITeacherService {
	
	@Autowired
	TeacherMapper teacherMapper;
	@Autowired
	CacheService cacheService;

	@Transactional(readOnly=false)
	@Override
	public Result getTeacherByMobile(String mobile, String verifyCode) {
		// 校验短信验证码
		if(!cacheService.isExist(SystemConstant.ALIYUN_MOBILE_SMS_PREFIX + mobile)){
			return Result.result(201, null, "请发送验证码");
		}
		if(!((String)cacheService.getObject(SystemConstant.ALIYUN_MOBILE_SMS_PREFIX + mobile)).equals(verifyCode)) {
			return Result.result(202, null, "验证码错误");
		}
		TeacherEntity condition = new TeacherEntity();
		condition.setMobile(mobile);
		TeacherEntity teacher = teacherMapper.selectOne(condition);
		if(teacher == null) {
			return Result.result(203, null, "欢迎");
		}
		return Result.successResult(teacher);
	}

	@Transactional(readOnly=false)
	@Override
	public void joinus(TeacherEntity teacher) {
		teacher.setCreateTime(DateUtil.getTime());
		teacher.setStatus(TeacherStatus.APPLY);
		teacherMapper.insert(teacher);
	}

}
