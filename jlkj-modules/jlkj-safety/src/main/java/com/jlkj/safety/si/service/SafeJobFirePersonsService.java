package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiJobFirePersons;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 动火安全作业证-动火人 服务类
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
public interface SafeJobFirePersonsService extends IService<SafeSiJobFirePersons> {

    /**
     * 动火安全作业证-新增作业人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobFirePersons(Map<String, Object> params);

    /**
     * 动火安全作业证-删除作业人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean deleteSafeJobFirePersons(Map<String, Object> params);
}
