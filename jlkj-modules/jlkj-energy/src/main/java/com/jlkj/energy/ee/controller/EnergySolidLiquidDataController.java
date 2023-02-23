package com.jlkj.energy.ee.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.dto.aop.permission.RoleAndPermission;
import com.jlkj.common.dto.aop.syslog.SysLogTypeEnum;
 
import com.jlkj.common.dto.resp.Result;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.energy.ee.dto.energysolidliquiddata.EnergySolidLiquidDataDTO;
import com.jlkj.energy.ee.domain.EnergySolidLiquidData;
import com.jlkj.energy.ee.service.EnergySolidLiquidDataService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 智能研发室 黄兵
 * @Description 固液体能源量资料操作controller
 * @date 2022/4/28 - 8:41
 */
@RestController
@RequestMapping("/energy/solid/liquid")
public class EnergySolidLiquidDataController {

    @Autowired
    EnergySolidLiquidDataService energySolidLiquidDataService;

    /**
     * 查询固液体能源量资料
     */
    @Log(title = "查询固液体能源量资料", businessType = BusinessType.OTHER)
    @Operation(summary = "查询固液体能源量资料")
    @GetMapping("/query")
    @RoleAndPermission(permission = "solid_liquid_query")
    public Object queryEnergySolidLiquidData(EnergySolidLiquidDataDTO energySolidLiquidDataDTO) {
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
            Page<EnergySolidLiquidData> page = energySolidLiquidDataService.page(new Page<>(pageNum, pageSize), queryWrapper);
            //总记录数
            long total = page.getTotal();
            //数据list集合
            List<EnergySolidLiquidData> records = page.getRecords();
            Map<String,Object> dataMap = new HashMap<>(16);
            dataMap.put("total",total);
            dataMap.put("list",records);
            if (records.isEmpty()){
                return Result.successOne("查无资料", dataMap);
            } else {
                return Result.successOne("查询成功！", dataMap);
            }
        } catch (Exception e) {
            return Result.failed();
        }
    }

    /**
     * 查询能源代码下拉选单
     */
    @Log(title = "查询能源代码下拉选单", businessType = BusinessType.OTHER)
    @Operation(summary = "查询能源代码下拉选单")
    @GetMapping("/queryDropDownMenu")
    public Object queryDropDownMenu() {
        List<EnergySolidLiquidData> list = energySolidLiquidDataService.list();
        List<String> list1 = new ArrayList<>();
        for (EnergySolidLiquidData energySolidLiquidData : list) {
            list1.add(energySolidLiquidData.getEngyId());
        }
        return Result.success(list1);
    }
}
