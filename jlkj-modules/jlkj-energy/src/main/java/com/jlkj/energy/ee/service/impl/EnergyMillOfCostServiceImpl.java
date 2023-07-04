package com.jlkj.energy.ee.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.energy.ee.domain.EnergyMillOfCost;
import com.jlkj.energy.ee.dto.energymillofcost.EnergyMillOfCostDTO;
import com.jlkj.energy.ee.mapper.EnergyMillOfCostMapper;
import com.jlkj.energy.ee.service.EnergyMillOfCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 265675
 * @description 针对表【t_energy_mill_of_cost(产线代码对应成本中心资料)】的数据库操作Service实现
 * @createDate 2022-04-30 08:37:05
 */
@Service
public class EnergyMillOfCostServiceImpl extends ServiceImpl<EnergyMillOfCostMapper, EnergyMillOfCost>
        implements EnergyMillOfCostService {
    @Autowired
    private EnergyMillOfCostMapper energyMillOfCostMapper;

    /**
     * @description 产线代码对应成本中心资料查询与列表
     * @author: 111191
     * @date: 2023年7月4日, 0004 上午 10:37:16
     * @param: energyMillOfCostDTO
     * @return: java.util.List<com.jlkj.energy.ee.domain.EnergyMillOfCost>
     * @throws:
     */
    @Override
    public List<EnergyMillOfCost> queryEnergyMillOfCost(EnergyMillOfCostDTO energyMillOfCostDTO) {
        String millIdCodeStart = energyMillOfCostDTO.getMillIdCodeStart();
        String millIdCodeEnd = energyMillOfCostDTO.getMillIdCodeEnd();
        LambdaQueryWrapper<EnergyMillOfCost> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(millIdCodeStart) && StringUtils.isNotBlank(millIdCodeEnd)) {
            queryWrapper.between(EnergyMillOfCost::getMillIdCode, millIdCodeStart, millIdCodeEnd);
        }
        List<EnergyMillOfCost> records = energyMillOfCostMapper.selectList(queryWrapper);
        return records;
    }

    /**
     * @description 新增产线代码对应成本中心资料
     * @author: 111191
     * @date: 2023年7月4日, 0004 上午 10:49:14
     * @param: energyMillOfCost 产线资料
     * @return: int 新增成功笔数
     * @throws:
     */
    @Override
    public int addEnergyMillOfCost(EnergyMillOfCost energyMillOfCost) {
        LambdaQueryWrapper<EnergyMillOfCost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EnergyMillOfCost::getMillIdCode, energyMillOfCost.getMillIdCode())
                .or().eq(EnergyMillOfCost::getCostCenter, energyMillOfCost.getCostCenter());

        List<EnergyMillOfCost> list = energyMillOfCostMapper.selectList(queryWrapper);
        if (list.size() >= 1) {
            new ServiceException("您输入的工场产线代号与成本中心系统中已存在，请重新输入！");
        }
        return energyMillOfCostMapper.insert(energyMillOfCost);
    }
    /**
     * @description 删除 产线代码对应成本中心资料
     * @author: 111191
     * @date: 2023年7月4日, 0004 上午 10:49:14
     * @param: ids 产线资料ID
     * @return: int 删除 成功笔数
     * @throws:
     */
    @Override
    public int removeBatchByIds(List<String> ids) {
        if(ids.size() < 0){
            new ServiceException("至少选择一笔资料后，再进行操作");
        }
       return energyMillOfCostMapper.deleteBatchIds(ids);
    }
    /**
     * @description 查询产线代码下拉选单
     * @author: 111191
     * @date: 2023年7月4日, 0004 上午 11:34:00
     * @param:
     * @return: com.alibaba.fastjson2.JSONArray
     * @throws:
     */
    @Override
    public JSONArray queryDropDownMenuZh(){
        LambdaQueryWrapper<EnergyMillOfCost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.isNotNull(true, EnergyMillOfCost::getMillIdCode)
                .orderByAsc(EnergyMillOfCost::getMillIdCode);
        List<EnergyMillOfCost> lists = energyMillOfCostMapper.selectList(queryWrapper);
        JSONArray array = new JSONArray();
        if(ObjectUtils.isNotEmpty(lists)){
            for (EnergyMillOfCost energyMillOfCost : lists) {
                JSONObject json = new JSONObject();
                json.put("key", energyMillOfCost.getMillIdCode());
                json.put("value", energyMillOfCost.getMillIdCode());
                json.put("label", energyMillOfCost.getMillIdName());
                array.add(json);
            }
        }
        return array;
    }
}




