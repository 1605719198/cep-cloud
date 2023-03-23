package com.jlkj.safety.si.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.safety.si.entity.SafeSiJobHoistingPersons;

import java.util.Map;

/**
 * <p>
 * 吊装安全作业证-作业人员 服务类
 * </p>
 *
 * @author su
 * @since 2021-02-01
 */
public interface SafeJobHoistingPersonsService extends IService<SafeSiJobHoistingPersons> {

    /**
     * 吊装安全作业证-新增作业人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobHoistingPersons(Map<String, Object> params);

    /**
     * 吊装安全作业证-删除作业人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean deleteSafeJobHoistingPersons(Map<String, Object> params);
}
