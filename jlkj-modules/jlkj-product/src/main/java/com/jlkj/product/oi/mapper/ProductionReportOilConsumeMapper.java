package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionReportOilConsume;
import com.jlkj.product.oi.dto.productionreportoilconsume.PageProductionReportOilConsumeDTO;
import org.apache.ibatis.annotations.MapKey;

import java.util.Map;

/**
 * @author yzl
 * @create 2023-04-13 13:27:01.945073
 */
public interface ProductionReportOilConsumeMapper extends BaseMapper<ProductionReportOilConsume> {

    /**
     * 查询
     * @param page
     * @param condition
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getListPage(Page<Map<String, String>> page, PageProductionReportOilConsumeDTO condition);

}


