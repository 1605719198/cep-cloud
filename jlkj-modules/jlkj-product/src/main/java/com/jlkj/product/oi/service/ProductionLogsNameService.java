package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionLogsName;
import com.jlkj.product.oi.dto.productionlogsname.PageProductionLogsNameDTO;

import java.util.Map;

/**
 * @author yzl
 * @create 2022-09-28 10:27:05.808763
 */
public interface ProductionLogsNameService extends IService<ProductionLogsName> {
    /**
     * 列表
     * @param pageProductionLogsNameDTO 查询条件dto
     * @return 分页列表
     */
    IPage<Map<String, String>> getListPage(PageProductionLogsNameDTO pageProductionLogsNameDTO);
}
