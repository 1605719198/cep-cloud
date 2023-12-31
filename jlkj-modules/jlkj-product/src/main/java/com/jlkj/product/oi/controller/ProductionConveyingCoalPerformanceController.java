package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productionconveyingcoalperformance.GetProductionConveyingCoalPerformanceDTO;
import com.jlkj.product.oi.dto.productionconveyingcoalperformance.GetProductionStackerReclaimerPerformanceDTO;
import com.jlkj.product.oi.service.ProductionConveyingCoalPerformanceService;
import com.jlkj.product.oi.service.ProductionStackerReclaimerPerformanceService;
import com.jlkj.product.oi.service.impl.ProductionConveyingCoalPerformanceServiceImpl;
import com.jlkj.product.oi.service.impl.ProductionStackerReclaimerPerformanceServiceImpl;
import com.jlkj.product.oi.swaggerdto.ProductionConveyingCoalPerformanceSwagger;
import com.jlkj.product.oi.swaggerdto.ProductionStackerReclaimerPerformanceSwagger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
*@description: 上煤实绩
*@Author: 265823
*@date: 2023/7/10 14:36
*/
@Tag(name = "上煤实绩")
@RestController
@RequestMapping("/performance")
@Slf4j
public class ProductionConveyingCoalPerformanceController {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    ProductionConveyingCoalPerformanceService conveyingCoalPerformanceService;

    @Autowired
    ProductionStackerReclaimerPerformanceService stackerReclaimerPerformanceService;

    /**
     * 上料实绩查询
     * @param dto
     * @return
     */
    @Operation(summary = "上料实绩查询",
            parameters = {
                    @Parameter(name = "shift_name", description = "班次"),
                    @Parameter(name = "class_name", description = "班别"),
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
                    @Parameter(name = "size", description = "10"),
                    @Parameter(name = "current", description = "1"),
                    @Parameter(name = "order", description = "排序字段"),
                    @Parameter(name = "orderby", description = "排序规则")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ProductionConveyingCoalPerformanceSwagger.class))
                    )
            }
    )
    @Log(title = "上料实绩查询",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listMaterialLoadingPerformance", method = RequestMethod.GET)
    public AjaxResult get(@Valid @ParamModel GetProductionConveyingCoalPerformanceDTO dto) {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(conveyingCoalPerformanceService.get(dto));
    }

    /**
     * 堆取料机实绩查询
     * @param dto
     * @return
     */
    @Operation(summary = "堆取料机实绩查询",
            parameters = {
                    @Parameter(name = "shift_name", description = "班次"),
                    @Parameter(name = "class_name", description = "班别"),
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
                    @Parameter(name = "size", description = "10"),
                    @Parameter(name = "current", description = "1"),
                    @Parameter(name = "order", description = "排序字段"),
                    @Parameter(name = "orderby", description = "排序规则")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ProductionStackerReclaimerPerformanceSwagger.class))
                    )
            }
    )
    @Log(title = "堆取料机实绩查询",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listStackerReclaimerPerformance", method = RequestMethod.GET)
    public AjaxResult getStackerReclaimerPerformance(@Valid @ParamModel GetProductionStackerReclaimerPerformanceDTO dto) {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(stackerReclaimerPerformanceService.getStackerReclaimerPerformance(dto));
    }
}
