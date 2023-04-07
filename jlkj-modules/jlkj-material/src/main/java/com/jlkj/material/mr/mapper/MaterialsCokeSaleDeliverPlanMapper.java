package com.jlkj.material.mr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.material.mr.dto.materialscokesaledeliverplan.PageMaterialsCokeSaleDeliverPlanDTO;
import com.jlkj.material.mr.domain.MaterialsCokeSaleDeliverPlan;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Mapper接口-焦炭外售发货计划
 * @author sudeyou
 * @since 2022-08-19 14:00:15
 */
public interface MaterialsCokeSaleDeliverPlanMapper extends BaseMapper<MaterialsCokeSaleDeliverPlan> {

    /**
     * 焦炭外售发货计划-列表
     * @param page 分页参数
     * @param condition 查询条件dto
     * @return 分页列表
     */
    @MapKey("id")
    IPage<Map<String, String>> getListPage(Page<Map<String, String>> page, @Param("condition") PageMaterialsCokeSaleDeliverPlanDTO condition);
}

