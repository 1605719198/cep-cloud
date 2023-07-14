package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionReportOilConsume;
import com.jlkj.product.oi.dto.productionreportoilconsume.AddProductionReportOilConsumeDTO;
import com.jlkj.product.oi.dto.productionreportoilconsume.DeleteProductionReportOilConsumeDTO;
import com.jlkj.product.oi.dto.productionreportoilconsume.PageProductionReportOilConsumeDTO;
import com.jlkj.product.oi.dto.productionreportoilconsume.UpdateProductionReportOilConsumeDTO;

import java.util.Map;

/**
*@description: 生产管理-报表管理-油品消耗
*@Author: 265823
*@date: 2023/7/12 8:41
*/
public interface ProductionReportOilConsumeService extends IService<ProductionReportOilConsume> {
    /**
     *  查询-分页-生产管理-统计分析-指标跟踪(月)
     * @param pageProductionReportOilConsumeDTO 查询条件dto
     * @return 分页列表
     */
    IPage<Map<String, String>> getListPage(PageProductionReportOilConsumeDTO pageProductionReportOilConsumeDTO);

    /**
     * 新增
     * @param addProductionReportOilConsumeDTO
     */
    void saveCustom(AddProductionReportOilConsumeDTO addProductionReportOilConsumeDTO);

    /**
     * 修改
     * @param updateProductionReportOilConsumeDTO
     */
    void updateCustom(UpdateProductionReportOilConsumeDTO updateProductionReportOilConsumeDTO);

    /**
     * 删除
     * @param deleteProductionReportOilConsumeDTO
     */
    void delete(DeleteProductionReportOilConsumeDTO deleteProductionReportOilConsumeDTO);
}
