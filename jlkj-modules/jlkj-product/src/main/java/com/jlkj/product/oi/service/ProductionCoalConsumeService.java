package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionCoalConsume;
import com.jlkj.product.oi.dto.productioncoalconsume.*;
import com.jlkj.product.oi.vo.productioncoalconsume.PageProductionCoalConsumeVO;

/**
*@description: 服务接口-炼焦煤消耗维护
*@Author: 265823
*@date: 2023/7/10 11:15
*/
public interface ProductionCoalConsumeService extends IService<ProductionCoalConsume> {

    /**
     * 炼焦煤消耗维护-查询-分页
     * @param pageProductionCoalConsumeDTO 查询条件dto
     * @return 分页数据
     */
    IPage<PageProductionCoalConsumeVO> getProductionCoalConsumePageData(PageProductionCoalConsumeDTO pageProductionCoalConsumeDTO);

    /**
     * 炼焦煤消耗维护-新增
     * @param insertProductionCoalConsumeDTO 新增dto
     * @return 执行结果
     */
    void insertProductionCoalConsumeData(InsertProductionCoalConsumeDTO insertProductionCoalConsumeDTO);

    /**
     * 炼焦煤消耗维护-修改
     * @param updateProductionCoalConsumeDTO 修改dto
     * @return 执行结果
     */
    void updateProductionCoalConsumeData(UpdateProductionCoalConsumeDTO updateProductionCoalConsumeDTO);

    /**
     * 炼焦煤消耗维护-确认
     * @param confirmProductionCoalConsumeDTO 修改dto
     * @return 执行结果
     */
    void confirmProductionCoalConsumeData(ConfirmProductionCoalConsumeDTO confirmProductionCoalConsumeDTO);

    /**
     * 炼焦煤消耗维护-删除
     * @param deleteProductionCoalConsumeDTO 删除dto
     * @return 执行结果
     */
    void deleteProductionCoalConsumeData(DeleteProductionCoalConsumeDTO deleteProductionCoalConsumeDTO);
}

