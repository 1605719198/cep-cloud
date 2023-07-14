package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productiondataboard.GetDataBoardDTO;
import com.jlkj.product.oi.dto.productiondataboard.GetUpCoalValueByNowDayDTO;
import com.jlkj.product.oi.service.ProductionDataBoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
*@description: 生产实绩-上煤记录-查询-分页
*@Author: 265823
*@date: 2023/7/10 14:44
*/
@Tag(name = "生产数据")
@RestController
@RequestMapping("/plan/dataBoard")
@Slf4j
public class ProductionDataBoardController {
    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    ProductionDataBoardService productionDataBoardService;


    /**
     * 生产看板-焦炭日产量
     * @return
     */
    @Operation(summary = "生产看板-焦炭日产量",
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
                                    @ExampleObject(name = "yield", description = "产量"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产看板-焦炭日产量",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getCokeOutputAnalysisByNowDay", method = RequestMethod.GET)
    public AjaxResult getCokeOutputAnalysisByNowDay() {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        return AjaxResult.success(productionDataBoardService.getCokeOutputAnalysisByNowDay());
    }

    /**
     * 生产看板-本年度-焦炭产量分析-分类汇总
     * @return
     */
    @Operation(summary = "生产看板-本年度-焦炭产量分析-分类汇总",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "year", description = "年度"),
                                    @ExampleObject(name = "month", description = "月份"),
                                    @ExampleObject(name = "yield", description = "产量"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产看板-本年度-焦炭产量分析-分类汇总",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getCokeOutputAnalysis", method = RequestMethod.GET)
    public AjaxResult getCokeOutputAnalysis() {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        return AjaxResult.success(productionDataBoardService.getCokeOutputAnalysis());
    }

    /**
     * 物料看板-本日-上煤量
     * @param dto
     * @return
     */
    @Operation(summary = "物料看板-本日-上煤量",
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
    @Log(title = "物料看板-本日-上煤量",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getUpCoalWeightByNowDay", method = RequestMethod.GET)
    public AjaxResult getUpCoalValueByNowDay(@Valid @ParamModel GetUpCoalValueByNowDayDTO dto) {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        return AjaxResult.success(productionDataBoardService.getUpCoalWeightByNowDay(dto));
    }

    /**
     * 生产看板-今日产量
     * @param dto
     * @return
     */
    @Operation(summary = "生产看板-今日产量",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产看板-今日产量",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getTodayYield", method = RequestMethod.GET)
    public AjaxResult getTodayYield(@Valid @ParamModel GetDataBoardDTO dto) {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        return AjaxResult.success(productionDataBoardService.getTodayYield(dto));
    }

    /**
     * 生产看板-总耗用量
     * @param dto
     * @return
     */
    @Operation(summary = "生产看板-总耗用量",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产看板-总耗用量",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getTodayConsumption", method = RequestMethod.GET)
    public AjaxResult getTodayConsumption(@Valid @ParamModel GetDataBoardDTO dto) {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        return AjaxResult.success(productionDataBoardService.getTodayConsumption(dto));
    }

    /**
     * 生产看板-焦棚库存量
     * @param dto
     * @return
     */
    @Operation(summary = "生产看板-焦棚库存量",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产看板-焦棚库存量",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getTodayCokeStock", method = RequestMethod.GET)
    public AjaxResult getTodayCokeStock(@Valid @ParamModel GetDataBoardDTO dto) {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        return AjaxResult.success(productionDataBoardService.getTodayCokeStock(dto));
    }

    /**
     * 生产看板-焦炭发运量
     * @param dto
     * @return
     */
    @Operation(summary = "生产看板-焦炭发运量",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产看板-焦炭发运量",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getTodayCokeShipment", method = RequestMethod.GET)
    public AjaxResult getTodayCokeShipment(@Valid @ParamModel GetDataBoardDTO dto) {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        return AjaxResult.success(productionDataBoardService.getTodayCokeShipment(dto));
    }

    /**
     * 生产看板-蒸汽产量
     * @param dto
     * @return
     */
    @Operation(summary = "生产看板-蒸汽产量",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产看板-蒸汽产量",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getTodayCokeSteam", method = RequestMethod.GET)
    public AjaxResult getTodayCokeSteam(@Valid @ParamModel GetDataBoardDTO dto) {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        return AjaxResult.success(productionDataBoardService.getTodayCokeSteam(dto));
    }
    @Operation(summary = "生产看板-焦炉技术指标数据",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产看板-焦炉技术指标数据",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getCraftCokeTemp", method = RequestMethod.GET)
    public AjaxResult getCraftCokeTemp(@Valid @ParamModel GetDataBoardDTO dto) {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        return AjaxResult.success(productionDataBoardService.getCraftCokeTemp(dto));
    }

    /**
     * 生产看板- 收率
     * @param dto
     * @return
     */
    @Operation(summary = "生产看板- 收率",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产看板-收率",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getCokeTotalYield", method = RequestMethod.GET)
    public AjaxResult getCokeTotalYield(@Valid @ParamModel GetDataBoardDTO dto) {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        return AjaxResult.success(productionDataBoardService.getCokeTotalYield(dto));
    }

    /**
     * 生产看板- 干熄焦技术指标
     * @param dto
     * @return
     */
    @Operation(summary = "生产看板- 干熄焦技术指标",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产看板- 干熄焦技术指标",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getCokeTechnologyIndex", method = RequestMethod.GET)
    public AjaxResult getCokeTechnologyIndex(@Valid @ParamModel GetDataBoardDTO dto) {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        return AjaxResult.success(productionDataBoardService.getCokeTechnologyIndex(dto));
    }

    /**
     * 生产看板- 财务月日成本
     * @param dto
     * @return
     */
    @Operation(summary = "生产看板- 财务月日成本",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产看板- 财务月日成本",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getCostStatistics", method = RequestMethod.GET)
    public AjaxResult getCostStatistics(@Valid @ParamModel GetDataBoardDTO dto) {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        return AjaxResult.success(productionDataBoardService.getCostStatistics(dto));
    }

}
