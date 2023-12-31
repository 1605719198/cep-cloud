package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionStackerReclaimerPerformance;
import com.jlkj.product.oi.dto.productionconveyingcoalperformance.GetProductionStackerReclaimerPerformanceDTO;
import com.jlkj.product.oi.dto.productionstackerreclaimerperformance.DeleteProductionStackerReclaimerPerformanceDTO;
import com.jlkj.product.oi.dto.productionstackerreclaimerperformance.InfoSchedulingDTO;
import com.jlkj.product.oi.dto.productionstackerreclaimerperformance.InsertProductionStackerReclaimerPerformanceDTO;
import com.jlkj.product.oi.dto.productionstackerreclaimerperformance.UpdateProductionStackerReclaimerPerformanceDTO;
import com.jlkj.product.oi.vo.productionstackerreclaimerperformance.InfoSchedulingVO;

import java.util.Map;

/**
*@description: 针对表【product_oi_stacker_reclaimer_performance(堆取料机实绩)】的数据库操作Service
*@Author: 265823
*@date: 2023/7/11 13:08
*/
public interface ProductionStackerReclaimerPerformanceService extends IService<ProductionStackerReclaimerPerformance> {

    /**
     * 堆取料机实绩-新增
     * @param insertProductionStackerReclaimerPerformanceDTO 新增dto
     */
    void insertProductionStackerReclaimerPerformanceData(InsertProductionStackerReclaimerPerformanceDTO insertProductionStackerReclaimerPerformanceDTO);

    /**
     * 堆取料机实绩-修改
     * @param updateProductionStackerReclaimerPerformanceDTO 修改dto
     */
    void updateProductionStackerReclaimerPerformanceData(UpdateProductionStackerReclaimerPerformanceDTO updateProductionStackerReclaimerPerformanceDTO);

    /**
     * 堆取料机实绩-删除
     * @param deleteProductionStackerReclaimerPerformanceDTO 删除dto
     */
    void deleteProductionStackerReclaimerPerformanceData(DeleteProductionStackerReclaimerPerformanceDTO deleteProductionStackerReclaimerPerformanceDTO);

    /**
     * 获取排班信息
     * @param infoSchedulingDTO 查询条件dto
     * @return 单行数据
     */
    InfoSchedulingVO getUserSchedulingInfoData(InfoSchedulingDTO infoSchedulingDTO);

    /**
     * 堆取料机实绩查询
     * @param dto
     * @return
     */
    IPage<Map<String, Object>> getStackerReclaimerPerformance(GetProductionStackerReclaimerPerformanceDTO dto);
}
