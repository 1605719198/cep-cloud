package com.jlkj.product.oi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.ProductionPowerPerformance;
import com.jlkj.product.oi.dto.productioncoefficientrecord.GetProductionCoefficientRecordDTO;
import com.jlkj.product.oi.mapper.ProductionPowerPerformanceMapper;
import com.jlkj.product.oi.service.ProductionPowerPerformanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
*@description: 针对表【product_oi_power_performance(发电量实绩)】的数据库操作Service实现
*@Author: 265823
*@date: 2023/7/11 8:59
*/
@Service
public class ProductionPowerPerformanceServiceImpl extends ServiceImpl<ProductionPowerPerformanceMapper, ProductionPowerPerformance>
    implements ProductionPowerPerformanceService {

    /**
     * 发电量实绩查询
     * @param dto
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public IPage<Map<String, Object>> get(GetProductionCoefficientRecordDTO dto) {
        Date start = DateUtil.parse(StrUtil.isEmpty(dto.getStartTime()) ? "1790-01-01" : dto.getStartTime() + " 00:00:00");
        Date end = DateUtil.parse(StrUtil.isEmpty(dto.getEndTime()) ? "1790-01-01" : dto.getEndTime() + " 23:59:59");
        QueryWrapper<ProductionPowerPerformance> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true, "asc".equals(dto.getOrderby()), dto.getOrder())
                .lambda()
                .ge(!StrUtil.isEmpty(dto.getStartTime()), ProductionPowerPerformance::getClassStartTime, start)
                .le(!StrUtil.isEmpty(dto.getEndTime()), ProductionPowerPerformance::getClassStartTime, end)
                .eq(!StrUtil.isEmpty(dto.getShiftName()), ProductionPowerPerformance::getShiftName, dto.getShiftName())
                .eq(!StrUtil.isEmpty(dto.getClassName()), ProductionPowerPerformance::getClassName, dto.getClassName());
        Page<Map<String, Object>> page = new Page<>(dto.getCurrent(), dto.getSize());
        return pageMaps(page, queryWrapper);
    }

}




