package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionReportExcipientConsume;
import com.jlkj.product.oi.dto.productionreportexcipientconsume.PageProductionReportExcipientConsumeDTO;
import org.apache.ibatis.annotations.MapKey;

import java.util.Map;

/**
*@description: 生产管理-报表管理-辅料消耗
*@Author: 265823
*@date: 2023/7/12 9:03
*/
public interface ProductionReportExcipientConsumeMapper extends BaseMapper<ProductionReportExcipientConsume> {

    /**
     * 查询
     * @param page
     * @param condition
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getListPage(Page<Map<String, String>> page, PageProductionReportExcipientConsumeDTO condition);

}


