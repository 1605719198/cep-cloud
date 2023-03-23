package com.jlkj.safety.si.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.safety.si.entity.Empty;

import java.util.Map;

/**
 * <p>
 * 安全报警处理 服务类
 * </p>
 *
 * @author cy
 * @since 2021-04-26
 */
public interface SafeAlarmDisposeService extends IService<Empty> {

    /**
     * 安全报警处理-查询列表
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeAlarmDisposePageList(Map<String, Object> params);

    /**
     * 安全报警处理-详情
     * @param params 查询条件
     * @return 结果
     */
    public Object getSafeAlarmDisposeById(Map<String, Object> params);

    /**
     * 安全报警处理-上报新数据
     * @param params 数据
     * @return 返回值
     */
    public Object addNewSafeAlarmDispose(Map<String, Object> params);

    /**
     * 安全报警处理-上报自动采集数据
     * @param params 数据
     * @return 返回值
     */
    public Object addAutoSafeAlarmDispose(Map<String, Object> params);

    /**
     * 安全报警处理-上传处理
     * @param params 数据
     * @return 返回值
     */
    public Object doDisposeSafeAlarmDispose(Map<String, Object> params);

    /**
     * 安全报警处理-上传确认
     * @param params 数据
     * @return 返回值
     */
    public Object doConfirmSafeAlarmDispose(Map<String, Object> params);

}
