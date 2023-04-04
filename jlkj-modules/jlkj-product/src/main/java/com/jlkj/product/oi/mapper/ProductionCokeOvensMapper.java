package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionCokeOvens;
import com.jlkj.product.oi.dto.productioncokeovens.PageProductionCokeOvensDTO;
import com.jlkj.product.oi.vo.productioncokeovens.CoalWaterRateVO;
import com.jlkj.product.oi.vo.productioncokeovens.ListProductionCoalConsumeVO;
import com.jlkj.product.oi.vo.productioncokeovens.ListProductionCokeOvensAccountVO;
import com.jlkj.product.oi.vo.productioncokeovens.PageProductionCokeOvensVO;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;

/**
 * Mapper接口-每日推焦炉数维护
 *
 * @author sudeyou
 * @since 2022-10-18 14:50:42
 */
public interface ProductionCokeOvensMapper extends BaseMapper<ProductionCokeOvens> {

    /**
     * 每日推焦炉数维护-查询-分页
     * @param page 分页
     * @param condition 查询条件dto
     * @return 分页数据
     */
    @MapKey("id")
    IPage<PageProductionCokeOvensVO> getProductionCokeOvensPageData(Page<?> page, PageProductionCokeOvensDTO condition);

    /**
     * 每日推焦炉数维护-账务统计
     * @return 列表数据
     */
    @MapKey("id")
    List<ListProductionCokeOvensAccountVO> getProductionCokeOvensAccountListData();

    /**
     * 获取煤消耗数据
     * @return 列表数据
     */
    @MapKey("id")
    List<ListProductionCoalConsumeVO> getProductionCoalConsumeListData();

    /**
     * 获取水份
     * @return 单行数据
     */
    @MapKey("id")
    CoalWaterRateVO getCoalWaterRateData();
}

