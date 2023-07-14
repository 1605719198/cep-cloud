package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.domain.EnergyCode;
import com.jlkj.product.oi.service.impl.EnergyCodeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
*@description: 能源树
*@Author: 265823
*@date: 2023/7/10 14:50
*/
@Tag(name = "能源树")
@RestController
@RequestMapping("/plan")
@Slf4j
public class ProductionEnergyCodeController {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    EnergyCodeServiceImpl energyCodeService;

    /**
     * 查询能源树
     * @return
     */
    @Operation(summary = "查询能源树",
            parameters = {
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "查询能源树",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/listEnergyCode", method = RequestMethod.GET)
    public AjaxResult get() {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        return AjaxResult.success(energyCodeService.lambdaQuery().orderByAsc(EnergyCode::getEngyType).list());
    }
}
