package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionConveyingCoalRecord;
import com.jlkj.product.oi.dto.productionconveyingcoalrecord.InsertProductionConveyingCoalRecordDTO;
import com.jlkj.product.oi.dto.productionconveyingcoalrecord.PageProductionConveyingCoalRecordDTO;
import com.jlkj.product.oi.vo.productionconveyingcoalrecord.PageProductionConveyingCoalRecordVO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Mapper接口-生产实绩-上煤记录
 *
 * @author sudeyou
 * @since 2022-11-17 15:18:05
 */
public interface ProductionConveyingCoalRecordMapper extends BaseMapper<ProductionConveyingCoalRecord> {
    /**
     * 生产实绩-上煤记录-查询-分页
     * @param page 分页
     * @param condition 查询条件dto
     * @return 分页数据
     */
    @MapKey("id")
    IPage<PageProductionConveyingCoalRecordVO> getProductionConveyingCoalRecordPageData(Page<?> page,@Param("condition") PageProductionConveyingCoalRecordDTO condition);

    /**
     * 获取当班时间内最新的质量数据
     * @param condition 条件dto
     * @return 分页数据
     */
    @MapKey("id")
    List<Map<String,Object>> getQualityTsRecordByTeam(InsertProductionConveyingCoalRecordDTO condition);
}
