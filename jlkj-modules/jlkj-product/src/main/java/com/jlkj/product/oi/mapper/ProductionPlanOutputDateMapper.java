package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.product.oi.domain.ProductionPlanOutputDate;
import com.jlkj.product.oi.domain.ProductionYieldAnalysisDate;
import com.jlkj.product.oi.dto.productionplanoutputdate.ListProductionPlanOutputDateTargetItemDTO;
import com.jlkj.product.oi.vo.productionplanoutputdate.ListProductionPlanOutputDateTargetItemVO;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
* @author zyf
* @description 针对表【product_oi_plan_output_date(计划管理--日产量计划表)】的数据库操作Mapper
* @createDate 2022-04-21 14:46:03
* @Entity com.jlkj.product.oi.entity.ProductionPlanOutputDate
*/
public interface ProductionPlanOutputDateMapper extends BaseMapper<ProductionPlanOutputDate> {

    /**
     * 獲取列表
     * @param sql
     * @return
     */
    @MapKey("id")
    List<Map<String, String>> getList(String sql);

    /**
     * 获取数据
     * @param year
     * @return
     */
    @MapKey("id")
    List<ProductionPlanOutputDate> getOutputDateRelationRepair(int year);

    /**
     * 获取数据
     * @param year
     * @return
     */
    @MapKey("id")
    List<ProductionYieldAnalysisDate> getAnaDateRelationRepair(int year);

    /**
     * 获取数据
     * @param year
     * @param month
     * @return
     */
    @MapKey("id")
    List<ProductionPlanOutputDate> getOutputDateWithMonthRelationRepair(int year, int month);

    /**
     * 获取数据
     * @param year
     * @param month
     * @return
     */
    @MapKey("id")
    List<ProductionYieldAnalysisDate> getAnaDateWithMonthRelationRepair(int year, int month);

    /**
     * 生产管理-指标跟踪-图表-指标项(日计划)
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    List<ListProductionPlanOutputDateTargetItemVO> getProductionPlanOutputDateTargetItemChartData(ListProductionPlanOutputDateTargetItemDTO condition);
}




