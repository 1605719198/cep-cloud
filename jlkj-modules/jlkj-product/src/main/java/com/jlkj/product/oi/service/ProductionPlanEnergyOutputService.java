package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.EnergyMonthPlanOutput;
import com.jlkj.product.oi.dto.productionplanenergyoutput.GetProductionPlanEnergyOutputDTO;

/**
 * @author yzl
 * @description 发电产量
 * @createDate 2022年9月6日 09:14:40
 */
public interface ProductionPlanEnergyOutputService  extends IService<EnergyMonthPlanOutput> {
    /**
     * 查询-分页-发电产量
     * @param dto 查询条件dto
     * @return 分页数据
     */
    Object getPageData(GetProductionPlanEnergyOutputDTO dto);

}
