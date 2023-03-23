package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiJobTemporaryElectricityPersons;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 临时用电安全作业证-作业人员 服务类
 * </p>
 *
 * @author su
 * @since 2021-02-01
 */
public interface SafeJobTemporaryElectricityPersonsService extends IService<SafeSiJobTemporaryElectricityPersons> {

    /**
     * 临时用电安全作业证-新增作业人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobTemporaryElectricityPersons(Map<String, Object> params);

    /**
     * 临时用电安全作业证-删除作业人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean deleteSafeJobTemporaryElectricityPersons(Map<String, Object> params);
}
