package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productionplanenergyoutput.GetProductionPlanEnergyOutputDTO;
import com.jlkj.product.oi.service.ProductionPlanEnergyOutputService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
*@description: 发电产量
*@Author: 265823
*@date: 2023/7/7 15:39
*/
@Tag(name = "发电产量")
@RestController
@RequestMapping("/plan")
@Slf4j
public class ProductionPlanEnergyOutputController {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    ProductionPlanEnergyOutputService productionPlanEnergyOutputService;


    /**
     * 查询发电产量
     * @param dto
     * @return
     */
    @Operation(summary = "查询发电产量",
            parameters = {
                    @Parameter(name = "planYear", description = "计划年度yyyy"),
                    @Parameter(name = "planMonth", description = "计划月份MM"),
                    @Parameter(name = "size", description = "10"),
                    @Parameter(name = "current", description = "1"),
                    @Parameter(name = "order", description = "排序字段"),
                    @Parameter(name = "orderby", description = "排序规则")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "查询发电产量",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/listEnergyOutputPlans", method = RequestMethod.GET)
    public AjaxResult get(@Valid GetProductionPlanEnergyOutputDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(productionPlanEnergyOutputService.getPageData(dto));
    }

}
