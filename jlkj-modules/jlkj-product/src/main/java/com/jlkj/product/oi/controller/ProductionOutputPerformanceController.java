package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productionoutputperformance.GetProductionOutputPerformanceDTO;
import com.jlkj.product.oi.service.ProductionOutputPerformanceService;
import com.jlkj.product.oi.swaggerdto.ProductionOutputPerformanceSwagger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
*@description: 出炉实绩
*@Author: 265823
*@date: 2023/7/10 16:59
*/
@Tag(name = "出炉实绩")
@RestController
@RequestMapping("/performance")
@Slf4j
public class ProductionOutputPerformanceController {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    ProductionOutputPerformanceService outputPerformanceService;


    /**
     * 出炉实绩查询
     * @param dto
     * @return
     */
    @Operation(summary = "出炉实绩查询",
            parameters = {
                    @Parameter(name = "shiftName", description = "班次"),
                    @Parameter(name = "className", description = "班别"),
                    @Parameter(name = "startTime", description = "开始时间"),
                    @Parameter(name = "endTime", description = "结束时间"),
                    @Parameter(name = "size", description = "10"),
                    @Parameter(name = "current", description = "1"),
                    @Parameter(name = "order", description = "排序字段"),
                    @Parameter(name = "orderby", description = "排序规则")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ProductionOutputPerformanceSwagger.class))
                    )
            }
    )
    @Log(title = "出炉实绩查询",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listOutputPerformance", method = RequestMethod.GET)
    public AjaxResult get(@Valid GetProductionOutputPerformanceDTO dto) {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(outputPerformanceService.get(dto));
    }
}
