package com.tamguo.modules.sys.service;

import com.aliyuncs.exceptions.ClientException;
import com.tamguo.common.utils.Result;

public interface ISmsService {

	public Result sendFindPasswordSms(String mobile) throws ClientException;
	
}
