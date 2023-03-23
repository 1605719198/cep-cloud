package com.jlkj.safety.si.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.safety.si.entity.Empty;

import java.util.Map;

/**
 * <p>
 * 公共-下拉列表服务类
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
public interface CommonComboService extends IService<Empty> {

    /**
     * 安全区域下拉列表
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeAreaDropList(Map<String, Object> params);

    /**
     * 工种下拉列表
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeWorkTypeDropList(Map<String, Object> params);

    /**
     * 巡检类型下拉列表
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeCheckTypeDropList(Map<String, Object> params);

    /**
     * 隐患类型下拉列表
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeDangerTypeDropList(Map<String, Object> params);

    /**
     * 工段下拉下拉列表
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeWorkshopSectionDropList(Map<String, Object> params);


}
