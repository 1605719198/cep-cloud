package com.jlkj.product.oi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.domain.ProductionParameterTargetItem;
import com.jlkj.product.oi.dto.productionplanoutputmonth.ListProductionPlanOutputMonthTargetItemDTO;
import com.jlkj.product.oi.dto.productionplantarget.GetProductionPlanMonthDTO;
import com.jlkj.product.oi.dto.productionplantarget.GetProductionPlanOneMonthDTO;
import com.jlkj.product.oi.dto.productionplantarget.UpdateProductionPlanMonthDTO;
import com.jlkj.product.oi.service.ProductionParameterTargetItemService;
import com.jlkj.product.oi.service.ProductionPlanOutputMonthService;
import com.jlkj.product.oi.service.impl.*;
import com.jlkj.product.oi.vo.productionplanoutputmonth.ListProductionPlanOutputMonthTargetItemVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * @author zyf
 * @Description
 * @create 2022-04-26 10:34
 */
@Tag(name = "月产量计划")
@RestController
@RequestMapping("/plan")
@Slf4j
public class ProductionPlanOutputMonthController {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    ProductionParameterTargetItemService productionParameterTargetItemService;

    @Autowired
    ProductionPlanOutputMonthService planOutputMonthService;


    @Operation(summary = "查询月产量计划",
            parameters = {
                    @Parameter(name = "planYear", description = "计划年度")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "查询月产量计划",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listMonthProductionOutputPlans", method = RequestMethod.GET)
    public Object get(@Valid GetProductionPlanMonthDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);

        List<ProductionParameterTargetItem> itemlist =
                productionParameterTargetItemService.list(new QueryWrapper<ProductionParameterTargetItem>().lambda()
                        .eq(ProductionParameterTargetItem::getTargetItemTypeId, 1));
        return planOutputMonthService.get(dto, itemlist);
    }

    @Operation(summary = "查询单条月生产产量计划",
            parameters = {
                    @Parameter(name = "planYear", description = "计划年度"),
                    @Parameter(name = "planMonth", description = "计划月份")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "查询单条月生产产量计划",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getMonthProductionOutputPlan", method = RequestMethod.GET)
    public Object getOne(@Valid GetProductionPlanOneMonthDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);

        List<ProductionParameterTargetItem> itemlist =
                productionParameterTargetItemService.list(new QueryWrapper<ProductionParameterTargetItem>().lambda()
                        .eq(ProductionParameterTargetItem::getTargetItemTypeId, 1));
        return planOutputMonthService.getOneCustom(dto, itemlist);
    }

    @Operation(summary = "修改月产量计划",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "planYear", description = "计划年度"),
                            @ExampleObject(name = "planMonth", description = "计划月份"),
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
    @Log(title = "修改月产量计划",businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/updateMonthProductionOutputPlan", method = RequestMethod.POST, produces = "application/json")
    public Object update(@Valid @RequestBody UpdateProductionPlanMonthDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return planOutputMonthService.updateCustom(dto);
    }

    @Operation(summary = "生产管理-指标跟踪-图表-指标项(月计划)",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ListProductionPlanOutputMonthTargetItemVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产管理-指标跟踪-图表-指标项(月计划)",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionPlanOutputMonthTargetItemChart", method = RequestMethod.GET)
    public Object getProductionPlanOutpumonthTargetItemChartData(@Validated @ParamModel ListProductionPlanOutputMonthTargetItemDTO listProductionPlanOutputMonthTargetItemDTO) {
        log.info("params => " + listProductionPlanOutputMonthTargetItemDTO);
        String errorMsg = ValidUtil.checkValid(listProductionPlanOutputMonthTargetItemDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, listProductionPlanOutputMonthTargetItemDTO);
        return planOutputMonthService.getProductionPlanOutputMonthTargetItemChartData(listProductionPlanOutputMonthTargetItemDTO);
    }
}
