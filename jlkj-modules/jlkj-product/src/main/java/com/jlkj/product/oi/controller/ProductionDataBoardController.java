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
 * @author yzl
 * @Description
 * @create 2022年7月20日 08:20:20
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
    public Object getCokeOutputAnalysisByNowDay() {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        return AjaxResult.success(productionDataBoardService.getCokeOutputAnalysisByNowDay());
    }

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
    public Object getCokeOutputAnalysis() {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        return AjaxResult.success(productionDataBoardService.getCokeOutputAnalysis());
    }




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
    public Object getUpCoalValueByNowDay(@Valid @ParamModel GetUpCoalValueByNowDayDTO dto) {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        return AjaxResult.success(productionDataBoardService.getUpCoalWeightByNowDay(dto));
    }




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
    public Object getTodayYield(@Valid @ParamModel GetDataBoardDTO dto) {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        return AjaxResult.success(productionDataBoardService.getTodayYield(dto));
    }

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
    public Object getTodayConsumption(@Valid @ParamModel GetDataBoardDTO dto) {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        return AjaxResult.success(productionDataBoardService.getTodayConsumption(dto));
    }

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
    public Object getTodayCokeStock(@Valid @ParamModel GetDataBoardDTO dto) {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        return AjaxResult.success(productionDataBoardService.getTodayCokeStock(dto));
    }


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
    public Object getTodayCokeShipment(@Valid @ParamModel GetDataBoardDTO dto) {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        return AjaxResult.success(productionDataBoardService.getTodayCokeShipment(dto));
    }

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
    public Object getTodayCokeSteam(@Valid @ParamModel GetDataBoardDTO dto) {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        return AjaxResult.success(productionDataBoardService.getTodayCokeSteam(dto));
    }




































}
