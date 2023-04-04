package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionCokeFactoryPerformance;
import com.jlkj.product.oi.dto.productioncokefactoryperformance.PageProductionCokeFactoryPerformanceDTO;
import com.jlkj.product.oi.vo.productioncokefactoryperformance.PageProductionCokeFactoryPerformanceVO;

/**
 * 服务接口-焦炭出厂实绩
 * @author sudeyou
 * @since 2022-08-11 14:02:21
 */
public interface ProductionCokeFactoryPerformanceService extends IService<ProductionCokeFactoryPerformance> {

    /**
     * 焦炭出厂实绩-查询-分页
     * @param pageProductionCokeFactoryPerformanceDTO 查询条件dto
     * @return 分页数据
     */
    IPage<PageProductionCokeFactoryPerformanceVO> getProductionCokeFactoryPerformancePageData(PageProductionCokeFactoryPerformanceDTO pageProductionCokeFactoryPerformanceDTO);
}

