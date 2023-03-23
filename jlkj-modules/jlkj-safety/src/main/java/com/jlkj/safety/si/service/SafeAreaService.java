package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiArea;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 安全区域 服务类
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
public interface SafeAreaService extends IService<SafeSiArea> {

    /**
     * 安全区域-查询
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeAreaPageList(Map<String, Object> params);

    /**
     * 安全区域-新增
     * @param params 请求参数
     * @return 结果
     */
    public Object insertSafeArea(Map<String, Object> params);

    /**
     * 安全区域-修改
     * @param params 请求参数
     * @return 结果
     */
    public Object updateSafeArea(Map<String, Object> params);

    /**
     * 安全区域-删除
     * @param params 请求参数
     * @return 结果
     */
    public Object deleteSafeArea(Map<String, Object> params);

}
