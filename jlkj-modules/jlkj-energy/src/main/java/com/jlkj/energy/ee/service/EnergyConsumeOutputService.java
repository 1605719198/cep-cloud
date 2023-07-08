package com.jlkj.energy.ee.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.energy.ee.EnergyConsumeOutputDTO;
import com.jlkj.energy.ee.domain.EnergyConsumeOutput;

import java.util.List;

/**
* @author 265675
* @description 针对表【t_energy_consume_output(能源项目基本资料)】的数据库操作Service
* @createDate 2022-05-05 08:33:10
*/
public interface EnergyConsumeOutputService extends IService<EnergyConsumeOutput> {

    /**
     * @description 成本中心日能耗资料查询
     * @author: 265800
     * @date: 2023/7/7 9:50
     * @param: [energyConsumeOutputDTO]
     * @return: com.jlkj.common.core.web.domain.AjaxResult
     * @throws:
     */
    AjaxResult queryEnergyConsumeOutput(EnergyConsumeOutputDTO energyConsumeOutputDTO);

    /**
     * @description 查询成本中心日能耗资料能源代码下拉选单
     * @author: 265800
     * @date: 2023/7/7 10:05
     * @param: []
     * @return: java.util.List<java.lang.String>
     * @throws:
     */
    List<String> queryDropDownMenu();

    /**
     * @description 接收报文执行每日能源产耗资料新增
     * @author: 265800
     * @date: 2023/7/7 10:34
     * @param: [energyConsumeOutputDTO]
     * @return: com.jlkj.common.core.web.domain.AjaxResult
     * @throws:
     */
    AjaxResult addEnergyConsumeOutput(EnergyConsumeOutputDTO energyConsumeOutputDTO);
}
