package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionOutputPerformanceManualBake;
import com.jlkj.product.oi.dto.productionoutputperformancemanualbake.PageProductionOutputPerformanceManualBakeDTO;
import com.jlkj.product.oi.vo.productionoutputperformancemanualbake.PageProductionOutputPerformanceManualBakeVO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper接口-生产管理-炼焦实绩-出炉实绩-手动出炉
 *
 * @author sudeyou
 * @since 2022-12-28 13:35:48
 */
public interface ProductionOutputPerformanceManualBakeMapper extends BaseMapper<ProductionOutputPerformanceManualBake> {

    /**
     * 生产管理-炼焦实绩-出炉实绩-手动出炉-查询-分页
     * @param page 分页
     * @param condition 查询条件dto
     * @return 分页数据
     */
    @MapKey("id")
    IPage<PageProductionOutputPerformanceManualBakeVO> getProductionOutputPerformanceManualBakePageData(Page<?> page,@Param("condition") PageProductionOutputPerformanceManualBakeDTO condition);
}
