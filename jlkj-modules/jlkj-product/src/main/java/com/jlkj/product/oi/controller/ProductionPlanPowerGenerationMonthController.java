package com.jlkj.product.oi.controller;


import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productionplantarget.GetProductionPlanMonthDTO;
import com.jlkj.product.oi.dto.productionplantarget.GetProductionPlanOneMonthDTO;
import com.jlkj.product.oi.dto.productionplantarget.UpdateProductionPlanMonthDTO;
import com.jlkj.product.oi.service.impl.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * @author zyf
 * @Description
 * @create 2022-04-29 14:02
 */
@Tag(name = "月发电计划")
@RestController
@RequestMapping("/plan")
@Slf4j
public class ProductionPlanPowerGenerationMonthController {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    ProductionParameterTargetItemServiceImpl productionParameterTargetItemService;

    @Autowired
    ProductionPlanRepairServiceImpl planRepairService;

    @Autowired
    ProductionPlanTargetYearServiceImpl planTargetYearService;

    @Autowired
    ProductionPlanPowerGenerationYearServiceImpl planPowerGenerationYearService;

    @Autowired
    ProductionPlanPowerGenerationMonthServiceImpl planPowerGenerationMonthService;

    @Autowired
    ProductionPlanPowerGenerationDateServiceImpl planPowerGenerationDateService;

    @Operation(summary = "查询月发电计划",
            parameters = {
                    @Parameter(name = "planYear", description = "计划年度")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "查询日发电计划",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/listMonthPowerGenerationTargetPlans", method = RequestMethod.GET)
    public Object get(@Valid GetProductionPlanMonthDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return planPowerGenerationYearService.get(dto);
    }

    @Operation(summary = "查询单条月发电计划",
            parameters = {
                    @Parameter(name = "planYear", description = "计划年度"),
                    @Parameter(name = "planMonth", description = "计划月份")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "查询单条月发电计划",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getMonthPowerGenerationTargetPlan", method = RequestMethod.GET)
    public Object getOne(@Valid GetProductionPlanOneMonthDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return planPowerGenerationYearService.getOne(dto);
    }

    @Operation(summary = "修改月发电计划",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "planYear", description = "计划年度"),
                            @ExampleObject(name = "planMonth", description = "计划月份"),
                            @ExampleObject(name = "targetItems", description = "指标项",
                                    externalValue = "[{'id': 'item_id', 'value': 'item_value', 'calcUnitId': 'item_calc_unit_id_value'}]"
                            ),
                            @ExampleObject(name = "userId", description = "创建人id"),
                            @ExampleObject(name = "userName", description = "创建人")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "1", description = "当年计划已存在"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "修改月发电计划",businessType = BusinessType.UPDATE)
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/updateMonthPowerGenerationTargetPlan", method = RequestMethod.POST, produces = "application/json")
    public Object update(@Valid @RequestBody UpdateProductionPlanMonthDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return planPowerGenerationYearService.update(dto);
    }
}
