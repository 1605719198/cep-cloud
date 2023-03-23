package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiJobBlindPlatePluggingSafetyMeasures;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *
    服务类
 * </p>
 *
 * @author su
 * @since 2021-02-07
 */
public interface SafeJobBlindPlatePluggingSafetyMeasuresService extends IService<SafeSiJobBlindPlatePluggingSafetyMeasures> {

    /**
     * 盲板抽堵安全作业证-安全措施确认
     * @param params 请求参数
     * @return 结果
     */
    public Object confirmSafeJobBlindPlatePluggingSafetyMeasures(Map<String, Object> params);

    /**
     * 盲板抽堵作业证-安全措施取消
     * @param params 请求参数
     * @return 结果
     */
    public Object cancelSafeJobBlindPlatePluggingSafetyMeasures(Map<String, Object> params);
}
