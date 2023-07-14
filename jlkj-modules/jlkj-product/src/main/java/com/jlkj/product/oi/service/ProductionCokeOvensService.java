package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionCokeOvens;
import com.jlkj.product.oi.dto.productioncokeovens.DeleteProductionCokeOvensDTO;
import com.jlkj.product.oi.dto.productioncokeovens.InsertProductionCokeOvensDTO;
import com.jlkj.product.oi.dto.productioncokeovens.PageProductionCokeOvensDTO;
import com.jlkj.product.oi.dto.productioncokeovens.UpdateProductionCokeOvensDTO;
import com.jlkj.product.oi.vo.productioncokeovens.PageProductionCokeOvensVO;

/**
 * 服务接口-每日推焦炉数维护
 *
 * @author sudeyou
 * @since 2022-10-18 14:50:42
 */
public interface ProductionCokeOvensService extends IService<ProductionCokeOvens> {

    /**
     * 每日推焦炉数维护-查询-分页
     * @param pageProductionCokeOvensDTO 查询条件dto
     * @return 分页数据
     */
    IPage<PageProductionCokeOvensVO> getProductionCokeOvensPageData(PageProductionCokeOvensDTO pageProductionCokeOvensDTO);

    /**
     * 每日推焦炉数维护-新增
     * @param insertProductionCokeOvensDTO 新增dto
     */
    void insertProductionCokeOvensData(InsertProductionCokeOvensDTO insertProductionCokeOvensDTO);

    /**
     * 每日推焦炉数维护-修改
     * @param updateProductionCokeOvensDTO 修改dto
     */
    void updateProductionCokeOvensData(UpdateProductionCokeOvensDTO updateProductionCokeOvensDTO);

    /**
     * 每日推焦炉数维护-确认
     */
    void confirmProductionCokeOvensData();

    /**
     * 每日推焦炉数维护-删除
     * @param deleteProductionCokeOvensDTO 删除dto
     */
    void deleteProductionCokeOvensData(DeleteProductionCokeOvensDTO deleteProductionCokeOvensDTO);
}

