package com.jlkj.human.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.jlkj.common.security.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 智能研发室 黄兵
 * @date 2022/4/26 - 20:18
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入时的填充策略
     *
     * @param metaObject Clinton Begin
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // setFieldValByName(String fieldName, Object fieldVal, MetaObject metaObject
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("createDate", new Date(), metaObject);
        this.setFieldValByName("updateDate", new Date(), metaObject);
        this.setFieldValByName("createBy", SecurityUtils.getNickName(), metaObject);
        this.setFieldValByName("createNo", SecurityUtils.getUsername(), metaObject);
        this.setFieldValByName("createId", SecurityUtils.getUsername(), metaObject);
    }

    /**
     * 更新时的填充策略
     *
     * @param metaObject Clinton Begin
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateDate", new Date(), metaObject);
        this.setFieldValByName("createDate", new Date(), metaObject);
    }
}
