package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.ProductionLogsName;
import com.jlkj.product.oi.dto.productionlogsname.PageProductionLogsNameDTO;
import com.jlkj.product.oi.mapper.ProductionLogsNameMapper;
import com.jlkj.product.oi.service.ProductionLogsNameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author yzl
 * @create 2022-09-28 10:27:05.808763
 */
@Service
@Slf4j
public class ProductionLogsNameServiceImpl extends ServiceImpl<ProductionLogsNameMapper, ProductionLogsName>
        implements ProductionLogsNameService {
    @Override
    public IPage<Map<String, String>> getListPage(PageProductionLogsNameDTO pageProductionLogsNameDTO) {
        Page<Map<String, String>> page = new Page<>(pageProductionLogsNameDTO.getCurrent(), pageProductionLogsNameDTO.getSize());
        return getBaseMapper().getListPage(page, pageProductionLogsNameDTO);
    }
}

