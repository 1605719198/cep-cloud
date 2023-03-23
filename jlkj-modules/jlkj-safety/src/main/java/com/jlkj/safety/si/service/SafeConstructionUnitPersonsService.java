package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiConstructionUnitPersons;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 施工单位-施工人员 服务类
 * </p>
 *
 * @author su
 * @since 2021-01-06
 */
public interface SafeConstructionUnitPersonsService extends IService<SafeSiConstructionUnitPersons> {

    /**
     * 施工人员-查询
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeConstructionUnitPersonsPageList(Map<String, Object> params);

    /**
     * 施工人员-新增
     * @param params 请求参数
     * @return 结果
     */
    public Object insertSafeConstructionUnitPersons(Map<String, Object> params);

    /**
     * 施工人员-修改
     * @param params 请求参数
     * @return 结果
     */
    public Object updateSafeConstructionUnitPersons(Map<String, Object> params);

    /**
     * 施工人员-删除
     * @param params 请求参数
     * @return 结果
     */
    public Object deleteSafeConstructionUnitPersons(Map<String, Object> params);

    /**
     * 施工人员-验证是否存在
     * @param params 请求参数
     * @return 结果
     */
    public boolean existsConstructionUnitPersonsById(Map<String, Object> params);

    /**
     * 施工人员-更新施工单位
     * @param params 请求参数
     * @return 结果
     */
    public boolean updateSafeConstructionUnitPersonsConstructionUnit(Map<String, Object> params);
}
