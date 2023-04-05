package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.product.oi.domain.ProductionSteamPerformance;
import com.jlkj.product.oi.dto.productionsteamperformance.ListHomeProductionSteamPerformanceYieldDTO;
import com.jlkj.product.oi.vo.productionsteamperformance.ListHomeProductionSteamPerformanceYieldVO;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;

/**
 * Mapper接口-蒸汽实绩
 * @author sudeyou
 * @since 2022-09-01 09:40:55
 */
public interface ProductionSteamPerformanceMapper extends BaseMapper<ProductionSteamPerformance> {

    /**
     * 首页-蒸汽产量
     * @param condition 查询条件dto
     * @return 列表数据
     */
    @MapKey("id")
    List<ListHomeProductionSteamPerformanceYieldVO> getHomeProductionSteamPerformanceYieldListData(ListHomeProductionSteamPerformanceYieldDTO condition);
}

