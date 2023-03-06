package com.jlkj.material.mr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.material.mr.dto.materialscokesaledeliverplan.DeleteMaterialsCokeSaleDeliverPlanDTO;
import com.jlkj.material.mr.dto.materialscokesaledeliverplan.InsertMaterialsCokeSaleDeliverPlanDTO;
import com.jlkj.material.mr.dto.materialscokesaledeliverplan.PageMaterialsCokeSaleDeliverPlanDTO;
import com.jlkj.material.mr.domain.MaterialsCokeSaleDeliverPlan;

import java.util.Map;

/**
 * 服务接口-焦炭外售发货计划
 * @author sudeyou
 * @since 2022-08-19 14:00:15
 */
public interface MaterialsCokeSaleDeliverPlanService extends IService<MaterialsCokeSaleDeliverPlan> {

    /**
     * 焦炭外售发货计划-新增
     * @param insertMaterialsCokeSaleDeliverPlanDTO 新增dto
     * @return 执行结果
     */
    Object insertMaterialsCokeSaleDeliverPlanData(InsertMaterialsCokeSaleDeliverPlanDTO insertMaterialsCokeSaleDeliverPlanDTO);

    /**
     * 焦炭外售发货计划-删除
     * @param deleteMaterialsCokeSaleDeliverPlanDTO 删除dto
     * @return 执行结果
     */
    Object deleteMaterialsCokeSaleDeliverPlanData(DeleteMaterialsCokeSaleDeliverPlanDTO deleteMaterialsCokeSaleDeliverPlanDTO);

    /**
     * 焦炭外售发货计划-列表
     * @param dto 查询条件dto
     * @return 分页列表
     */
    IPage<Map<String, String>> getListPage(PageMaterialsCokeSaleDeliverPlanDTO dto);
}

