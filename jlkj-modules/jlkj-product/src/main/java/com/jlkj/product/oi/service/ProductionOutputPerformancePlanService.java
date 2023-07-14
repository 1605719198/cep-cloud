package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionOutputPerformancePlan;
import com.jlkj.product.oi.dto.productionoutputperformanceplan.PageProductionOutputPerformancePlanDTO;
import com.jlkj.product.oi.vo.productionoutputperformanceplan.PageProductionOutputPerformancePlanVO;

/**
*@description: 服务接口-生产管理-炼焦实绩-推焦计划-数据同步
*@Author: 265823
*@date: 2023/7/12 8:24
*/
public interface ProductionOutputPerformancePlanService extends IService<ProductionOutputPerformancePlan> {

    /**
     * 生产管理-炼焦实绩-推焦计划-数据同步-查询-分页
     * @param pageProductionOutputPerformancePlanDTO 查询条件dto
     * @return 分页数据
     */
    IPage<PageProductionOutputPerformancePlanVO> getProductionOutputPerformancePlanPageData(PageProductionOutputPerformancePlanDTO pageProductionOutputPerformancePlanDTO);
}
