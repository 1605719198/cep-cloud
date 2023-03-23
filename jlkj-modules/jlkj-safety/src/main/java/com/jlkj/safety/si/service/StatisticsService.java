package com.jlkj.safety.si.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.safety.si.entity.Empty;

import java.util.Map;

/**
 * <p>
 * 安环统计 服务类
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
public interface StatisticsService extends IService<Empty> {

    /**
     * 报警历史
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeAlarmHisList(Map<String, Object> params);
}
