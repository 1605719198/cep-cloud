package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionConveyingCoalRecord;
import com.jlkj.product.oi.dto.productionconveyingcoalrecord.DeleteProductionConveyingCoalRecordDTO;
import com.jlkj.product.oi.dto.productionconveyingcoalrecord.InsertProductionConveyingCoalRecordDTO;
import com.jlkj.product.oi.dto.productionconveyingcoalrecord.PageProductionConveyingCoalRecordDTO;
import com.jlkj.product.oi.vo.productionconveyingcoalrecord.PageProductionConveyingCoalRecordVO;

/**
*@description: 服务接口-生产实绩-上煤记录
*@Author: 265823
*@date: 2023/7/10 14:42
*/
public interface ProductionConveyingCoalRecordService extends IService<ProductionConveyingCoalRecord> {

    /**
     * 生产实绩-上煤记录-新增
     * @param insertProductionConveyingCoalRecordDTO 新增dto
     */
    void insertProductionConveyingCoalRecordData(InsertProductionConveyingCoalRecordDTO insertProductionConveyingCoalRecordDTO);

    /**
     * 生产实绩-上煤记录-删除
     * @param deleteProductionConveyingCoalRecordDTO 删除dto
     */
    void deleteProductionConveyingCoalRecordData(DeleteProductionConveyingCoalRecordDTO deleteProductionConveyingCoalRecordDTO);

    /**
     * 生产实绩-上煤记录-查询-分页
     * @param pageProductionConveyingCoalRecordDTO 查询条件dto
     * @return 分页数据
     */
    IPage<PageProductionConveyingCoalRecordVO> getProductionConveyingCoalRecordPageData(PageProductionConveyingCoalRecordDTO pageProductionConveyingCoalRecordDTO);
}
