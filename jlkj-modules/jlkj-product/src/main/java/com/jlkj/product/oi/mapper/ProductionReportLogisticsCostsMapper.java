package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionReportLogisticsCosts;
import com.jlkj.product.oi.dto.productionreportlogisticscosts.PageProductionReportLogisticsCostsDTO;
import org.apache.ibatis.annotations.MapKey;

import java.util.Map;

/**
*@description: 生产管理-报表管理-物流费用
*@Author: 265823
*@date: 2023/7/12 10:27
*/
public interface ProductionReportLogisticsCostsMapper extends BaseMapper<ProductionReportLogisticsCosts> {

    /**
     * 查询
     * @param page
     * @param condition
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getListPage(Page<Map<String, String>> page, PageProductionReportLogisticsCostsDTO condition);

}


