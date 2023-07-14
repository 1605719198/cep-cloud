package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.dto.productionreportlogisticscosts.AddProductionReportLogisticsCostsDTO;
import com.jlkj.product.oi.dto.productionreportlogisticscosts.DeleteProductionReportLogisticsCostsDTO;
import com.jlkj.product.oi.dto.productionreportlogisticscosts.PageProductionReportLogisticsCostsDTO;
import com.jlkj.product.oi.domain.ProductionReportLogisticsCosts;
import com.jlkj.product.oi.dto.productionreportlogisticscosts.UpdateProductionReportLogisticsCostsDTO;

import java.util.Map;

/**
*@description: 生产管理-报表管理-物流费用
*@Author: 265823
*@date: 2023/7/12 10:27
*/
public interface ProductionReportLogisticsCostsService extends IService<ProductionReportLogisticsCosts> {
    /**
     * 查询生产管理-报表管理-物流费用
     * @param pageProductionReportLogisticsCostsDTO 查询条件dto
     * @return 分页列表
     */
    IPage<Map<String, String>> getListPage(PageProductionReportLogisticsCostsDTO pageProductionReportLogisticsCostsDTO);

    /**
     * 新增生产管理-报表管理-物流费用
     * @param dto
     */
    void saveCustom(AddProductionReportLogisticsCostsDTO dto);

    /**
     * 修改生产管理-报表管理-物流费用
     * @param dto
     */
    void updateCustom(UpdateProductionReportLogisticsCostsDTO dto);

    /**
     * 删除生产管理-报表管理-物流费用
     * @param deleteProductionReportLogisticsCostsDTO
     */
    void delete(DeleteProductionReportLogisticsCostsDTO deleteProductionReportLogisticsCostsDTO);
}
