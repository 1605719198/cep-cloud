package com.jlkj.material.mr.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.constant.CommonConstant;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.material.mr.dto.materialscokesaledeliverplan.DeleteMaterialsCokeSaleDeliverPlanDTO;
import com.jlkj.material.mr.dto.materialscokesaledeliverplan.InsertMaterialsCokeSaleDeliverPlanDTO;
import com.jlkj.material.mr.dto.materialscokesaledeliverplan.PageMaterialsCokeSaleDeliverPlanDTO;
import com.jlkj.material.mr.domain.MaterialsCokeSaleDeliverPlan;
import com.jlkj.material.mr.mapper.MaterialsCokeSaleDeliverPlanMapper;
import com.jlkj.material.mr.service.MaterialsCokeSaleDeliverPlanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 服务实现-焦炭外售发货计划
 * @author sudeyou
 * @since 2022-08-19 14:00:15
 */
@Service
public class MaterialsCokeSaleDeliverPlanServiceImpl extends ServiceImpl<MaterialsCokeSaleDeliverPlanMapper, MaterialsCokeSaleDeliverPlan> 
    implements MaterialsCokeSaleDeliverPlanService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertMaterialsCokeSaleDeliverPlanData(InsertMaterialsCokeSaleDeliverPlanDTO insertMaterialsCokeSaleDeliverPlanDTO) {
        List<MaterialsCokeSaleDeliverPlan> list = list(new QueryWrapper<MaterialsCokeSaleDeliverPlan>().lambda()
                .eq(MaterialsCokeSaleDeliverPlan::getPlanListNo, insertMaterialsCokeSaleDeliverPlanDTO.getPlanListNo())
                .last(CommonConstant.LIMIT_ONE_ROW)
        );
        if (list.size() > 0) {
            return AjaxResult.error("焦炭外售发货计划已存在");
        }
        MaterialsCokeSaleDeliverPlan materialsCokeSaleDeliverPlan = new MaterialsCokeSaleDeliverPlan();
        materialsCokeSaleDeliverPlan.setId(IdUtil.randomUUID());
        materialsCokeSaleDeliverPlan.setPlanListNo(insertMaterialsCokeSaleDeliverPlanDTO.getPlanListNo());
        materialsCokeSaleDeliverPlan.setCarrierType(insertMaterialsCokeSaleDeliverPlanDTO.getCarrierType());
        materialsCokeSaleDeliverPlan.setCarrierNo(insertMaterialsCokeSaleDeliverPlanDTO.getCarrierNo());
        materialsCokeSaleDeliverPlan.setMtrlNo(insertMaterialsCokeSaleDeliverPlanDTO.getMtrlNo());
        materialsCokeSaleDeliverPlan.setFromCode(insertMaterialsCokeSaleDeliverPlanDTO.getFromCode());
        materialsCokeSaleDeliverPlan.setToCode(insertMaterialsCokeSaleDeliverPlanDTO.getToCode());
        materialsCokeSaleDeliverPlan.setToDesc(insertMaterialsCokeSaleDeliverPlanDTO.getToDesc());
        materialsCokeSaleDeliverPlan.setPchrNo(insertMaterialsCokeSaleDeliverPlanDTO.getPchrNo());
        materialsCokeSaleDeliverPlan.setIsLoad(insertMaterialsCokeSaleDeliverPlanDTO.getIsLoad());
        materialsCokeSaleDeliverPlan.setLoadStation(insertMaterialsCokeSaleDeliverPlanDTO.getLoadStation());
        materialsCokeSaleDeliverPlan.setIssueDept(insertMaterialsCokeSaleDeliverPlanDTO.getIssueDept());
        materialsCokeSaleDeliverPlan.setIssueDeptName(insertMaterialsCokeSaleDeliverPlanDTO.getIssueDeptName());
        materialsCokeSaleDeliverPlan.setIssueEmpNo(insertMaterialsCokeSaleDeliverPlanDTO.getIssueEmpNo());
        materialsCokeSaleDeliverPlan.setIssueEmpName(insertMaterialsCokeSaleDeliverPlanDTO.getIssueEmpName());
        materialsCokeSaleDeliverPlan.setIssueDate(insertMaterialsCokeSaleDeliverPlanDTO.getIssueDate());
        save(materialsCokeSaleDeliverPlan);
        return AjaxResult.success("焦炭外售发货计划增加成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteMaterialsCokeSaleDeliverPlanData(DeleteMaterialsCokeSaleDeliverPlanDTO deleteMaterialsCokeSaleDeliverPlanDTO) {
        boolean succeed = remove(new QueryWrapper<MaterialsCokeSaleDeliverPlan>().lambda()
                .eq(MaterialsCokeSaleDeliverPlan::getPlanListNo, deleteMaterialsCokeSaleDeliverPlanDTO.getPlanListNo()));
        if (succeed) {
            return AjaxResult.success("焦炭外售发货计划删除成功");
        }
        else {
            return AjaxResult.error("数据不存在或已被删除");
        }
    }


    @Override
    public IPage<Map<String, String>> getListPage(PageMaterialsCokeSaleDeliverPlanDTO dto) {
        Page<Map<String, String>> page = new Page<>(dto.getCurrent(), dto.getSize());
        return getBaseMapper().getListPage(page, dto);
    }
}

