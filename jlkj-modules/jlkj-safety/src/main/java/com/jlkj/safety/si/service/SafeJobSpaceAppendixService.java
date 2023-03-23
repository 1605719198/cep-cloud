package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeJobSpaceAppendix;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 有限空间作业票-附件表 服务类
 * </p>
 *
 * @author su
 * @since 2022-06-15
 */
public interface SafeJobSpaceAppendixService extends IService<SafeJobSpaceAppendix> {

    /**
     * 受限空间安全作业证-新增附件
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobSpaceFile(Map<String, Object> params);
}
