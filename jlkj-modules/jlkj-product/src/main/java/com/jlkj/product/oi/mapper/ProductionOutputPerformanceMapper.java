package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.product.oi.domain.ProductionOutputPerformance;
import com.jlkj.product.oi.dto.dashboardindex.GetCokePlanPerformanceDTO;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
* @author zyf
* @description 针对表【product_oi_output_performance(出炉实绩)】的数据库操作Mapper
* @createDate 2022-05-11 14:37:05
* @Entity com.jlkj.product.oi.entity.ProductionOutputPerformance
*/
public interface ProductionOutputPerformanceMapper extends BaseMapper<ProductionOutputPerformance> {

    /**
     * 接口
     * @param dto
     * @return
     */
    @MapKey("id")
    List<Map<String, Object>> getCokePlanAnalys(GetCokePlanPerformanceDTO dto);

    /**
     * 接口
     * @param dto
     * @return
     */
    @MapKey("id")
    List<Map<String, Object>> getCokePerformanceAnalys(GetCokePlanPerformanceDTO dto);


    /**
     * 接口
     * @param dto
     * @return
     */
    @MapKey("id")
    List<Map<String, Object>> getCokeOutputAnalysByLevel(GetCokePlanPerformanceDTO dto);


    /**
     * 接口
     * @param dto
     * @return
     */
    @MapKey("id")
    List<Map<String, Object>> getCokeOutputAnalysByStove(GetCokePlanPerformanceDTO dto);

    /**
     * 接口
     * @param dto
     * @return
     */
    @MapKey("id")
    List<Map<String, Object>> getProductivity(GetCokePlanPerformanceDTO dto);
}




