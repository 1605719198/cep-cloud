package com.jlkj.product.oi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.ProductionCoefficientRecord;
import com.jlkj.product.oi.dto.productioncoefficientrecord.GetProductionCoefficientRecordDTO;
import com.jlkj.product.oi.mapper.ProductionCoefficientRecordMapper;
import com.jlkj.product.oi.service.ProductionCoefficientRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
* @author zyf
* @description 针对表【product_oi_coefficient_record(焦炉系数记录)】的数据库操作Service实现
* @createDate 2022-08-11 13:52:12
*/
@Service
public class ProductionCoefficientRecordServiceImpl extends ServiceImpl<ProductionCoefficientRecordMapper, ProductionCoefficientRecord>
    implements ProductionCoefficientRecordService {

    /**
     * 焦炉系数记录查询
     * @param dto
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public IPage<Map<String, Object>> get(GetProductionCoefficientRecordDTO dto) {
        Date start = DateUtil.parse(StrUtil.isEmpty(dto.getStartTime()) ? "1790-01-01" : dto.getStartTime() + " 00:00:00");
        Date end = DateUtil.parse(StrUtil.isEmpty(dto.getEndTime()) ? "1790-01-01" : dto.getEndTime() + " 23:59:59");
        QueryWrapper<ProductionCoefficientRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true, "asc".equals(dto.getOrderby()), dto.getOrder())
                .lambda()
                .ge(!StrUtil.isEmpty(dto.getStartTime()), ProductionCoefficientRecord::getMeasureTime, start)
                .le(!StrUtil.isEmpty(dto.getEndTime()), ProductionCoefficientRecord::getMeasureTime, end)
                .eq(!StrUtil.isEmpty(dto.getShiftName()), ProductionCoefficientRecord::getShiftName, dto.getShiftName())
                .eq(!StrUtil.isEmpty(dto.getClassName()), ProductionCoefficientRecord::getClassName, dto.getClassName());
        Page<Map<String, Object>> page = new Page<>(dto.getCurrent(), dto.getSize());
        return pageMaps(page, queryWrapper);
    }
}




