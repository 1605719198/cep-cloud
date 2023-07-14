package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionReportUnloading;
import com.jlkj.product.oi.dto.productionreportunloading.PageProductionReportUnloadingDTO;
import org.apache.ibatis.annotations.MapKey;

import java.util.Map;

/**
*@description: 生产管理-报表管理-煤场卸车情况
*@Author: 265823
*@date: 2023/7/12 9:29
*/
public interface ProductionReportUnloadingMapper extends BaseMapper<ProductionReportUnloading> {

    /**
     * 查询
     * @param page
     * @param condition
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getListPage(Page<Map<String, String>> page, PageProductionReportUnloadingDTO condition);

}


