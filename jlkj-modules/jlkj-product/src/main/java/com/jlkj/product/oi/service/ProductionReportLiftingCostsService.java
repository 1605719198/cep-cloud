package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionReportLiftingCosts;
import com.jlkj.product.oi.dto.productionreportliftingcosts.AddProductionReportLiftingCostsDTO;
import com.jlkj.product.oi.dto.productionreportliftingcosts.DeleteProductionReportLiftingCostsDTO;
import com.jlkj.product.oi.dto.productionreportliftingcosts.PageProductionReportLiftingCostsDTO;
import com.jlkj.product.oi.dto.productionreportliftingcosts.UpdateProductionReportLiftingCostsDTO;

import java.util.Map;

/**
*@description: 生产管理-报表管理-吊装费用
*@Author: 265823
*@date: 2023/7/12 10:01
*/
public interface ProductionReportLiftingCostsService extends IService<ProductionReportLiftingCosts> {
    /**
     *
     * @param pageProductionReportLiftingCostsDTO 查询条件dto
     * @return 分页列表
     */
    IPage<Map<String, String>> getListPage(PageProductionReportLiftingCostsDTO pageProductionReportLiftingCostsDTO);

    /**
     * 新增生产管理-报表管理-吊装费用
     * @param dto
     */
    void saveCustom(AddProductionReportLiftingCostsDTO dto);

    /**
     * 修改生产管理-报表管理-吊装费用
     * @param dto
     */
    void updateCustom(UpdateProductionReportLiftingCostsDTO dto);

    /**
     * 删除生产管理-报表管理-吊装费用
     * @param deleteProductionReportLiftingCostsDTO
     */
    void delete(DeleteProductionReportLiftingCostsDTO deleteProductionReportLiftingCostsDTO);
}
