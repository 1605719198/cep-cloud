package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionReportUnloading;
import com.jlkj.product.oi.dto.productionreportunloading.AddProductionReportUnloadingDTO;
import com.jlkj.product.oi.dto.productionreportunloading.DeleteProductionReportUnloadingDTO;
import com.jlkj.product.oi.dto.productionreportunloading.PageProductionReportUnloadingDTO;
import com.jlkj.product.oi.dto.productionreportunloading.UpdateProductionReportUnloadingDTO;

import java.util.Map;

/**
*@description: 生产管理-报表管理-煤场卸车情况
*@Author: 265823
*@date: 2023/7/12 9:29
*/
public interface ProductionReportUnloadingService extends IService<ProductionReportUnloading> {
    /**
     * 查询生产管理-报表管理-煤场卸车情况
     * @param pageProductionReportUnloadingDTO 查询条件dto
     * @return 分页列表
     */
    IPage<Map<String, String>> getListPage(PageProductionReportUnloadingDTO pageProductionReportUnloadingDTO);

    /**
     * 新增-煤场卸车情况
     * @param dto
     */
    void saveCustom(AddProductionReportUnloadingDTO dto);

    /**
     * 修改-煤场卸车情况
     * @param dto
     */
    void updateCustom(UpdateProductionReportUnloadingDTO dto);

    /**
     * 删除生产管理-报表管理-煤场卸车情况
     * @param deleteProductionReportUnloadingDTO
     */
    void delete(DeleteProductionReportUnloadingDTO deleteProductionReportUnloadingDTO);
}
