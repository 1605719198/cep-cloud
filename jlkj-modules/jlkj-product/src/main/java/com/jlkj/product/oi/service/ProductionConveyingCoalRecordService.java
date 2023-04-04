package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionConveyingCoalRecord;
import com.jlkj.product.oi.dto.productionconveyingcoalrecord.DeleteProductionConveyingCoalRecordDTO;
import com.jlkj.product.oi.dto.productionconveyingcoalrecord.InsertProductionConveyingCoalRecordDTO;
import com.jlkj.product.oi.dto.productionconveyingcoalrecord.PageProductionConveyingCoalRecordDTO;
import com.jlkj.product.oi.vo.productionconveyingcoalrecord.PageProductionConveyingCoalRecordVO;

/**
 * 服务接口-生产实绩-上煤记录
 *
 * @author sudeyou
 * @since 2022-11-17 15:18:05
 */
public interface ProductionConveyingCoalRecordService extends IService<ProductionConveyingCoalRecord> {

    /**
     * 生产实绩-上煤记录-新增
     * @param insertProductionConveyingCoalRecordDTO 新增dto
     * @return 执行结果
     */
    Object insertProductionConveyingCoalRecordData(InsertProductionConveyingCoalRecordDTO insertProductionConveyingCoalRecordDTO);

    /**
     * 生产实绩-上煤记录-删除
     * @param deleteProductionConveyingCoalRecordDTO 删除dto
     * @return 执行结果
     */
    Object deleteProductionConveyingCoalRecordData(DeleteProductionConveyingCoalRecordDTO deleteProductionConveyingCoalRecordDTO);

    /**
     * 生产实绩-上煤记录-查询-分页
     * @param pageProductionConveyingCoalRecordDTO 查询条件dto
     * @return 分页数据
     */
    IPage<PageProductionConveyingCoalRecordVO> getProductionConveyingCoalRecordPageData(PageProductionConveyingCoalRecordDTO pageProductionConveyingCoalRecordDTO);
}
