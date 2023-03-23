package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiJobSoilPersons;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 动土安全作业证-作业人员 服务类
 * </p>
 *
 * @author su
 * @since 2021-02-01
 */
public interface SafeJobSoilPersonsService extends IService<SafeSiJobSoilPersons> {

    /**
     * 动土安全作业证-新增作业人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobSoilPersons(Map<String, Object> params);

    /**
     * 动土安全作业证-删除作业人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean deleteSafeJobSoilPersons(Map<String, Object> params);
}
