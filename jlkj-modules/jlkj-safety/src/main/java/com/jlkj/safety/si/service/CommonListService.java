package com.jlkj.safety.si.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.safety.si.entity.Empty;

import java.util.Map;

/**
 * <p>
 * 公共-数据列表 服务类
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
public interface CommonListService extends IService<Empty> {

    /**
     * 外委单位人员下拉列表
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeConstructionUnitPersonList(Map<String, Object> params);
}
