package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionLogsName;
import com.jlkj.product.oi.dto.productionlogsname.AddProductionLogsNameDTO;
import com.jlkj.product.oi.dto.productionlogsname.DeleteProductionLogsNameDTO;
import com.jlkj.product.oi.dto.productionlogsname.PageProductionLogsNameDTO;
import com.jlkj.product.oi.dto.productionlogsname.UpdateProductionLogsNameDTO;

import java.util.Map;

/**
*@description: 日志项名称
*@Author: 265823
*@date: 2023/7/10 16:04
*/
public interface ProductionLogsNameService extends IService<ProductionLogsName> {
    /**
     * 列表
     * @param pageProductionLogsNameDTO 查询条件dto
     * @return 分页列表
     */
    IPage<Map<String, String>> getListPage(PageProductionLogsNameDTO pageProductionLogsNameDTO);

    /**
     * 新增
     * @param addProductionLogsNameDTO
     */
    void saveCustom(AddProductionLogsNameDTO addProductionLogsNameDTO);

    /**
     * 修改
     * @param updateProductionLogsNameDTO
     */
    void updateCustom(UpdateProductionLogsNameDTO updateProductionLogsNameDTO);

    /**
     * 删除日志项名称
     * @param deleteProductionLogsNameDTO
     */
    void delete(DeleteProductionLogsNameDTO deleteProductionLogsNameDTO);
}
