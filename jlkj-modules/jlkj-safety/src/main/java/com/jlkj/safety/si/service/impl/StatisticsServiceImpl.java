package com.jlkj.safety.si.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.Empty;
import com.jlkj.safety.si.mapper.HomeMapper;
import com.jlkj.safety.si.mapper.StatisticsMapper;
import com.jlkj.safety.si.service.StatisticsService;
import com.jlkj.safety.si.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 安环统计 服务实现类
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
@Service
@Slf4j
public class StatisticsServiceImpl extends ServiceImpl<HomeMapper, Empty> implements StatisticsService {

    @Resource
    private StatisticsMapper statisticsMapper;

    @Override
    @Transactional(readOnly = true)
    public Object getSafeAlarmHisList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, statisticsMapper.getSafeAlarmHisList(ResponseUtil.initPage(params), params));
    }



}
