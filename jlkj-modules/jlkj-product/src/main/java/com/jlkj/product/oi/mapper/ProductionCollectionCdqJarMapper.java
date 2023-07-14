package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.dto.productioncollectioncdqjar.PageProductionCollectionCdqJarDTO;
import com.jlkj.product.oi.domain.ProductionCollectionCdqJar;
import org.apache.ibatis.annotations.MapKey;

import java.util.Map;

/**
*@description: 生产管理-数据采集-干熄焦-接焦信息
*@Author: 265823
*@date: 2023/7/13 8:01
*/
public interface ProductionCollectionCdqJarMapper extends BaseMapper<ProductionCollectionCdqJar> {

    /**
     * 查询
     * @param page
     * @param condition
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getListPage(Page<Map<String, String>> page, PageProductionCollectionCdqJarDTO condition);

}


