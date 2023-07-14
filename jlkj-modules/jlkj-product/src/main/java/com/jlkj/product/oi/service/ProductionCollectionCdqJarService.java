package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.dto.productioncollectioncdqjar.PageProductionCollectionCdqJarDTO;
import com.jlkj.product.oi.domain.ProductionCollectionCdqJar;

import java.util.Map;

/**
*@description: 生产管理-数据采集-干熄焦-接焦信息
*@Author: 265823
*@date: 2023/7/13 7:59
*/
public interface ProductionCollectionCdqJarService extends IService<ProductionCollectionCdqJar> {
    /**
     * 查询生产管理-数据采集-干熄焦-接焦信息
     * @param pageProductionCollectionCdqJarDTO 查询条件dto
     * @return 分页列表
     */
    IPage<Map<String, String>> getListPage(PageProductionCollectionCdqJarDTO pageProductionCollectionCdqJarDTO);
}
