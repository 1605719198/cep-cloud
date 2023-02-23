package com.jlkj.energy.ee.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.energy.ee.dto.energyconsumptionoutputaccounting.InsertEnergyConsumptionOutputAccountingDTO;
import com.jlkj.energy.ee.domain.EnergyConsumptionOutputAccounting;

/**
 * 服务接口-能源耗用产出账务信息
 * @author sudeyou
 * @since 2022-09-16 16:10:10
 */
public interface EnergyConsumptionOutputAccountingService extends IService<EnergyConsumptionOutputAccounting> {

    /**
     * 能源耗用产出账务信息-新增
     * @param insertEnergyConsumptionOutputAccountingDTO 新增dto
     * @return 执行结果
     */
    Object insertEnergyConsumptionOutputAccountingData(InsertEnergyConsumptionOutputAccountingDTO insertEnergyConsumptionOutputAccountingDTO);
}

