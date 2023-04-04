package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.resp.PageResult;
import com.jlkj.common.core.web.resp.Result;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productionconfigurecoalspeciesperformance.PageConfigureCoalSpeciesAnalysisStatisticsDTO;
import com.jlkj.product.oi.dto.productionconfigurecoalspeciesperformance.PageProductionConfigureCoalSpeciesAnalysisChartDTO;
import com.jlkj.product.oi.dto.productionconfigurecoalspeciesperformance.PageProductionConfigureCoalSpeciesAnalysisDTO;
import com.jlkj.product.oi.dto.productionconfigurecoalspeciesperformance.PageProductionConfigureCoalSpeciesPerformanceDTO;
import com.jlkj.product.oi.service.ProductionConfigureCoalSpeciesPerformanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * 控制器-配煤实绩主记录
 * @author sudeyou
 */
@Tag(name = "配煤实绩主记录")
@RestController
@RequestMapping("/performance")
@Slf4j
public class ProductionConfigureCoalSpeciesPerformanceController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private ProductionConfigureCoalSpeciesPerformanceService productionConfigureCoalSpeciesPerformanceService;

    @Operation(summary = "查询-分页-配煤实绩主记录",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
                    @Parameter(name = "shift_name", description = "班次"),
                    @Parameter(name = "class_name", description = "班别"),
                    @Parameter(name = "start_time", description = "开始时间[开始]"),
                    @Parameter(name = "end_time", description = "开始时间[结束]"),
                    @Parameter(name = "current", description = "页码-从1开始"),
                    @Parameter(name = "size", description = "每页行数"),
                    @Parameter(name = "order", description = "排序字段"),
                    @Parameter(name = "orderby", description = "排序规则字段")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "id", description = "主键标识;UUID"),
                                    @ExampleObject(name = "shift_name", description = "班次"),
                                    @ExampleObject(name = "class_name", description = "班别"),
                                    @ExampleObject(name = "configure_coal_species_date", description = "配煤日期"),
                                    @ExampleObject(name = "coal_tower_number", description = "储煤塔号"),
                                    @ExampleObject(name = "before_material_level", description = "入煤前料位"),
                                    @ExampleObject(name = "after_material_level", description = "入煤后料位"),
                                    @ExampleObject(name = "before_stock", description = "入煤前存量"),
                                    @ExampleObject(name = "after_stock", description = "入煤后存量"),
                                    @ExampleObject(name = "start_time", description = "开始时间"),
                                    @ExampleObject(name = "end_time", description = "结束时间"),
                                    @ExampleObject(name = "duration", description = "配煤时长"),
                                    @ExampleObject(name = "coal_tower_weight", description = "配煤总量"),
                                    @ExampleObject(name = "actual_proportion", description = "实际配煤比"),
                                    @ExampleObject(name = "coal_blending_accuracy", description = "配煤精准度"),
                                    @ExampleObject(name = "create_time", description = "创建时间"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )

    @Log(title = "查询-分页-配煤实绩主记录",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listConfigureCoalSpeciesPerformance", method = RequestMethod.GET)
    public Object getPageData(@Validated @ParamModel PageProductionConfigureCoalSpeciesPerformanceDTO pageProductionConfigureCoalSpeciesPerformanceDTO) {
        log.info("params => " + pageProductionConfigureCoalSpeciesPerformanceDTO);
        String errorMsg = ValidUtil.checkValid(pageProductionConfigureCoalSpeciesPerformanceDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageProductionConfigureCoalSpeciesPerformanceDTO);
        return AjaxResult.success(productionConfigureCoalSpeciesPerformanceService.getPageData(pageProductionConfigureCoalSpeciesPerformanceDTO));
    }


    @Operation(summary = "查询-分页-配煤分析",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
                    @Parameter(name = "year", description = "年份"),
                    @Parameter(name = "month", description = "月份"),
                    @Parameter(name = "current", description = "页码-从1开始"),
                    @Parameter(name = "size", description = "每页行数"),
                    @Parameter(name = "order", description = "排序字段"),
                    @Parameter(name = "orderby", description = "排序规则字段")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "id", description = "主键标识;UUID"),
                                    @ExampleObject(name = "plan_number", description = "计划编号"),
                                    @ExampleObject(name = "plan_state_text", description = "计划状态"),
                                    @ExampleObject(name = "receive_time", description = "下发时间"),
                                    @ExampleObject(name = "start_time", description = "配煤-开始时间"),
                                    @ExampleObject(name = "end_time", description = "配煤-结束时间"),
                                    @ExampleObject(name = "duration", description = "配煤时长"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "查询-分页-配煤分析",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listConfigureCoalSpeciesAnalysis", method = RequestMethod.GET)
    public Object listConfigureCoalSpeciesAnalysis(@Validated @ParamModel PageProductionConfigureCoalSpeciesAnalysisDTO dto) {
        log.info("params => " + dto);
        String errorMsg = ValidUtil.checkValid(dto);
        if (!"".equals(errorMsg)) {
            return Result.validatedFailure(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return PageResult.success(productionConfigureCoalSpeciesPerformanceService.getConfigureCoalSpeciesAnalysis(dto));
    }


    @Operation(summary = "查询-分页-配煤分析-图表-年",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
                    @Parameter(name = "year", description = "年份"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "id", description = "主键标识;UUID"),
                                    @ExampleObject(name = "year", description = "年份"),
                                    @ExampleObject(name = "month", description = "月份"),
                                    @ExampleObject(name = "material_number", description = "物料编号"),
                                    @ExampleObject(name = "material_code", description = "物料代码"),
                                    @ExampleObject(name = "material_name", description = "物料名称"),
                                    @ExampleObject(name = "weight", description = "重量"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "查询-分页-配煤分析-图表-年",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listConfigureCoalSpeciesAnalysisChartByYear", method = RequestMethod.GET)
    public Object listConfigureCoalSpeciesAnalysisChartByYear(@Validated @ParamModel PageProductionConfigureCoalSpeciesAnalysisChartDTO dto) {
        log.info("params => " + dto);
        String errorMsg = ValidUtil.checkValid(dto);
        if (!"".equals(errorMsg)) {
            return Result.validatedFailure(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return Result.success(productionConfigureCoalSpeciesPerformanceService.getConfigureCoalSpeciesAnalysisChartByYear(dto));
    }

    @Operation(summary = "查询-分页-配煤分析-图表-月",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
                    @Parameter(name = "year", description = "年份"),
                    @Parameter(name = "month", description = "月份"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "id", description = "主键标识;UUID"),
                                    @ExampleObject(name = "year", description = "年"),
                                    @ExampleObject(name = "month", description = "月"),
                                    @ExampleObject(name = "day", description = "日"),
                                    @ExampleObject(name = "material_number", description = "物料编号"),
                                    @ExampleObject(name = "material_code", description = "物料代码"),
                                    @ExampleObject(name = "material_name", description = "物料名称"),
                                    @ExampleObject(name = "weight", description = "重量"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "查询-分页-配煤分析-图表-月",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listConfigureCoalSpeciesAnalysisChartByMonth", method = RequestMethod.GET)
    public Object listConfigureCoalSpeciesAnalysisChartByMonth(@Validated @ParamModel PageProductionConfigureCoalSpeciesAnalysisChartDTO dto) {
        log.info("params => " + dto);
        String errorMsg = ValidUtil.checkValid(dto);
        if (!"".equals(errorMsg)) {
            return Result.validatedFailure(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return Result.success(productionConfigureCoalSpeciesPerformanceService.getConfigureCoalSpeciesAnalysisChartByMonth(dto));
    }


    @Operation(summary = "统计分析-配煤比分析-图表",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "materials_id", description = "物料id"),
                                    @ExampleObject(name = "materials_name", description = "物料名称"),
                                    @ExampleObject(name = "weight", description = "上煤重量"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "统计分析-配煤比分析-图表",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getConfigureCoalSpeciesAnalysisStatistics", method = RequestMethod.GET)
    public Object getConfigureCoalSpeciesAnalysisStatistics(@Validated @ParamModel PageConfigureCoalSpeciesAnalysisStatisticsDTO dto) {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return Result.success(productionConfigureCoalSpeciesPerformanceService.getConfigureCoalSpeciesAnalysisStatistics(dto));
    }

}

