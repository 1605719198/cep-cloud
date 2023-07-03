package com.jlkj.common.core.config;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.jlkj.common.core.constant.Constants;
import com.jlkj.common.core.context.SecurityContextHolder;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.Objects;

/**
 * Mybatis Plus 配置
 *
 * @author XIN
 * @date 2022/4/27 - 9:39
 */
@Configuration
public class MyBatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件
        interceptor.addInnerInterceptor(paginationInnerInterceptor());
        // 乐观锁插件
        interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor());
        // 阻断插件
        interceptor.addInnerInterceptor(blockAttackInnerInterceptor());
        return interceptor;
    }

    /**
     * 分页插件
     *
     * @return
     */
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // 设置数据库类型为mysql
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        // 设置最大单页限制数量， 默认500条 , -1 不受限制
        paginationInnerInterceptor.setMaxLimit(-1L);
        return paginationInnerInterceptor;
    }

    /**
     * 乐观锁插件
     *
     * @return
     */
    public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }

    /**
     * 如果是对全表的删除或更新操作，就会终止该操作
     *
     * @return
     */
    public BlockAttackInnerInterceptor blockAttackInnerInterceptor() {
        return new BlockAttackInnerInterceptor();
    }

    /**
     * 自动填充功能
     */
    @Bean
    public MyMetaObjectHandler metaObjectHandler() {
        return new MyMetaObjectHandler();
    }

    @Slf4j
    public static class MyMetaObjectHandler implements MetaObjectHandler {
        @Override
        public void insertFill(MetaObject metaObject) {
            if (metaObject.getOriginalObject() instanceof BaseEntity) {
                this.strictInsertFill(metaObject, Constants.CREATE_TIME, Date.class, new Date(System.currentTimeMillis()));
                this.strictInsertFill(metaObject, Constants.CREATE_BY, String.class, StrUtil.nullToDefault(getUserName(), ""));
            }
        }

        @Override
        public void updateFill(MetaObject metaObject) {
            if (metaObject.getOriginalObject() instanceof BaseEntity) {
                this.setFieldValByName( Constants.UPDATE_TIME,  new Date(System.currentTimeMillis()),metaObject);
                this.setFieldValByName( Constants.UPDATE_BY,  StrUtil.nullToDefault(getUserName(), ""),metaObject);
            }
        }

        /**
         * @description 获取当前登录人姓名
         * @author: 111191
         * @date: 2023年7月3日, 0003 上午 08:30:46
         * @param:
         * @return: java.lang.String
         * @throws:
         */
        private String getUserName() {
            try {
                String user = SecurityContextHolder.getUserName();
                if (!Objects.isNull(user)) {
                    return user;
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            return null;
        }
    }
}
