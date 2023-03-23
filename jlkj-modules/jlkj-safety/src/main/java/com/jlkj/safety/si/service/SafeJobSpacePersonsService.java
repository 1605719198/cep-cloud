package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiJobSpacePersons;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 受限空间安全作业证-作业人员 服务类
 * </p>
 *
 * @author su
 * @since 2021-01-26
 */
public interface SafeJobSpacePersonsService extends IService<SafeSiJobSpacePersons> {

    /**
     * 受限空间安全作业证-新增作业人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobSpacePersons(Map<String, Object> params);

    /**
     * 受限空间安全作业证-删除作业人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean deleteSafeJobSpacePersons(Map<String, Object> params);
}
