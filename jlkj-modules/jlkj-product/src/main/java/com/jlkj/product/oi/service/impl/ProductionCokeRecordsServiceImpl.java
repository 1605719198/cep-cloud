package com.jlkj.product.oi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.ProductionCokeRecords;
import com.jlkj.product.oi.dto.productioncoefficientrecord.GetProductionCoefficientRecordDTO;
import com.jlkj.product.oi.dto.productioncokerecords.ListHomeCokeProportionDTO;
import com.jlkj.product.oi.mapper.ProductionCokeRecordsMapper;
import com.jlkj.product.oi.service.ProductionCokeRecordsService;
import com.jlkj.product.oi.vo.productioncokerecords.ListHomeCokeProportionVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author zyf
* @description 针对表【product_oi_coke_records(焦炭产量记录)】的数据库操作Service实现
* @createDate 2022-08-11 13:52:37
*/
@Service
public class ProductionCokeRecordsServiceImpl extends ServiceImpl<ProductionCokeRecordsMapper, ProductionCokeRecords>
    implements ProductionCokeRecordsService {


    public IPage<Map<String, Object>> get(GetProductionCoefficientRecordDTO dto) {
        Date start = DateUtil.parse(StrUtil.isEmpty(dto.getStartTime()) ? "1790-01-01" : dto.getStartTime() + " 00:00:00");
        Date end = DateUtil.parse(StrUtil.isEmpty(dto.getEndTime()) ? "1790-01-01" : dto.getEndTime() + " 23:59:59");
        QueryWrapper<ProductionCokeRecords> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true, "asc".equals(dto.getOrderby()), dto.getOrder())
                .lambda()
                .ge(!StrUtil.isEmpty(dto.getStartTime()), ProductionCokeRecords::getClassStartTime, start)
                .le(!StrUtil.isEmpty(dto.getEndTime()), ProductionCokeRecords::getClassStartTime, end)
                .eq(!StrUtil.isEmpty(dto.getShiftName()), ProductionCokeRecords::getShiftName, dto.getShiftName())
                .eq(!StrUtil.isEmpty(dto.getClassName()), ProductionCokeRecords::getClassName, dto.getClassName());
        Page<Map<String, Object>> page = new Page<>(dto.getCurrent(), dto.getSize());
        return pageMaps(page, queryWrapper);
    }
    @Override
    @Transactional(readOnly = true)
    public List<ListHomeCokeProportionVO> getHomeCokeProportionListData(ListHomeCokeProportionDTO listHomeCokeProportionDTO) {
        return getBaseMapper().getHomeCokeProportionListData(listHomeCokeProportionDTO);
    }
}




