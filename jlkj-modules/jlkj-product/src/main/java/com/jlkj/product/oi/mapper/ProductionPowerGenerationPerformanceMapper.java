package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.product.oi.domain.ProductionPowerGenerationPerformance;
import com.jlkj.product.oi.dto.productionpowergenerationperformance.ListHomeProductionPowerGenerationYieldDTO;
import com.jlkj.product.oi.vo.productionpowergenerationperformance.ListHomeProductionPowerGenerationYieldVO;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;

/**
 * Mapper接口-发电实绩
 * @author sudeyou
 * @since 2022-09-01 09:44:19
 */
public interface ProductionPowerGenerationPerformanceMapper extends BaseMapper<ProductionPowerGenerationPerformance> {

    /**
     * 首页-发电量
     * @param condition 查询条件dto
     * @return 列表数据
     */
    @MapKey("id")
    List<ListHomeProductionPowerGenerationYieldVO> getHomeProductionPowerGenerationYieldListData(ListHomeProductionPowerGenerationYieldDTO condition);
}

