package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productionplantarget.AddProductionPlanYearDTO;
import com.jlkj.product.oi.dto.productionplantarget.DeleteProductionPlanYearDTO;
import com.jlkj.product.oi.service.ProductionParameterTargetItemService;
import com.jlkj.product.oi.service.ProductionPlanTargetYearService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
*@description: 年指标计划
*@Author: 265823
*@date: 2023/7/7 14:39
*/
@Tag(name = "年指标计划")
@RestController
@RequestMapping("/plan")
@Slf4j
public class ProductionPlanTargetYearController {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    ProductionPlanTargetYearService planTargetYearService;

    @Autowired
    ProductionParameterTargetItemService productionParameterTargetItemService;

    /**
     * 年指标计划查询
     * @return
     */
    @Operation(summary = "年指标计划查询",
            parameters = {
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "年指标计划查询",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listYearProductionTargetPlans", method = RequestMethod.GET)
    public AjaxResult get() {
        log.info("params => listYearProductionTargetPlans");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        return AjaxResult.success(planTargetYearService.getList());
    }

    /**
     * 新增年生产指标计划
     * @param productionPlanTargetYearDTO
     */
    @Operation(summary = "新增年生产指标计划",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "planYear", description = "计划年度"),
                            @ExampleObject(name = "targetItems", description = "指标项",
                                    externalValue = "[{'id': 'item_id', 'value': 'item_value'}]"
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
    @Log(title = "新增年生产指标计划",businessType = BusinessType.INSERT)
    @RequestMapping(value = "/saveYearProductionTargetPlan", method = RequestMethod.POST, produces = "application/json")
    public void save(@Valid @RequestBody AddProductionPlanYearDTO productionPlanTargetYearDTO) {
        log.info("params => " + productionPlanTargetYearDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, productionPlanTargetYearDTO);
        planTargetYearService.saveBy(productionPlanTargetYearDTO);
    }

    /**
     * 删除年计划
     * @param deleteProductionPlanYearDTO
     */
    @Operation(summary = "删除年计划",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "planYear", description = "年份")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "1", description = "错误信息"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "删除年计划",businessType = BusinessType.DELETE)
    @RequestMapping(value = "/deleteYearProductionTargetPlan", method = RequestMethod.POST, produces = "application/json")
    public void delete(@Valid @RequestBody DeleteProductionPlanYearDTO deleteProductionPlanYearDTO) {
        log.info("params => " + deleteProductionPlanYearDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteProductionPlanYearDTO);
        planTargetYearService.delete(deleteProductionPlanYearDTO);
    }
}
