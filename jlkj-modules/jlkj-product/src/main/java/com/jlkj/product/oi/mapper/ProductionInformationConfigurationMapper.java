package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionInformationConfiguration;
import com.jlkj.product.oi.dto.productioninformationconfiguration.PageProductionInformationConfigurationDTO;
import com.jlkj.product.oi.vo.productioninformationconfiguration.PageProductionInformationConfigurationVO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper接口-生产信息配置
 *
 * @author sudeyou
 * @since 2022-10-18 14:47:44
 */
public interface ProductionInformationConfigurationMapper extends BaseMapper<ProductionInformationConfiguration> {

    /**
     * 生产信息配置-查询-分页
     * @param page 分页
     * @param condition 查询条件dto
     * @return 分页数据
     */
    @MapKey("id")
    IPage<PageProductionInformationConfigurationVO> getProductionInformationConfigurationPageData(Page<?> page, @Param("condition") PageProductionInformationConfigurationDTO condition);
}

