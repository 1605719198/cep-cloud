package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionCoefficientRecord;
import com.jlkj.product.oi.dto.productioncoefficientrecord.GetProductionCoefficientRecordDTO;

import java.util.Map;

/**
* @author zyf
* @description 针对表【product_oi_coefficient_record(焦炉系数记录)】的数据库操作Service
* @createDate 2022-08-11 13:52:12
*/
public interface ProductionCoefficientRecordService extends IService<ProductionCoefficientRecord> {

    /**
     * 焦炉系数记录查询
     * @param dto
     * @return
     */
    IPage<Map<String, Object>> get(GetProductionCoefficientRecordDTO dto);
}
