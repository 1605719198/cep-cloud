package com.jlkj.energy.ee.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.energy.ee.dto.dashboardenergy.GetDashBoardEnergyDTO;
import com.jlkj.energy.ee.service.impl.EnergyBoardView1ServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liukuiyan
 * @Description
 * @create 2023-02-16 14:31
 */
@Tag(name = "能源看板统计")
@RestController
@RequestMapping("/dashboard")
@Slf4j
public class DashboardEnergyController {

    @Autowired
    EnergyBoardView1ServiceImpl energyBoardService;

    @Log(title = "能源看板", businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getEngyTopAnalys", method = RequestMethod.GET)
    public Object getEngyTopAnalys(GetDashBoardEnergyDTO dto) {
        Map<String, Object> engyTop = energyBoardService.getEngyTopAnalys();
        List<Map<String, Object>> engyBottom = energyBoardService.getEngyBottomAnalys(dto);
        List<Object> engyBottomRight = energyBoardService.getEngyBottomRightAnalys(dto);
        Map<String, Object> res = new HashMap<>(1);
        res.put("engyTop", engyTop);
        res.put("engyBottom", engyBottom);
        res.put("engyBottomRight", engyBottomRight);
        return AjaxResult.success(res);
    }
}
