package com.tamguo.config.dao;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import com.tamguo.modules.sys.utils.ShiroUtils;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  注入公共字段自动填充,任选注入方式即可
 */
//@Component
public class MyMetaObjectHandler extends MetaObjectHandler {

    protected final static Logger logger = LoggerFactory.getLogger(MyMetaObjectHandler.class);
    
    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldValByName("createBy", ShiroUtils.getUserCode() , metaObject);
        setFieldValByName("updateBy", ShiroUtils.getUserCode() , metaObject);
        setFieldValByName("createDate", new Date() , metaObject);
        setFieldValByName("updateDate", new Date() , metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("updateBy", ShiroUtils.getUserCode() , metaObject);
        setFieldValByName("updateDate", new Date() , metaObject);
    }
}
