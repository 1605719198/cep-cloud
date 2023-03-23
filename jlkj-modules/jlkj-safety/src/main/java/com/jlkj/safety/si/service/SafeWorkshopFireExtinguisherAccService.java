package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeWorkshopFireExtinguisherAcc;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 灭火器台账 服务类
 * </p>
 *
 * @author su
 * @since 2021-05-20
 */
public interface SafeWorkshopFireExtinguisherAccService extends IService<SafeWorkshopFireExtinguisherAcc> {

    /**
     * 灭火器台账-查询
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeWorkshopFireExtinguisherAccPageList(Map<String, Object> params);

    /**
     * 灭火器台账-新增
     * @param params 请求参数
     * @return 结果
     */
    public Object insertSafeWorkshopFireExtinguisherAcc(Map<String, Object> params);

    /**
     * 灭火器台账-修改
     * @param params 请求参数
     * @return 结果
     */
    public Object updateSafeWorkshopFireExtinguisherAcc(Map<String, Object> params);

    /**
     * 灭火器台账-删除
     * @param params 请求参数
     * @return 结果
     */
    public Object deleteSafeWorkshopFireExtinguisherAcc(Map<String, Object> params);
}
