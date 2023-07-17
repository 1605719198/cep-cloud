package com.jlkj.energy.ee.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.energy.ee.domain.EnergySolidLiquidData;
import com.jlkj.energy.ee.dto.energysolidliquiddata.EnergySolidLiquidDataDTO;

import java.util.List;

/**
* @author 265675
* 针对表【t_energy_solid_liquid_data(固体液体能源量资料)】的数据库操作Service
* @createDate 2022-04-28 08:41:17
*/
public interface EnergySolidLiquidDataService extends IService<EnergySolidLiquidData> {

    /**
     * 查询固液体能源量资料
     * @author 265800
     * @date 2023/7/12 10:40
     * @param energySolidLiquidDataDTO 固体液体能源量资料
     * @return java.util.List<com.jlkj.energy.ee.domain.EnergySolidLiquidData>
     */
    List<EnergySolidLiquidData> queryEnergySolidLiquidData(EnergySolidLiquidDataDTO energySolidLiquidDataDTO);

    /**
     * 查询能源代码下拉选单
     * @author 265800
     * @date 2023/7/12 10:41
     * @return java.util.List<java.lang.String>
     */
    List<String> queryDropDownMenu();
}
