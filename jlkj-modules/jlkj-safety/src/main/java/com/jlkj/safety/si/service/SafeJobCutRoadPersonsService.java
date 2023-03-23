package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiJobCutRoadPersons;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 断路安全作业证-作业人员 服务类
 * </p>
 *
 * @author su
 * @since 2021-02-01
 */
public interface SafeJobCutRoadPersonsService extends IService<SafeSiJobCutRoadPersons> {

    /**
     * 断路安全作业证-新增作业人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobCutRoadPersons(Map<String, Object> params);

    /**
     * 断路安全作业证-删除作业人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean deleteSafeJobCutRoadPersons(Map<String, Object> params);
}
