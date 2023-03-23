package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiWorkType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 工种 服务类
 * </p>
 *
 * @author su
 * @since 2021-01-07
 */
public interface SafeWorkTypeService extends IService<SafeSiWorkType> {

    /**
     * 工种-查询
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeWorkTypePageList(Map<String, Object> params);

    /**
     * 工种-新增
     * @param params 请求参数
     * @return 结果
     */
    public Object insertSafeWorkType(Map<String, Object> params);

    /**
     * 工种-修改
     * @param params 请求参数
     * @return 结果
     */
    public Object updateSafeWorkType(Map<String, Object> params);

    /**
     * 工种-删除
     * @param params 请求参数
     * @return 结果
     */
    public Object deleteSafeWorkType(Map<String, Object> params);

}
