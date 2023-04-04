package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.MaterialsCoalDayStock;
import com.jlkj.product.oi.mapper.MaterialsCoalDayStockMapper;
import com.jlkj.product.oi.service.MaterialsCoalDayStockService;
import org.springframework.stereotype.Service;

/**
 * 服务实现-物料管理-物料煤场每日库存表
 *
 * @author sudeyou
 * @since 2022-12-07 13:39:30
 */
@Service
public class MaterialsCoalDayStockServiceImpl extends ServiceImpl<MaterialsCoalDayStockMapper, MaterialsCoalDayStock>
    implements MaterialsCoalDayStockService {
}
