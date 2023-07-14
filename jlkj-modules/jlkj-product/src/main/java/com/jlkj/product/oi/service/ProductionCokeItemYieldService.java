package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionCokeItemYieldEntity;
import com.jlkj.product.oi.dto.productioncokeitemyield.CokeItemYieldInsertDTO;
import com.jlkj.product.oi.dto.productioncokeitemyield.CokeItemYieldQueryDTO;
import com.jlkj.product.oi.dto.productioncokeitemyield.CokeItemYieldUpdateDTO;

import java.util.Map;

/**
*@description: 煤焦分项产量的服务接口
*@Author: 265823
*@date: 2023/7/10 11:36
*/
public interface ProductionCokeItemYieldService extends IService<ProductionCokeItemYieldEntity> {

    /**
     * 焦炭分项产量-查询
     * @param dto
     * @return
     */
    IPage<Map<String, Object>> getCokeItemYieldList(CokeItemYieldQueryDTO dto);

    /**
     * 焦炭分项产量-查询2
     * @param dto
     * @return
     */
    IPage<Map<String, Object>> getCokeItemYieldList2(CokeItemYieldQueryDTO dto);

    /**
     * 焦炭分项产量-新增
     * @param dto
     * @return
     */
    void insertCokeItemYield(CokeItemYieldInsertDTO dto);

    /**
     * 焦炭分项产量-修改
     * @param dto
     * @return
     */
    void updateCokeItemYield(CokeItemYieldUpdateDTO dto);

    /**
     * 焦炭分项产量-删除
     * @param id
     */
    void deleteCokeItemYield(String id);
}
