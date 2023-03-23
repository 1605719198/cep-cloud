package com.jlkj.safety.si.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.safety.si.entity.AlertRecord;

import java.util.List;
import java.util.Map;

/**
 * @author yzl
 * @create 2022-12-14 09:36:40.866316
 */
public interface AlertRecordService extends IService<AlertRecord> {
    /**
     * 环保看板 - 查询环保报警
     * @return
     */
    List<Map<String, String>> getAlertRecord();

    /**
     * 环保看板 - 超标次数分析
     * @return
     */
    Map<String, String> getAlertRecordExceedance();
    /**
     * 首页 - 环保指标
     * @return
     */
    List<Map<String, String>> getHomeAlertRecord();
}
