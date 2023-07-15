package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionOutputPerformancePlan;
import com.jlkj.product.oi.dto.productionoutputperformanceplan.PageProductionOutputPerformancePlanDTO;
import com.jlkj.product.oi.vo.productionoutputperformanceplan.PageProductionOutputPerformancePlanVO;
import org.apache.ibatis.annotations.MapKey;

/**
 * Mapper接口-生产管理-炼焦实绩-推焦计划-数据同步
 *
 * @author sudeyou
 * @since 2023-04-10 10:11:01
 */
public interface ProductionOutputPerformancePlanMapper extends BaseMapper<ProductionOutputPerformancePlan> {

    /**
     * 生产管理-炼焦实绩-推焦计划-数据同步-查询-分页
     * @param page 分页
     * @param condition 查询条件dto
     * @return 分页数据
     */
    @MapKey("id")
    IPage<PageProductionOutputPerformancePlanVO> getProductionOutputPerformancePlanPageData(Page<?> page, PageProductionOutputPerformancePlanDTO condition);
}
