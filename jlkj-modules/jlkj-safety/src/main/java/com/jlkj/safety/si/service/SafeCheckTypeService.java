package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiCheckType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 安全巡检类型 服务类
 * </p>
 *
 * @author su
 * @since 2021-01-07
 */
public interface SafeCheckTypeService extends IService<SafeSiCheckType> {

    /**
     * 安全巡检类型-查询
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeCheckTypePageList(Map<String, Object> params);

    /**
     * 安全巡检类型-新增
     * @param params 请求参数
     * @return 结果
     */
    public Object insertSafeCheckType(Map<String, Object> params);

    /**
     * 安全巡检类型-修改
     * @param params 请求参数
     * @return 结果
     */
    public Object updateSafeCheckType(Map<String, Object> params);

    /**
     * 安全巡检类型-删除
     * @param params 请求参数
     * @return 结果
     */
    public Object deleteSafeCheckType(Map<String, Object> params);

}
