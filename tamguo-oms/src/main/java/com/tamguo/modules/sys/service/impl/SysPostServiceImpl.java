package com.tamguo.modules.sys.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.modules.sys.dao.SysPostMapper;
import com.tamguo.modules.sys.model.SysPostEntity;
import com.tamguo.modules.sys.service.ISysPostService;

@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPostEntity> implements ISysPostService{

}
