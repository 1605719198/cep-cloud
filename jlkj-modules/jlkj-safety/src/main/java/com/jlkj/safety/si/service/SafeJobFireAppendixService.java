package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeJobFireAppendix;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 动火作业票-附件表 服务类
 * </p>
 *
 * @author su
 * @since 2022-06-17
 */
public interface SafeJobFireAppendixService extends IService<SafeJobFireAppendix> {

    /**
     * 动火安全作业证-新增附件
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobFireFile(Map<String, Object> params);
}
