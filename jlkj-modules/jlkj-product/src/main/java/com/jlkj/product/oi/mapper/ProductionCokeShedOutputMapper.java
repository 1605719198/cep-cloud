package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionCokeShedOutput;
import com.jlkj.product.oi.dto.productioncokeshedoutput.PageProductionCokeShedOutputDTO;
import com.jlkj.product.oi.vo.productioncokeshedoutput.PageProductionCokeShedOutputVO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper接口-焦棚产量维护
 *
 * @author sudeyou
 * @since 2022-10-18 14:57:19
 */
public interface ProductionCokeShedOutputMapper extends BaseMapper<ProductionCokeShedOutput> {

    /**
     * 焦棚产量维护-查询-分页
     * @param page 分页
     * @param condition 查询条件dto
     * @return 分页数据
     */
    @MapKey("id")
    IPage<PageProductionCokeShedOutputVO> getProductionCokeShedOutputPageData(Page<?> page, @Param("condition") PageProductionCokeShedOutputDTO condition);
}

