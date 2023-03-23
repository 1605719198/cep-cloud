package com.jlkj.safety.si.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.AlertRecord;
import com.jlkj.safety.si.mapper.AlertRecordMapper;
import com.jlkj.safety.si.service.AlertRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author yzl
 * @create 2022-12-14 09:36:40.867315
 */
@Service
@Slf4j
public class AlertRecordServiceImpl extends ServiceImpl<AlertRecordMapper, AlertRecord>
        implements AlertRecordService {
    @Override
    public List<Map<String, String>> getAlertRecord() {
        return getBaseMapper().getAlertRecord();
    }

    @Override
    public Map<String, String> getAlertRecordExceedance() {
        return getBaseMapper().getAlertRecordExceedance();
    }

    @Override
    public List<Map<String, String>> getHomeAlertRecord() {
        return getBaseMapper().getHomeAlertRecord();
    }
}

