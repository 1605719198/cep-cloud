package com.jlkj.energy.ee.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
     * 成本中心日能耗资料查询
     * @Author: 111191
     * @Date: 2023年7月8日, 0008 上午 07:33:33
     * @param energyConsumeOutputDTO 查询条件
     * @return java.util.List<com.jlkj.energy.ee.domain.EnergyConsumeOutput> 查询结果
     */
    List<EnergyConsumeOutput> queryEnergyConsumeOutput(EnergyConsumeOutputDTO energyConsumeOutputDTO);

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
     * 接收报文执行每日能源产耗资料新增
     * @Author: 111191
     * @Date: 2023年7月8日, 0008 上午 08:09:38
     * @param energyConsumeOutputDTO 每日能源产耗资料
     * @return int 新增成功笔数
     */
    int addEnergyConsumeOutput(EnergyConsumeOutput energyConsumeOutput);
}
