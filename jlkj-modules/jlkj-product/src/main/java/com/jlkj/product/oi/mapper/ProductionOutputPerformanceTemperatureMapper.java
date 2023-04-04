package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionOutputPerformanceTemperature;
import com.jlkj.product.oi.dto.productionoutputperformancetemperature.PageProductionOutputPerformanceTemperatureDTO;
import com.jlkj.product.oi.vo.productionoutputperformancetemperature.PageProductionOutputPerformanceTemperatureVO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper接口-出炉实绩测温记录
 * @author sudeyou
 * @since 2022-08-11 14:06:15
 */
public interface ProductionOutputPerformanceTemperatureMapper extends BaseMapper<ProductionOutputPerformanceTemperature> {

    /**
     * 出炉实绩测温记录-查询-分页
     * @param page 分页
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    IPage<PageProductionOutputPerformanceTemperatureVO> getProductionOutputPerformanceTemperaturePageData(Page<?> page,@Param("condition") PageProductionOutputPerformanceTemperatureDTO condition);
}

