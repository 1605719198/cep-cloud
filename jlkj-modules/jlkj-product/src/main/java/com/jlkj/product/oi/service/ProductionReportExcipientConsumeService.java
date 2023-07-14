package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionReportExcipientConsume;
import com.jlkj.product.oi.dto.productionreportexcipientconsume.AddProductionReportExcipientConsumeDTO;
import com.jlkj.product.oi.dto.productionreportexcipientconsume.DeleteProductionReportExcipientConsumeDTO;
import com.jlkj.product.oi.dto.productionreportexcipientconsume.PageProductionReportExcipientConsumeDTO;
import com.jlkj.product.oi.dto.productionreportexcipientconsume.UpdateProductionReportExcipientConsumeDTO;

import java.util.Map;

/**
*@description: 生产管理-报表管理-辅料消耗
*@Author: 265823
*@date: 2023/7/12 9:02
*/
public interface ProductionReportExcipientConsumeService extends IService<ProductionReportExcipientConsume> {
    /**
     * 查询生产管理-报表管理-辅料消耗
     * @param pageProductionReportExcipientConsumeDTO 查询条件dto
     * @return 分页列表
     */
    IPage<Map<String, String>> getListPage(PageProductionReportExcipientConsumeDTO pageProductionReportExcipientConsumeDTO);

    /**
     * 新增-辅料消耗
     * @param addProductionReportExcipientConsumeDTO
     */
    void saveCustom(AddProductionReportExcipientConsumeDTO addProductionReportExcipientConsumeDTO);

    /**
     * 修改-辅料消耗
     * @param updateProductionReportExcipientConsumeDTO
     */
    void updateCustom(UpdateProductionReportExcipientConsumeDTO updateProductionReportExcipientConsumeDTO);

    /**
     * 删除-辅料消耗
     * @param deleteProductionReportExcipientConsumeDTO
     */
    void delete(DeleteProductionReportExcipientConsumeDTO deleteProductionReportExcipientConsumeDTO);
}
