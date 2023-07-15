package com.jlkj.product.oi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.ProductionOutputPerformance;
import com.jlkj.product.oi.dto.dashboardindex.GetCokePlanPerformanceDTO;
import com.jlkj.product.oi.dto.productionoutputperformance.GetProductionOutputPerformanceDTO;
import com.jlkj.product.oi.mapper.ProductionOutputPerformanceMapper;
import com.jlkj.product.oi.service.ProductionOutputPerformanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*@description: 针对表【product_oi_output_performance(出炉实绩)】的数据库操作Service实现
*@Author: 265823
*@date: 2023/7/10 17:00
*/
@Service
public class ProductionOutputPerformanceServiceImpl extends ServiceImpl<ProductionOutputPerformanceMapper, ProductionOutputPerformance>
    implements ProductionOutputPerformanceService {
    /**
     * 出炉实绩查询
     * @param dto
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public IPage<Map<String, Object>> get(GetProductionOutputPerformanceDTO dto) {
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
        return list;
    }

    /**
     * 焦炭产量记录查询
     * @param dto
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Map<String, Object> getCokePerformanceAnalys(GetCokePlanPerformanceDTO dto) {
        List<Map<String, Object>> plan = baseMapper.getCokePlanAnalys(dto);
        List<Map<String, Object>> perf = baseMapper.getCokePerformanceAnalys(dto);
        Map<String, Object> res = new HashMap<>(2);
        res.put("plan", plan);
        res.put("perf", perf);
        return res;
    }
    /**
     * 焦炭产量-详情-焦炭产量 基于 焦炭等级
     * @param dto
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<Map<String, Object>> getCokeOutputAnalysByLevel(GetCokePlanPerformanceDTO dto) {
        return baseMapper.getCokeOutputAnalysByLevel(dto);
    }

    /**
     * 焦炭产量-详情-焦炭产量 基于 炉号
     * @param dto
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<Map<String, Object>> getCokeOutputAnalysByStove(GetCokePlanPerformanceDTO dto) {
        return baseMapper.getCokeOutputAnalysByStove(dto);
    }

    /**
     * 焦炭产量-详情-焦炭产率（干熄焦、湿熄焦）
     * @param dto
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Map<String, Object> getProductivity(GetCokePlanPerformanceDTO dto) {
        List<Map<String, Object>> productivity = baseMapper.getProductivity(dto);
        Map<String, Object> res = new HashMap<>(4);
        if(productivity.size() > 0){
            res.put("gxj",productivity.get(0).get("gxj"));
            res.put("sxj",productivity.get(0).get("sxj"));
        }else{
            res.put("gxj",0.000);
            res.put("sxj",0.000);
        }
        return res;
    }

}




