package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionPlanConfigCokeDetail;
import com.jlkj.product.oi.dto.productionplanconfigcokedetail.*;
import com.jlkj.product.oi.vo.productionplanconfigcokedetail.InfoProductionPlanConfigCokeDetailByWarehouseNumberVO;

import java.util.Map;

/**
 * 服务接口-配煤计划配煤记录
 * @author sudeyou
 */
public interface ProductionPlanConfigCokeDetailService extends IService<ProductionPlanConfigCokeDetail> {

    /**
     * 查询-分页-配煤计划配煤记录
     * @param pageProductionPlanConfigCokeDetailDTO 查询条件dto
     * @return 分页数据
     */
    IPage<Map<String, String>> getPageData(PageProductionPlanConfigCokeDetailDTO pageProductionPlanConfigCokeDetailDTO);

    /**
     * 新增-配煤计划配煤记录
     * @param addProductionPlanConfigCokeDetailDTO 新增dto
     * @return 执行结果
     */
    Object addData(AddProductionPlanConfigCokeDetailDTO addProductionPlanConfigCokeDetailDTO);

    /**
     * 修改-配煤计划配煤记录
     * @param updateProductionPlanConfigCokeDetailDTO 修改dto
     * @return 执行结果
     */
    Object updateData(UpdateProductionPlanConfigCokeDetailDTO updateProductionPlanConfigCokeDetailDTO);

    /**
     * 删除-配煤计划配煤记录
     * @param deleteProductionPlanConfigCokeDetailDTO 删除dto
     * @return 执行结果
     */
    Object deleteData(DeleteProductionPlanConfigCokeDetailDTO deleteProductionPlanConfigCokeDetailDTO);
    /**
     * 删除-配煤计划配煤记录(指定计划)
     * @param planId 计划id
     * @return 执行结果
     */
    Boolean deleteDataByPlanId(String planId);

    /**
     * 获取配煤仓配煤计划信息
     * @param infoProductionPlanConfigCokeDetailByWarehouseNumberDTO 查询条件dto
     * @return 单行数据
     */
    InfoProductionPlanConfigCokeDetailByWarehouseNumberVO getProductionPlanConfigCokeDetailInfoByWarehouseNumberData(InfoProductionPlanConfigCokeDetailByWarehouseNumberDTO infoProductionPlanConfigCokeDetailByWarehouseNumberDTO);
}

