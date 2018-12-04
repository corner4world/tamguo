package com.tamguo.modules.sys.service;

import java.math.BigDecimal;

import com.aliyuncs.exceptions.ClientException;
import com.tamguo.common.utils.Result;

public interface ISmsService {

	public Result sendFindPasswordSms(String mobile) throws ClientException;
	
	public Result sendRewardSms(String mobile ,String name , String bookName , Integer point, BigDecimal money) throws ClientException;
	
}
