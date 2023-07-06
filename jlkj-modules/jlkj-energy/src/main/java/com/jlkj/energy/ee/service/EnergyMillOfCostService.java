package com.jlkj.energy.ee.service;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.energy.ee.domain.EnergyMillOfCost;
import com.jlkj.energy.ee.dto.energymillofcost.EnergyMillOfCostDTO;

import java.util.List;

/**
* @author 265675
* @description 针对表【t_energy_mill_of_cost(产线代码对应成本中心资料)】的数据库操作Service
* @createDate 2022-04-30 08:37:05
*/
public interface EnergyMillOfCostService extends IService<EnergyMillOfCost> {
    /**
     * @description 产线代码对应成本中心资料查询与列表
     * @author: 111191
     * @date: 2023年7月4日, 0004 上午 10:37:26
     * @param: energyMillOfCostDTO 产线代码
     * @return: java.util.List<com.jlkj.energy.ee.domain.EnergyMillOfCost>
    */
    List<EnergyMillOfCost> queryEnergyMillOfCost(EnergyMillOfCostDTO energyMillOfCostDTO);
    /**
     * @description 新增产线代码对应成本中心资料
     * @author: 111191
     * @date: 2023年7月4日, 0004 上午 10:48:28
     * @param: energyMillOfCost 产线资料
     * @return: int 新增成功笔数
    */
    int addEnergyMillOfCost(EnergyMillOfCost energyMillOfCost);
    /**
     * @description 删除产线代码对应成本中心资料
     * @author: 111191
     * @date: 2023年7月4日, 0004 上午 10:57:55
     * @param: ids 产线ID
     * @return: int 删除成功笔数
     * @throws:
    */
    int  removeBatchByIds(List<String> ids);
    /**
     * @description 查询产线代码下拉选单
     * @author: 111191
     * @date: 2023年7月4日, 0004 上午 11:34:09
     * @param:
     * @return: com.alibaba.fastjson2.JSONArray
     * @throws:
     */
    JSONArray queryDropDownMenuZh();
}
