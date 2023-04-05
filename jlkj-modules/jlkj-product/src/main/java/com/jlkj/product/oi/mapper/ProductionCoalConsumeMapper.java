package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionCoalConsume;
import com.jlkj.product.oi.dto.productioncoalconsume.PageProductionCoalConsumeDTO;
import com.jlkj.product.oi.vo.productioncoalconsume.PageProductionCoalConsumeVO;
import org.apache.ibatis.annotations.MapKey;

/**
 * Mapper接口-炼焦煤消耗维护
 *
 * @author sudeyou
 * @since 2022-11-03 17:10:54
 */
public interface ProductionCoalConsumeMapper extends BaseMapper<ProductionCoalConsume> {

    /**
     * 炼焦煤消耗维护-查询-分页
     * @param page 分页
     * @param condition 查询条件dto
     * @return 分页数据
     */
    @MapKey("id")
    IPage<PageProductionCoalConsumeVO> getProductionCoalConsumePageData(Page<?> page, PageProductionCoalConsumeDTO condition);
}

