package com.jlkj.energy.ee.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.common.dto.energy.ee.EnergyCodeForMaterialDTO;
import com.jlkj.energy.ee.domain.EnergyCodeFoMaterial;

import java.util.List;

/**
* @author 265675
* @Description: 针对表【t_energy_code_fo_material(固、液体能源代码、料号对照)】的数据库操作Service
* @Date:  2022-04-28 11:25:31
*/

public interface EnergyCodeForMaterialService extends IService<EnergyCodeFoMaterial> {
    /**
     * (固、液体能源代码、料号对照)查询方法
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 09:17:33
     * @param energyCodeForMaterialDTO 查询条件
     * @return java.util.List<com.jlkj.energy.ee.domain.EnergyCodeFoMaterial> 分页结果
     */

    List<EnergyCodeFoMaterial> queryEnergyCodeFoMaterial(EnergyCodeForMaterialDTO energyCodeForMaterialDTO);

}
