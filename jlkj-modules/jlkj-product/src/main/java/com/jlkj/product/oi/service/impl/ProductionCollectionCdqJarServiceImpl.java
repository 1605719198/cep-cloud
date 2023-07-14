package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.dto.productioncollectioncdqjar.PageProductionCollectionCdqJarDTO;
import com.jlkj.product.oi.domain.ProductionCollectionCdqJar;
import com.jlkj.product.oi.mapper.ProductionCollectionCdqJarMapper;
import com.jlkj.product.oi.service.ProductionCollectionCdqJarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
*@description: 生产管理-数据采集-干熄焦-接焦信息
*@Author: 265823
*@date: 2023/7/13 8:00
*/
@Service
@Slf4j
public class ProductionCollectionCdqJarServiceImpl extends ServiceImpl<ProductionCollectionCdqJarMapper, ProductionCollectionCdqJar>
        implements ProductionCollectionCdqJarService {
    /**
     * 查询生产管理-数据采集-干熄焦-接焦信息
     * @param pageProductionCollectionCdqJarDTO 查询条件dto
     * @return
     */
    @Override
    public IPage<Map<String, String>> getListPage(PageProductionCollectionCdqJarDTO pageProductionCollectionCdqJarDTO) {
        Page<Map<String, String>> page = new Page<>(pageProductionCollectionCdqJarDTO.getCurrent(), pageProductionCollectionCdqJarDTO.getSize());
        return getBaseMapper().getListPage(page, pageProductionCollectionCdqJarDTO);
    }
}

