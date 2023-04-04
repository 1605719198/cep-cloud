package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionCokeShedOutput;
import com.jlkj.product.oi.dto.productioncokeshedoutput.*;
import com.jlkj.product.oi.vo.productioncokeshedoutput.PageProductionCokeShedOutputVO;

/**
 * 服务接口-焦棚产量维护
 *
 * @author sudeyou
 * @since 2022-10-18 14:57:19
 */
public interface ProductionCokeShedOutputService extends IService<ProductionCokeShedOutput> {

    /**
     * 焦棚产量维护-查询-分页
     * @param pageProductionCokeShedOutputDTO 查询条件dto
     * @return 分页数据
     */
    IPage<PageProductionCokeShedOutputVO> getProductionCokeShedOutputPageData(PageProductionCokeShedOutputDTO pageProductionCokeShedOutputDTO);

    /**
     * 焦棚产量维护-新增
     * @param insertProductionCokeShedOutputDTO 新增dto
     * @return 执行结果
     */
    Object insertProductionCokeShedOutputData(InsertProductionCokeShedOutputDTO insertProductionCokeShedOutputDTO);

    /**
     * 焦棚产量维护-修改
     * @param updateProductionCokeShedOutputDTO 修改dto
     * @return 执行结果
     */
    Object updateProductionCokeShedOutputData(UpdateProductionCokeShedOutputDTO updateProductionCokeShedOutputDTO);

    /**
     * 焦棚产量维护-确认
     * @param confirmProductionCokeShedOutputDTO 修改dto
     * @return 执行结果
     */
    Object confirmProductionCokeShedOutputData(ConfirmProductionCokeShedOutputDTO confirmProductionCokeShedOutputDTO);

    /**
     * 焦棚产量维护-删除
     * @param deleteProductionCokeShedOutputDTO 删除dto
     * @return 执行结果
     */
    Object deleteProductionCokeShedOutputData(DeleteProductionCokeShedOutputDTO deleteProductionCokeShedOutputDTO);
}

