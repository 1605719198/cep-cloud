package com.jlkj.product.oi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.resp.PageResult;
import com.jlkj.product.oi.domain.ProductionOutputPerformance;
import com.jlkj.product.oi.dto.dashboardindex.GetCokePlanPerformanceDTO;
import com.jlkj.product.oi.dto.productionoutputperformance.GetProductionOutputPerformanceDTO;
import com.jlkj.product.oi.mapper.ProductionOutputPerformanceMapper;
import com.jlkj.product.oi.service.ProductionOutputPerformanceService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author zyf
* @description 针对表【product_oi_output_performance(出炉实绩)】的数据库操作Service实现
* @createDate 2022-05-11 14:37:05
*/
@Service
public class ProductionOutputPerformanceServiceImpl extends ServiceImpl<ProductionOutputPerformanceMapper, ProductionOutputPerformance>
    implements ProductionOutputPerformanceService {

    public Object get(GetProductionOutputPerformanceDTO dto) {
        Date start = DateUtil.parse(StrUtil.isEmpty(dto.getStartTime()) ? "1790-01-01" : dto.getStartTime() + " 00:00:00");
        Date end = DateUtil.parse(StrUtil.isEmpty(dto.getEndTime()) ? "1790-01-01" : dto.getEndTime() + " 23:59:59");
        QueryWrapper<ProductionOutputPerformance> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true, "asc".equals(dto.getOrderby()), dto.getOrder())
                .lambda()
                .ge(!StrUtil.isEmpty(dto.getStartTime()), ProductionOutputPerformance::getProductionDate, start)
                .le(!StrUtil.isEmpty(dto.getEndTime()), ProductionOutputPerformance::getProductionDate, end)
                .eq(!StrUtil.isEmpty(dto.getShiftName()), ProductionOutputPerformance::getShiftName, dto.getShiftName())
                .eq(!StrUtil.isEmpty(dto.getClassName()), ProductionOutputPerformance::getClassName, dto.getClassName());
        Page<Map<String, Object>> page = new Page<>(dto.getCurrent(), dto.getSize());
        IPage<Map<String, Object>> list = pageMaps(page, queryWrapper);
        return PageResult.success(list);
    }

    public List<Map<String, Object>> getCokePlanAnalys(GetCokePlanPerformanceDTO dto) {
        return baseMapper.getCokePlanAnalys(dto);
    }

    public List<Map<String, Object>> getCokePerformanceAnalys(GetCokePlanPerformanceDTO dto) {
        return baseMapper.getCokePerformanceAnalys(dto);
    }


    public List<Map<String, Object>> getCokeOutputAnalysByLevel(GetCokePlanPerformanceDTO dto) {
        return baseMapper.getCokeOutputAnalysByLevel(dto);
    }
    public List<Map<String, Object>> getCokeOutputAnalysByStove(GetCokePlanPerformanceDTO dto) {
        return baseMapper.getCokeOutputAnalysByStove(dto);
    }

    public List<Map<String, Object>> getProductivity(GetCokePlanPerformanceDTO dto) {
        return baseMapper.getProductivity(dto);
    }

}




