package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiJobHighPersons;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 高处安全作业证-作业人员 服务类
 * </p>
 *
 * @author su
 * @since 2021-02-01
 */
public interface SafeJobHighPersonsService extends IService<SafeSiJobHighPersons> {

    /**
     * 高处安全作业证-新增作业人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobHighPersons(Map<String, Object> params);

    /**
     * 高处安全作业证-删除作业人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean deleteSafeJobHighPersons(Map<String, Object> params);
}
