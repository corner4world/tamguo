package com.tamguo.modules.sys.service;

import com.tamguo.common.utils.Result;
import com.tamguo.modules.sys.model.TeacherEntity;

public interface ITeacherService {

	/**
	 * 获取教师档案
	 * @param mobile
	 * @param verifyCode
	 * @return
	 */
	Result getTeacherByMobile(String mobile , String verifyCode);
	
	/**
	 * 加入我们
	 * @param teacher
	 * @return
	 */
	void joinus(TeacherEntity teacher);
	
}
