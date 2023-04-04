package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.MaterialsCokeDayStock;
import com.jlkj.product.oi.mapper.MaterialsCokeDayStockMapper;
import com.jlkj.product.oi.service.MaterialsCokeDayStockService;
import org.springframework.stereotype.Service;

/**
 * 服务实现-物料管理-物料焦场每日库存表
 *
 * @author sudeyou
 * @since 2022-12-07 13:40:27
 */
@Service
public class MaterialsCokeDayStockServiceImpl extends ServiceImpl<MaterialsCokeDayStockMapper, MaterialsCokeDayStock>
    implements MaterialsCokeDayStockService {
}
