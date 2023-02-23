package com.jlkj.energy.ee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.common.dto.resp.Result;
import com.jlkj.energy.ee.dto.energyconsumptionoutputaccounting.InsertEnergyConsumptionOutputAccountingDTO;
import com.jlkj.energy.ee.domain.EnergyConsumptionOutputAccounting;
import com.jlkj.energy.ee.mapper.EnergyConsumptionOutputAccountingMapper;
import com.jlkj.energy.ee.service.EnergyConsumptionOutputAccountingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务实现-能源耗用产出账务信息
 * @author sudeyou
 * @since 2022-09-16 16:10:10
 */
@Service
public class EnergyConsumptionOutputAccountingServiceImpl extends ServiceImpl<EnergyConsumptionOutputAccountingMapper, EnergyConsumptionOutputAccounting> 
    implements EnergyConsumptionOutputAccountingService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertEnergyConsumptionOutputAccountingData(InsertEnergyConsumptionOutputAccountingDTO insertEnergyConsumptionOutputAccountingDTO) {
        EnergyConsumptionOutputAccounting energyConsumptionOutputAccounting = new EnergyConsumptionOutputAccounting();
        energyConsumptionOutputAccounting.setId(UUID.randomUUID().toString());
        energyConsumptionOutputAccounting.setCostCenter(insertEnergyConsumptionOutputAccountingDTO.getCostCenter());
        energyConsumptionOutputAccounting.setFactoryId(insertEnergyConsumptionOutputAccountingDTO.getFactoryId());
        energyConsumptionOutputAccounting.setMillId(insertEnergyConsumptionOutputAccountingDTO.getMillId());
        energyConsumptionOutputAccounting.setEountDate(insertEnergyConsumptionOutputAccountingDTO.getEountDate());
        energyConsumptionOutputAccounting.setEngyId(insertEnergyConsumptionOutputAccountingDTO.getEngyId());
        energyConsumptionOutputAccounting.setIoTypeId(insertEnergyConsumptionOutputAccountingDTO.getIoTypeId());
        energyConsumptionOutputAccounting.setEngyUnitF(insertEnergyConsumptionOutputAccountingDTO.getEngyUnitF());
        energyConsumptionOutputAccounting.setProConQty(insertEnergyConsumptionOutputAccountingDTO.getProConQty());
        energyConsumptionOutputAccounting.setCalValue(insertEnergyConsumptionOutputAccountingDTO.getCalValue());
        energyConsumptionOutputAccounting.setEngyValue(insertEnergyConsumptionOutputAccountingDTO.getEngyValue());
        energyConsumptionOutputAccounting.setChargeCostCenter(insertEnergyConsumptionOutputAccountingDTO.getChargeCostCenter());
        energyConsumptionOutputAccounting.setChargeFactoryId(insertEnergyConsumptionOutputAccountingDTO.getChargeFactoryId());
        save(energyConsumptionOutputAccounting);
        return Result.success("能源耗用产出账务信息增加成功");
    }
}

