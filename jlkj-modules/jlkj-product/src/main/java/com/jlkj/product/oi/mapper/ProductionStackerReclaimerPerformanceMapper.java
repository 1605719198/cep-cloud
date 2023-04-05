package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.product.oi.domain.ProductionStackerReclaimerPerformance;
import com.jlkj.product.oi.dto.productionstackerreclaimerperformance.InfoSchedulingDTO;
import com.jlkj.product.oi.vo.productionstackerreclaimerperformance.InfoSchedulingVO;
import org.apache.ibatis.annotations.MapKey;

/**
* @author zyf
* @description 针对表【product_oi_stacker_reclaimer_performance(堆取料机实绩)】的数据库操作Mapper
* @createDate 2022-05-11 09:37:46
* @Entity com.jlkj.product.oi.entity.ProductionStackerReclaimerPerformance
*/
public interface ProductionStackerReclaimerPerformanceMapper extends BaseMapper<ProductionStackerReclaimerPerformance> {

    /**
     * 获取排班信息
     * @param condition 查询条件dto
     * @return 单行数据
     */
    @MapKey("id")
    InfoSchedulingVO getUserSchedulingInfoData(InfoSchedulingDTO condition);
}




