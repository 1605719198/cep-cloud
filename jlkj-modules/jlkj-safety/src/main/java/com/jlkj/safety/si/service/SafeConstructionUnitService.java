package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiConstructionUnit;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 外委施工单位 服务类
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
public interface SafeConstructionUnitService extends IService<SafeSiConstructionUnit> {

    /**
     * 外委施工单位-查询
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeConstructionUnitPageList(Map<String, Object> params);

    /**
     * 外委施工单位-新增
     * @param params 请求参数
     * @return 结果
     */
    public Object insertSafeConstructionUnit(Map<String, Object> params);

    /**
     * 外委施工单位-修改
     * @param params 请求参数
     * @return 结果
     */
    public Object updateSafeConstructionUnit(Map<String, Object> params);

    /**
     * 外委施工单位-删除
     * @param params 请求参数
     * @return 结果
     */
    public Object deleteSafeConstructionUnit(Map<String, Object> params);

}
