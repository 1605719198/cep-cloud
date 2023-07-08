package com.jlkj.energy.ee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.energy.ee.domain.EnergySolidLiquidData;
import com.jlkj.energy.ee.dto.energysolidliquiddata.EnergySolidLiquidDataDTO;
import com.jlkj.energy.ee.mapper.EnergySolidLiquidDataMapper;
import com.jlkj.energy.ee.service.EnergySolidLiquidDataService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 265675
* @description 针对表【t_energy_solid_liquid_data(固体液体能源量资料)】的数据库操作Service实现
* @createDate 2022-04-28 08:41:17
*/
@Service
public class EnergySolidLiquidDataServiceImpl extends ServiceImpl<EnergySolidLiquidDataMapper, EnergySolidLiquidData>
    implements EnergySolidLiquidDataService{

    /**
     * @description 查询固液体能源量资料
     * @author: 265800
     * @date: 2023/7/7 8:30
     * @param: [energySolidLiquidDataDTO]
     * @return: com.jlkj.common.core.web.domain.AjaxResult
     * @throws:
     */
    @Override
    public AjaxResult queryEnergySolidLiquidData(EnergySolidLiquidDataDTO energySolidLiquidDataDTO) {
        try {
            String dateYm = energySolidLiquidDataDTO.getDateYm();
            String engyDateStart = energySolidLiquidDataDTO.getEngyDateStart();
            String engyDateEnd = energySolidLiquidDataDTO.getEngyDateEnd();
            String engyIdStart = energySolidLiquidDataDTO.getEngyIdStart();
            String engyIdEnd = energySolidLiquidDataDTO.getEngyIdEnd();
            String costCenterStart = energySolidLiquidDataDTO.getCostCenterStart();
            String costCenterEnd = energySolidLiquidDataDTO.getCostCenterEnd();
            Long pageNum = energySolidLiquidDataDTO.getPageNum();
            Long pageSize = energySolidLiquidDataDTO.getPageSize();
            LambdaQueryWrapper<EnergySolidLiquidData> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.likeRight(StringUtils.isNotBlank(dateYm), EnergySolidLiquidData::getEngyDate, dateYm);
            if (StringUtils.isNotBlank(engyDateStart) && StringUtils.isNotBlank(engyDateEnd)) {
                queryWrapper.between(EnergySolidLiquidData::getEngyDate, engyDateStart, engyDateEnd);
            }
            if (StringUtils.isNotBlank(engyIdStart) && StringUtils.isNotBlank(engyIdEnd)) {
                queryWrapper.between(EnergySolidLiquidData::getEngyId, engyIdStart, engyIdEnd);
            } else if (StringUtils.isNotBlank(engyIdStart)) {
                queryWrapper.eq(EnergySolidLiquidData::getEngyId, engyIdStart);
            } else if (StringUtils.isNotBlank(engyIdEnd)) {
                queryWrapper.eq(EnergySolidLiquidData::getEngyId, engyIdEnd);
            }
            if (StringUtils.isNotBlank(costCenterStart) && StringUtils.isNotBlank(costCenterEnd)) {
                queryWrapper.between(EnergySolidLiquidData::getCostCenter, costCenterStart, costCenterEnd);
            } else if (StringUtils.isNotBlank(costCenterStart)) {
                queryWrapper.eq(EnergySolidLiquidData::getCostCenter, costCenterStart);
            } else if (StringUtils.isNotBlank(costCenterEnd)){
                queryWrapper.eq(EnergySolidLiquidData::getCostCenter, costCenterEnd);
            }
            Page<EnergySolidLiquidData> page = getBaseMapper().selectPage(new Page<>(pageNum, pageSize), queryWrapper);
            //总记录数
            long total = page.getTotal();
            //数据list集合
            List<EnergySolidLiquidData> records = page.getRecords();
            Map<String,Object> dataMap = new HashMap<>(16);
            dataMap.put("total",total);
            dataMap.put("list",records);
            if (records.isEmpty()){
                return AjaxResult.success("查无资料", dataMap);
            } else {
                return AjaxResult.success("查询成功！", dataMap);
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * @description 查询能源代码下拉选单
     * @author: 265800
     * @date: 2023/7/7 8:39
     * @param: []
     * @return: java.util.List<java.lang.String>
     * @throws:
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




