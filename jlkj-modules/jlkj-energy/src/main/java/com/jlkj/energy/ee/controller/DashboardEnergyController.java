package com.jlkj.energy.ee.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.energy.ee.dto.dashboardenergy.GetDashBoardEnergyDTO;
import com.jlkj.energy.ee.service.EnergyBoardView1Service;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author liukuiyan
 * @create 2023-02-16 14:31
 */
@Tag(name = "能源看板统计")
@RestController
@RequestMapping("/dashboard")
@Slf4j
public class DashboardEnergyController {

    @Autowired
    EnergyBoardView1Service energyBoardService;

    /**
     * 能源看板数据查询
     * @author 265800
     * @date 2023/7/12 10:48
     * @param dto 能源看板查询参数
     * @return java.lang.Object
     */
    @Log(title = "能源看板", businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getEngyTopAnalys", method = RequestMethod.GET)
    public Object getEngyTopAnalys(GetDashBoardEnergyDTO dto) {
        Map<String, Object> res = energyBoardService.getEngyTopAnalys(dto);
        return AjaxResult.success(res);
    }
}
