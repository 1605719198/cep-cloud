package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiWorkshopAccSection;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 车间明细台账-工段 服务类
 * </p>
 *
 * @author su
 * @since 2021-06-04
 */
public interface SafeWorkshopAccSectionService extends IService<SafeSiWorkshopAccSection> {

    /**
     * 车间明细台账-工段-查询
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeWorkshopAccSectionPageList(Map<String, Object> params);

    /**
     * 车间明细台账-工段-新增
     * @param params 请求参数
     * @return 结果
     */
    public Object insertSafeWorkshopAccSection(Map<String, Object> params);

    /**
     * 车间明细台账-工段-修改
     * @param params 请求参数
     * @return 结果
     */
    public Object updateSafeWorkshopAccSection(Map<String, Object> params);

    /**
     * 车间明细台账-工段-删除
     * @param params 请求参数
     * @return 结果
     */
    public Object deleteSafeWorkshopAccSection(Map<String, Object> params);
}
