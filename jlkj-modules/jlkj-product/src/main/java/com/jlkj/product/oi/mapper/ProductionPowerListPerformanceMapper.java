package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionPowerListPerformanceEntity;
import com.jlkj.product.oi.dto.productionpowerlistperformance.ProductionPowerYieldListPerformanceDTO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * mapper 接口 发电实绩中的发电量实绩列表
 * @author zzh
 * @since 2022年9月7日15:13:56
 */
public interface ProductionPowerListPerformanceMapper extends BaseMapper<ProductionPowerListPerformanceEntity> {

    /**
     * 发电产量实绩
     * @param page
     * @param condition
     * @return
     */
    @MapKey("id")
    IPage<Map<String, Object>> getListPage(Page<?> page,@Param("condition") ProductionPowerYieldListPerformanceDTO condition);

}
