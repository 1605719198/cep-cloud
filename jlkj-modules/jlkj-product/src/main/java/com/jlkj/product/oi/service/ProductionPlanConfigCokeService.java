package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionPlanConfigCoke;
import com.jlkj.product.oi.dto.productionplanconfigcoke.*;
import com.jlkj.product.oi.vo.productionplanconfigcoke.PlanNumberVO;

import java.util.Map;

/**
 * 服务接口-配煤计划主记录
 * @author sudeyou
 */
public interface ProductionPlanConfigCokeService extends IService<ProductionPlanConfigCoke> {

    /**
     * 查询-分页-配煤计划主记录
     * @param pageProductionPlanConfigCokeDTO 查询条件dto
     * @return 分页数据
     */
    IPage<Map<String, String>> getPageData(PageProductionPlanConfigCokeDTO pageProductionPlanConfigCokeDTO);

    /**
     * 新增-配煤计划主记录
     * @param addProductionPlanConfigCokeDTO 新增dto
     * @return 执行结果
     */
    Object addData(AddProductionPlanConfigCokeDTO addProductionPlanConfigCokeDTO);

    /**
     * 修改-配煤计划主记录
     * @param updateProductionPlanConfigCokeDTO 修改dto
     * @return 执行结果
     */
    Object updateData(UpdateProductionPlanConfigCokeDTO updateProductionPlanConfigCokeDTO);

    /**
     * 删除-配煤计划主记录
     * @param deleteProductionPlanConfigCokeDTO 删除dto
     * @return 执行结果
     */
    Object deleteData(DeleteProductionPlanConfigCokeDTO deleteProductionPlanConfigCokeDTO);

    /**
     * 确认-配煤计划主记录
     * @param confirmProductionPlanConfigCokeDTO 修改dto
     * @return 执行结果
     */
    Object confirmData(ConfirmProductionPlanConfigCokeDTO confirmProductionPlanConfigCokeDTO);

    /**
     * 配煤计划主记录-计划编号
     * @return 单行数据
     */
    PlanNumberVO getPlanCodeData();
    /**
     * 手动切配煤计划-配煤计划主记录
     * @param dto dto
     * @return 执行结果
     */
    Object changeProductionPlanCfgCokeConfirm(ChangeProductionPlanCfgCokeDTO dto);

}

