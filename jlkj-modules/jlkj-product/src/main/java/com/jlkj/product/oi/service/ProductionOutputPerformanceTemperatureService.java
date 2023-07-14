package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionOutputPerformanceTemperature;
import com.jlkj.product.oi.dto.productionoutputperformancetemperature.PageProductionOutputPerformanceTemperatureDTO;
import com.jlkj.product.oi.vo.productionoutputperformancetemperature.PageProductionOutputPerformanceTemperatureVO;

/**
*@description: 服务接口-出炉实绩测温记录
*@Author: 265823
*@date: 2023/7/10 17:07
*/
public interface ProductionOutputPerformanceTemperatureService extends IService<ProductionOutputPerformanceTemperature> {

    /**
     * 出炉实绩测温记录-查询-分页
     * @param pageProductionOutputPerformanceTemperatureDTO 查询条件dto
     * @return 分页数据
     */
    IPage<PageProductionOutputPerformanceTemperatureVO> getProductionOutputPerformanceTemperaturePageData(PageProductionOutputPerformanceTemperatureDTO pageProductionOutputPerformanceTemperatureDTO);
}

