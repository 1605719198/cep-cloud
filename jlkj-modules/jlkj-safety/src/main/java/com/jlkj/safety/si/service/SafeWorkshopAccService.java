package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiWorkshopAcc;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 车间明细台账 服务类
 * </p>
 *
 * @author su
 * @since 2021-05-20
 */
public interface SafeWorkshopAccService extends IService<SafeSiWorkshopAcc> {

    /**
     * 车间明细台账-查询
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeWorkshopAccPageList(Map<String, Object> params);

    /**
     * 车间明细台账-查询(工段)
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeWorkshopAccPageListByProcedure(Map<String, Object> params);

    /**
     * 车间明细台账-新增
     * @param params 请求参数
     * @return 结果
     */
    public Object insertSafeWorkshopAcc(Map<String, Object> params);

    /**
     * 车间明细台账-修改
     * @param params 请求参数
     * @return 结果
     */
    public Object updateSafeWorkshopAcc(Map<String, Object> params);

    /**
     * 车间明细台账-删除
     * @param params 请求参数
     * @return 结果
     */
    public Object deleteSafeWorkshopAcc(Map<String, Object> params);

    /**
     * 车间明细台账-批量删除
     * @param params 请求参数
     * @return 结果
     */
    public Object deleteSafeWorkshopAccs(Map<String, Object> params);

    /**
     * 车间明细台账-工段列表
     * @param params 请求参数
     * @return 结果列表
     */
    public Object getSafeworkshopAccSectionList(Map<String, Object> params);

    /**
     * 车间明细台账-报文-发送
     * @param params 请求参数
     * @return 结果
     */
    void sendSafeWorkshopAccMessageQueue(Map<String, Object> params);

    /**
     * 车间明细台账-详情
     * @param params 请求参数
     * @return 结果
     */
    public Object getSafeworkshopAccInfo(Map<String, Object> params);
}
