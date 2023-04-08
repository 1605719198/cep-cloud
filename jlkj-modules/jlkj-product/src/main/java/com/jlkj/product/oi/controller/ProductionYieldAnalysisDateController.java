package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.home.HomeDTO;
import com.jlkj.product.oi.dto.productionyieldanalysisdate.ListProductionYieldAnalysisDateDTO;
import com.jlkj.product.oi.dto.productionyieldanalysisdate.ListProductionYieldAnalysisDateMaterialDTO;
import com.jlkj.product.oi.service.ProductionYieldAnalysisDateService;
import com.jlkj.product.oi.vo.home.*;
import com.jlkj.product.oi.vo.productionyieldanalysisdate.ListProductionYieldAnalysisDateMaterialVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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
 * 控制器-产量分析日表
 * @author sudeyou
 * @since 2022-07-15 14:27:41
 */
@Tag(name = "产量分析日表")
@RestController
@RequestMapping("/yield")
@Slf4j
public class ProductionYieldAnalysisDateController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private ProductionYieldAnalysisDateService productionYieldAnalysisDateService;

    @Operation(summary = "生产管理-产量跟踪-查询-日",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "year", description = "年度"),
                                    @ExampleObject(name = "month", description = "月份"),
                                    @ExampleObject(name = "date", description = "日"),
                                    @ExampleObject(name = "产量数据", description = "产量数据【动态列】"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产管理-产量跟踪-查询-日",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionYieldAnalysisDate", method = RequestMethod.GET)
    public Object getProductionYieldAnalysis(@Validated @ParamModel ListProductionYieldAnalysisDateDTO listProductionYieldAnalysisDateDTO) {
        log.info("params => " + listProductionYieldAnalysisDateDTO);
        String errorMsg = ValidUtil.checkValid(listProductionYieldAnalysisDateDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, listProductionYieldAnalysisDateDTO);
        return productionYieldAnalysisDateService.getProductionYieldAnalysisDateList(listProductionYieldAnalysisDateDTO);
    }

    @Operation(summary = "生产管理-产量跟踪-图表-物料名称(日)",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ListProductionYieldAnalysisDateMaterialVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产管理-产量跟踪-图表-物料名称(日)",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionYieldAnalysisDateChart", method = RequestMethod.GET)
    public Object getProductionYieldAnalysisChartDate(@Validated @ParamModel ListProductionYieldAnalysisDateMaterialDTO listProductionYieldAnalysisDateMaterialDTO) {
        log.info("params => " + listProductionYieldAnalysisDateMaterialDTO);
        String errorMsg = ValidUtil.checkValid(listProductionYieldAnalysisDateMaterialDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, listProductionYieldAnalysisDateMaterialDTO);
        return productionYieldAnalysisDateService.getProductionYieldAnalysisChartDate(listProductionYieldAnalysisDateMaterialDTO);
    }
    @Operation(summary = "生产管理-首页-焦碳计划实绩图表",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ListYieldChartVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产管理-首页-焦碳计划实绩图表",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionHomeYieldChart", method = RequestMethod.GET)
    public Object getProductionHomeYieldChartData(@Validated @ParamModel HomeDTO homeDTO) {
        log.info("params => " + homeDTO);
        String errorMsg = ValidUtil.checkValid(homeDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, homeDTO);
        return productionYieldAnalysisDateService.getProductionHomeYieldChartData(homeDTO);
    }

    @Operation(summary = "生产管理-首页-焦碳计划实绩统计",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = InfoYieldStatisticsVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产管理-首页-焦碳计划实绩统计",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionHomeYieldStatistics", method = RequestMethod.GET)
    public Object getProductionHomeYieldStatisticsData(@Validated @ParamModel HomeDTO homeDTO) {
        log.info("params => " + homeDTO);
        String errorMsg = ValidUtil.checkValid(homeDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, homeDTO);
        return productionYieldAnalysisDateService.getProductionHomeYieldStatisticsData(homeDTO);
    }

    @Operation(summary = "生产管理-首页-发电计划实绩统计",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = InfoPowerStatisticsVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产管理-首页-发电计划实绩统计",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionHomePowerStatistics", method = RequestMethod.GET)
    public Object getProductionHomePowerStatisticsData(@Validated @ParamModel HomeDTO homeDTO) {
        log.info("params => " + homeDTO);
        String errorMsg = ValidUtil.checkValid(homeDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, homeDTO);
        return productionYieldAnalysisDateService.getProductionHomePowerStatisticsData(homeDTO);
    }

    @Operation(summary = "生产管理-首页-发电仪表图表",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ListPowerMeterChartVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产管理-首页-发电仪表图表",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionHomePowerMeterChart", method = RequestMethod.GET)
    public Object getProductionHomePowerMeterChartData(@Validated @ParamModel HomeDTO homeDTO) {
        log.info("params => " + homeDTO);
        String errorMsg = ValidUtil.checkValid(homeDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, homeDTO);
        return productionYieldAnalysisDateService.getProductionHomePowerMeterChartData(homeDTO);
    }

    @Operation(summary = "生产管理-首页-统计",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = InfoStatisticsVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产管理-首页-统计",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionHomeStatistics", method = RequestMethod.GET)
    public Object getProductionHomeStatisticsData(@Validated @ParamModel HomeDTO homeDTO) {
        log.info("params => " + homeDTO);
        String errorMsg = ValidUtil.checkValid(homeDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, homeDTO);
        return productionYieldAnalysisDateService.getProductionHomeStatisticsData(homeDTO);
    }

    @Operation(summary = "生产管理-产量跟踪-查询-获取动态列表",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "id", description = "id"),
                                    @ExampleObject(name = "materials_name", description = "名称"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产管理-产量跟踪-查询-获取动态列表",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionYieldAnalysisColList", method = RequestMethod.GET)
    public Object getProductionYieldAnalysisColList() {
        return productionYieldAnalysisDateService.getProductionYieldAnalysisColList();
    }
}

