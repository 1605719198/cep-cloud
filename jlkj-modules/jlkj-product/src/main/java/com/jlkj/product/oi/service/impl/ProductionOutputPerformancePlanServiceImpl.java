package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.ProductionOutputPerformancePlan;
import com.jlkj.product.oi.dto.productionoutputperformanceplan.PageProductionOutputPerformancePlanDTO;
import com.jlkj.product.oi.mapper.ProductionOutputPerformancePlanMapper;
import com.jlkj.product.oi.service.ProductionOutputPerformancePlanService;
import com.jlkj.product.oi.vo.productionoutputperformanceplan.PageProductionOutputPerformancePlanVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
*@description: 服务实现-生产管理-炼焦实绩-推焦计划-数据同步
*@Author: 265823
*@date: 2023/7/12 8:24
*/
@Service
public class ProductionOutputPerformancePlanServiceImpl extends ServiceImpl<ProductionOutputPerformancePlanMapper, ProductionOutputPerformancePlan>
        implements ProductionOutputPerformancePlanService {

    /**
     * 生产管理-炼焦实绩-推焦计划-数据同步-查询-分页
     * @param pageProductionOutputPerformancePlanDTO 查询条件dto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public IPage<PageProductionOutputPerformancePlanVO> getProductionOutputPerformancePlanPageData(PageProductionOutputPerformancePlanDTO pageProductionOutputPerformancePlanDTO) {
        Page<Map<String, Object>> page = new Page<>(pageProductionOutputPerformancePlanDTO.getCurrent(), pageProductionOutputPerformancePlanDTO.getSize());
        return getBaseMapper().getProductionOutputPerformancePlanPageData(page, pageProductionOutputPerformancePlanDTO);
    }
}
