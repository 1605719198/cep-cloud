package com.jlkj.product.oi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.nacos.shaded.org.checkerframework.common.util.report.qual.ReportOverride;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.product.oi.domain.ProductionConveyingCoalPerformance;
import com.jlkj.product.oi.dto.productionconveyingcoalperformance.GetProductionConveyingCoalPerformanceDTO;
import com.jlkj.product.oi.mapper.ProductionConveyingCoalPerformanceMapper;
import com.jlkj.product.oi.service.ProductionConveyingCoalPerformanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
*@description: 针对表【product_oi_conveying_coal_performance(上煤实绩)】的数据库操作Service实现
*@Author: 265823
*@date: 2023/7/10 14:37
*/
@Service
public class ProductionConveyingCoalPerformanceServiceImpl extends ServiceImpl<ProductionConveyingCoalPerformanceMapper, ProductionConveyingCoalPerformance>
    implements ProductionConveyingCoalPerformanceService {

    /**
     * 上料实绩查询
     * @param dto
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public IPage<Map<String, Object>> get(GetProductionConveyingCoalPerformanceDTO dto) {
        Date start = DateUtil.parse(StrUtil.isEmpty(dto.getStartTime()) ? "1790-01-01" : dto.getStartTime() + " 00:00:00");
        Date end = DateUtil.parse(StrUtil.isEmpty(dto.getEndTime()) ? "1790-01-01" : dto.getEndTime() + " 23:59:59");
        QueryWrapper<ProductionConveyingCoalPerformance> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true, "asc".equals(dto.getOrderby()), dto.getOrder())
                .lambda()
                .ge(!StrUtil.isEmpty(dto.getStartTime()), ProductionConveyingCoalPerformance::getStartTime, start)
                .le(!StrUtil.isEmpty(dto.getEndTime()), ProductionConveyingCoalPerformance::getStartTime, end)
                .eq(!StrUtil.isEmpty(dto.getShiftName()), ProductionConveyingCoalPerformance::getShiftName, dto.getShiftName())
                .eq(!StrUtil.isEmpty(dto.getClassName()), ProductionConveyingCoalPerformance::getClassName, dto.getClassName());
        Page<Map<String, Object>> page = new Page<>(dto.getCurrent(), dto.getSize());
        IPage<Map<String, Object>> list = pageMaps(page, queryWrapper);
        return list;
    }
}




