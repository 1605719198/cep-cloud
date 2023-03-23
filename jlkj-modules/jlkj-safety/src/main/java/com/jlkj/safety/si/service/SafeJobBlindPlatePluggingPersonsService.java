package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiJobBlindPlatePluggingPersons;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 盲板抽堵安全作业证-作业人员 服务类
 * </p>
 *
 * @author su
 * @since 2021-02-01
 */
public interface SafeJobBlindPlatePluggingPersonsService extends IService<SafeSiJobBlindPlatePluggingPersons> {

    /**
     * 盲板抽堵安全作业证-新增作业人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobBlindPlatePluggingPersons(Map<String, Object> params);

    /**
     * 盲板抽堵安全作业证-删除作业人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean deleteSafeJobBlindPlatePluggingPersons(Map<String, Object> params);
}
