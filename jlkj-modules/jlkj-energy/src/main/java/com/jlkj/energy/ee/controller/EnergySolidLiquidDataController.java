package com.jlkj.energy.ee.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.energy.ee.dto.energysolidliquiddata.EnergySolidLiquidDataDTO;
import com.jlkj.energy.ee.service.EnergySolidLiquidDataService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 智能研发室 黄兵
 * 固液体能源量资料操作controller
 * @date 2022/4/28 - 8:41
 */
@RestController
@RequestMapping("/energy/solid/liquid")
public class EnergySolidLiquidDataController extends BaseController {

    @Autowired
    EnergySolidLiquidDataService energySolidLiquidDataService;

    /**
     * 查询固液体能源量资料
     * @author 265800
     * @date 2023/7/12 11:03
     * @param energySolidLiquidDataDTO EnergySolidLiquidDataDTO
     * @return com.jlkj.common.core.web.page.TableDataInfo
     */
    @Log(title = "查询固液体能源量资料", businessType = BusinessType.OTHER)
    @Operation(summary = "查询固液体能源量资料")
    @GetMapping("/query")
    public TableDataInfo queryEnergySolidLiquidData(EnergySolidLiquidDataDTO energySolidLiquidDataDTO) {
        startPage();
        return getDataTable(energySolidLiquidDataService.queryEnergySolidLiquidData(energySolidLiquidDataDTO));
    }

    /**
     * 查询能源代码下拉选单
     * @author 265800
     * @date 2023/7/12 11:04
     * @return java.lang.Object
     */
    @Log(title = "查询能源代码下拉选单", businessType = BusinessType.OTHER)
    @Operation(summary = "查询能源代码下拉选单")
    @GetMapping("/queryDropDownMenu")
    public Object queryDropDownMenu() {
        List<String> list1 = energySolidLiquidDataService.queryDropDownMenu();
        return AjaxResult.success(list1);
    }
}
