package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiDangerType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 隐患等级 服务类
 * </p>
 *
 * @author su
 * @since 2021-01-07
 */
public interface SafeDangerTypeService extends IService<SafeSiDangerType> {

    /**
     * 隐患等级-查询
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeDangerTypePageList(Map<String, Object> params);

    /**
     * 隐患等级-新增
     * @param params 请求参数
     * @return 结果
     */
    public Object insertSafeDangerType(Map<String, Object> params);

    /**
     * 隐患等级-修改
     * @param params 请求参数
     * @return 结果
     */
    public Object updateSafeDangerType(Map<String, Object> params);

    /**
     * 隐患等级-删除
     * @param params 请求参数
     * @return 结果
     */
    public Object deleteSafeDangerType(Map<String, Object> params);

}
