package com.jlkj.safety.si.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.safety.si.entity.SafeJobHoistingAppendix;

import java.util.Map;

/**
 * <p>
 * 吊装作业票-附件表 服务类
 * </p>
 *
 * @author su
 * @since 2022-06-14
 */
public interface SafeJobHoistingAppendixService extends IService<SafeJobHoistingAppendix> {

    /**
     * 吊装安全作业证-新增附件
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobHoistingFile(Map<String, Object> params);

}
