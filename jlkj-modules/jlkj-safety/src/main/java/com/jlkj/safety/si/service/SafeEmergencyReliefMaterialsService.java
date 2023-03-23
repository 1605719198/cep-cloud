package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeEmergencyReliefMaterials;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 气防站应急救援物资 服务类
 * </p>
 *
 * @author su
 * @since 2021-05-20
 */
public interface SafeEmergencyReliefMaterialsService extends IService<SafeEmergencyReliefMaterials> {

    /**
     * 气防站应急救援物资-查询列表
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeEmergencyReliefMaterialsPageList(Map<String, Object> params);

    /**
     * 气防站应急救援物资-新增
     * @param params 请求参数
     * @return 结果
     */
    public Object insertSafeEmergencyReliefMaterials(Map<String, Object> params);

    /**
     * 气防站应急救援物资-修改
     * @param params 请求参数
     * @return 结果
     */
    public Object updateSafeEmergencyReliefMaterials(Map<String, Object> params);

    /**
     * 气防站应急救援物资-删除
     * @param params 请求参数
     * @return 结果
     */
    public Object deleteSafeEmergencyReliefMaterials(Map<String, Object> params);
}
