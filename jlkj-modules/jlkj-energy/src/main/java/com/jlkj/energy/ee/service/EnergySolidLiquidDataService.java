package com.jlkj.energy.ee.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.energy.ee.domain.EnergySolidLiquidData;
import com.jlkj.energy.ee.dto.energysolidliquiddata.EnergySolidLiquidDataDTO;

import java.util.List;

/**
* @author 265675
* @description 针对表【t_energy_solid_liquid_data(固体液体能源量资料)】的数据库操作Service
* @createDate 2022-04-28 08:41:17
*/
public interface EnergySolidLiquidDataService extends IService<EnergySolidLiquidData> {

    /**
     * @description 查询固液体能源量资料
     * @author: 265800
     * @date: 2023/7/7 8:31
     * @param: [energySolidLiquidDataDTO]
     * @return: com.jlkj.common.core.web.domain.AjaxResult
     * @throws:
     */
    AjaxResult queryEnergySolidLiquidData(EnergySolidLiquidDataDTO energySolidLiquidDataDTO);

    /**
     * @description 查询能源代码下拉选单
     * @author: 265800
     * @date: 2023/7/7 8:39
     * @param: []
     * @return: java.util.List<java.lang.String>
     * @throws:
     */
    List<String> queryDropDownMenu();
}
