package com.jlkj.energy.ee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.energy.ee.domain.EnergySolidLiquidData;
import com.jlkj.energy.ee.dto.energysolidliquiddata.EnergySolidLiquidDataDTO;
import com.jlkj.energy.ee.mapper.EnergySolidLiquidDataMapper;
import com.jlkj.energy.ee.service.EnergySolidLiquidDataService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author 265675
* 针对表【t_energy_solid_liquid_data(固体液体能源量资料)】的数据库操作Service实现
* @createDate 2022-04-28 08:41:17
*/
@Service
public class EnergySolidLiquidDataServiceImpl extends ServiceImpl<EnergySolidLiquidDataMapper, EnergySolidLiquidData>
    implements EnergySolidLiquidDataService{

    /**
     * 查询固液体能源量资料
     * @Author: 265800
     * @Date: 2023/7/12 9:49
     * @Param: [energySolidLiquidDataDTO]
     * @Return: java.util.List<com.jlkj.energy.ee.domain.EnergySolidLiquidData>
     */
    @Override
    public List<EnergySolidLiquidData> queryEnergySolidLiquidData(EnergySolidLiquidDataDTO energySolidLiquidDataDTO) {
        LambdaQueryWrapper<EnergySolidLiquidData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.likeRight(StringUtils.isNotBlank(energySolidLiquidDataDTO.getDateYm()), EnergySolidLiquidData::getEngyDate, energySolidLiquidDataDTO.getDateYm());
        if (StringUtils.isNotBlank(energySolidLiquidDataDTO.getEngyDateStart()) && StringUtils.isNotBlank(energySolidLiquidDataDTO.getEngyDateEnd())) {
            queryWrapper.between(EnergySolidLiquidData::getEngyDate, energySolidLiquidDataDTO.getEngyDateStart(), energySolidLiquidDataDTO.getEngyDateEnd());
        }
        if (StringUtils.isNotBlank(energySolidLiquidDataDTO.getEngyIdStart()) && StringUtils.isNotBlank(energySolidLiquidDataDTO.getEngyIdEnd())) {
            queryWrapper.between(EnergySolidLiquidData::getEngyId, energySolidLiquidDataDTO.getEngyIdStart(), energySolidLiquidDataDTO.getEngyIdEnd());
        } else if (StringUtils.isNotBlank(energySolidLiquidDataDTO.getEngyIdStart())) {
            queryWrapper.eq(EnergySolidLiquidData::getEngyId, energySolidLiquidDataDTO.getEngyIdStart());
        } else if (StringUtils.isNotBlank(energySolidLiquidDataDTO.getEngyIdEnd())) {
            queryWrapper.eq(EnergySolidLiquidData::getEngyId, energySolidLiquidDataDTO.getEngyIdEnd());
        }
        if (StringUtils.isNotBlank(energySolidLiquidDataDTO.getCostCenterStart()) && StringUtils.isNotBlank(energySolidLiquidDataDTO.getCostCenterEnd())) {
            queryWrapper.between(EnergySolidLiquidData::getCostCenter, energySolidLiquidDataDTO.getCostCenterStart(), energySolidLiquidDataDTO.getCostCenterEnd());
        } else if (StringUtils.isNotBlank(energySolidLiquidDataDTO.getCostCenterStart())) {
            queryWrapper.eq(EnergySolidLiquidData::getCostCenter, energySolidLiquidDataDTO.getCostCenterStart());
        } else if (StringUtils.isNotBlank(energySolidLiquidDataDTO.getCostCenterEnd())){
            queryWrapper.eq(EnergySolidLiquidData::getCostCenter, energySolidLiquidDataDTO.getCostCenterEnd());
        }
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 查询能源代码下拉选单
     * @author 265800
     * @date 2023/7/12 10:43
     * @return java.util.List<java.lang.String>
     */
    @Override
    public List<String> queryDropDownMenu() {
        List<EnergySolidLiquidData> list = getBaseMapper().selectList(null);
        List<String> list1 = new ArrayList<>();
        for (EnergySolidLiquidData energySolidLiquidData : list) {
            list1.add(energySolidLiquidData.getEngyId());
        }
        return list1;
    }
}




