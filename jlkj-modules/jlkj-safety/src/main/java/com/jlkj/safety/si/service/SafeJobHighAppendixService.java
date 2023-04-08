package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiJobHighAppendix;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 高处作业票-附件表 服务类
 * </p>
 *
 * @author su
 * @since 2022-06-14
 */
public interface SafeJobHighAppendixService extends IService<SafeSiJobHighAppendix> {

    /**
     * 高处安全作业证-新增附件
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobHighFile(Map<String, Object> params);
}
