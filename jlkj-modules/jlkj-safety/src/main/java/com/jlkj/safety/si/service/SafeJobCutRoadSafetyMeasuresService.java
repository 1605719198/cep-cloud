package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiJobCutRoadSafetyMeasures;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *
    服务类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
public interface SafeJobCutRoadSafetyMeasuresService extends IService<SafeSiJobCutRoadSafetyMeasures> {

    /**
     * 断路安全作业证-安全措施确认
     * @param params 请求参数
     * @return 结果
     */
    public Object confirmSafeJobCutRoadSafetyMeasures(Map<String, Object> params);

    /**
     * 断路安全作业证-安全措施取消
     * @param params 请求参数
     * @return 结果
     */
    public Object cancelSafeJobCutRoadSafetyMeasures(Map<String, Object> params);
}
