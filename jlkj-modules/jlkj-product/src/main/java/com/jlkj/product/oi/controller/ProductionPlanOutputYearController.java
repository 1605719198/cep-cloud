package com.jlkj.product.oi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.domain.*;
import com.jlkj.product.oi.dto.productionplantarget.AddProductionPlanYearDTO;
import com.jlkj.product.oi.dto.productionplantarget.DeleteProductionPlanYearDTO;
import com.jlkj.product.oi.service.*;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * @author zyf
 * @Description
 * @create 2022-04-26 10:33
 */
@Tag(name = "年产量计划")
@RestController
@RequestMapping("/plan")
@Slf4j
public class ProductionPlanOutputYearController {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    ProductionParameterTargetItemService productionParameterTargetItemService;

    @Autowired
    ProductionPlanOutputYearService planOutputYearService;

    @Autowired
    ProductionPlanOutputMonthService planOutputMonthService;

    @Autowired
    ProductionPlanOutputDateService planOutputDateService;

    @Autowired
    ProductionYieldAnalysisYearService yieldAnalysisYearService;

    @Resource
    ChangeLogService changeLogService;

    @Operation(summary = "年产量计划查询",
            parameters = {
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "年产量计划查询",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listYearProductionOutputPlans", method = RequestMethod.GET)
    public Object get() {
        log.info("params => listYearProductionOutputPlans");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        List<ProductionParameterTargetItem> itemlist =
                productionParameterTargetItemService.list(new QueryWrapper<ProductionParameterTargetItem>().lambda()
                        .eq(ProductionParameterTargetItem::getTargetItemTypeId, 1));
        return planOutputYearService.get(itemlist);
    }

    @Operation(summary = "新增年生产产量计划",
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
                    @ApiResponse(responseCode = "1", description = "当年产量计划已存在/当年指标计划不存在"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "新增年生产产量计划",businessType = BusinessType.INSERT)
    @RequestMapping(value = "/saveYearProductionOutputPlan", method = RequestMethod.POST, produces = "application/json")
    public Object save(@Valid @RequestBody AddProductionPlanYearDTO productionPlanYearDTO) {
        log.info("params => " + productionPlanYearDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, productionPlanYearDTO);
        return planOutputMonthService.saveCustom(productionPlanYearDTO);
    }

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
    @RequestMapping(value = "/deleteYearProductionOutputPlan", method = RequestMethod.POST, produces = "application/json")
    public Object delete(@Valid @RequestBody DeleteProductionPlanYearDTO deleteProductionPlanYearDTO) {
        log.info("params => " + deleteProductionPlanYearDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteProductionPlanYearDTO);
        return planOutputMonthService.delete(deleteProductionPlanYearDTO);
    }
}
