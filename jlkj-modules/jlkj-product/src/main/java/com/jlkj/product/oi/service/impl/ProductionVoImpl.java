package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jlkj.product.oi.domain.ProductionParameterTargetItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zyf
 * @Description
 * @create 2022-08-31 10:41
 */
@Service
public class ProductionVoImpl {

    @Autowired
    ProductionParameterTargetItemServiceImpl targetItemService;

    public List<ProductionParameterTargetItem> getItems(List<String> itemIds) {
        return targetItemService.list(new LambdaQueryWrapper<ProductionParameterTargetItem>()
                .in(ProductionParameterTargetItem::getId, itemIds));
    }
}
