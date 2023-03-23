package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiJobFireSafetyMeasures;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *
    服务类
 * </p>
 *
 * @author su
 * @since 2021-01-04
 */
public interface SafeJobFireSafetyMeasuresService extends IService<SafeSiJobFireSafetyMeasures> {

    /**
     * 动火安全作业证-安全措施确认
     * @param params 请求参数
     * @return 结果
     */
    public Object confirmSafeJobFireSafetyMeasures(Map<String, Object> params);

    /**
     * 动火安全作业证-安全措施取消
     * @param params 请求参数
     * @return 结果
     */
    public Object cancelSafeJobFireSafetyMeasures(Map<String, Object> params);
}
