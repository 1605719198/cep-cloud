package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productioncoefficientrecord.GetProductionCoefficientRecordDTO;
import com.jlkj.product.oi.service.ProductionCoefficientRecordService;
import com.jlkj.product.oi.swaggerdto.ProductionCoefficientRecordSwagger;
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
*@description: 焦炉系数记录
*@Author: 265823
*@date: 2023/7/10 11:30
*/
@Tag(name = "焦炉系数记录")
@RestController
@RequestMapping("/coefficientRecord")
@Slf4j
public class ProductionCoefficientRecordController {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    ProductionCoefficientRecordService coefficientRecordService;

    /**
     * 焦炉系数记录查询
     * @param dto
     * @return
     */
    @Operation(summary = "焦炉系数记录查询",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
                    @Parameter(name = "shift_name", description = "班次"),
                    @Parameter(name = "class_name", description = "班别"),
                    @Parameter(name = "size", description = "10"),
                    @Parameter(name = "current", description = "1"),
                    @Parameter(name = "order", description = "排序字段"),
                    @Parameter(name = "orderby", description = "排序规则")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ProductionCoefficientRecordSwagger.class)))
            }
    )
    @Log(title = "焦炉系数记录查询",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionCoefficientRecord", method = RequestMethod.GET)
    public AjaxResult get(@Valid @ParamModel GetProductionCoefficientRecordDTO dto) {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(coefficientRecordService.get(dto));
    }
}
