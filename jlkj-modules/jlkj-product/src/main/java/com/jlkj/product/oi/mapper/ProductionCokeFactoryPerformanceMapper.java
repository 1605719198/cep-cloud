package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionCokeFactoryPerformance;
import com.jlkj.product.oi.dto.productioncokefactoryperformance.PageProductionCokeFactoryPerformanceDTO;
import com.jlkj.product.oi.vo.productioncokefactoryperformance.PageProductionCokeFactoryPerformanceVO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper接口-焦炭出厂实绩
 * @author sudeyou
 * @since 2022-08-11 14:02:21
 */
public interface ProductionCokeFactoryPerformanceMapper extends BaseMapper<ProductionCokeFactoryPerformance> {

    /**
     * 焦炭出厂实绩-查询-分页
     * @param page 分页
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    IPage<PageProductionCokeFactoryPerformanceVO> getProductionCokeFactoryPerformancePageData(Page<?> page,@Param("condition") PageProductionCokeFactoryPerformanceDTO condition);
}

