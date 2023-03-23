package com.jlkj.safety.si.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.safety.si.entity.SafeSiJobHoistingSafetyMeasures;

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
public interface SafeJobHoistingSafetyMeasuresService extends IService<SafeSiJobHoistingSafetyMeasures> {

    /**
     * 吊装安全作业证-安全措施确认
     * @param params 请求参数
     * @return 结果
     */
    public Object confirmSafeJobHoistingSafetyMeasures(Map<String, Object> params);

    /**
     * 吊装安全作业证-安全措施取消
     * @param params 请求参数
     * @return 结果
     */
    public Object cancelSafeJobHoistingSafetyMeasures(Map<String, Object> params);
}
