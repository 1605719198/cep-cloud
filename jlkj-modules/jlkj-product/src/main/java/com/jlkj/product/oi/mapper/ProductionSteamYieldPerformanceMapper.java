package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionSteamYieldPerformanceEntity;
import com.jlkj.product.oi.dto.productionsteamyieldperformance.ProductionSteamYieldPerformanceDTO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * mapper 接口 发电实绩中的蒸汽产量实绩
 * @author zzh
 * @since 2022年9月8日14:16:33
 */
public interface ProductionSteamYieldPerformanceMapper extends BaseMapper<ProductionSteamYieldPerformanceEntity> {

    /**
     * 蒸汽产量实绩
     * @param page
     * @param condition
     * @return
     */
    @MapKey("id")
    IPage<Map<String, Object>> getListPage(Page<?> page,@Param("condition") ProductionSteamYieldPerformanceDTO condition);
}
