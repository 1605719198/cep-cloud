package com.jlkj.product.oi.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.dashboardindex.GetCokePlanPerformanceDTO;
import com.jlkj.product.oi.service.ProductionBoardEmsDataService;
import com.jlkj.product.oi.service.ProductionDataBoardService;
import com.jlkj.product.oi.service.ProductionOutputPerformanceService;
import com.jlkj.product.oi.service.ProductionRealOutputMonthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
*@description: 首页看板统计
*@Author: 265823
*@date: 2023/7/10 9:31
*/
@Tag(name = "首页看板统计")
@RestController
@RequestMapping("/dashboard")
@Slf4j
public class DashboardIndexController {

    @Resource
    HttpServletRequest httpServletRequest;

    @Resource
    ProductionOutputPerformanceService performanceService;

    @Resource
    private ProductionRealOutputMonthService productionRealOutputMonthService;

    @Resource
    private ProductionDataBoardService productionDataBoardService;

    @Resource
    private ProductionBoardEmsDataService productionBoardEmsDataService;

    /**
     * 焦炭产量记录查询
     * @param dto
     * @return
     */
    @Operation(summary = "焦炭产量记录查询",
            parameters = {
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "year", description = "年"),
                                    @ExampleObject(name = "month", description = "月"),
                                    @ExampleObject(name = "date", description = "日"),
                                    @ExampleObject(name = "yeild", description = "量"),
                            })
                    )
            }
    )
    @Log(title = "焦炭产量记录查询",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getCokePerformanceAnalys", method = RequestMethod.GET)
    public Object getCokePerformanceAnalys(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(performanceService.getCokePerformanceAnalys(dto));
    }


    /**
     * 焦炭产量-详情-焦炭产量 基于 焦炭等级
     * @param dto
     * @return
     */
    @Operation(summary = "焦炭产量-详情-焦炭产量 基于 焦炭等级",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "焦炭产量-详情-焦炭产量 基于 焦炭等级",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getCokeOutputAnalysByLevel", method = RequestMethod.GET)
    public AjaxResult getCokeOutputAnalysByLevel(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(performanceService.getCokeOutputAnalysByLevel(dto));
    }

    /**
     * 焦炭产量-详情-焦炭产量 基于 炉号
     * @param dto
     * @return
     */
    @Operation(summary = "焦炭产量-详情-焦炭产量 基于 炉号",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "焦炭产量-详情-焦炭产量 基于 炉号",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getCokeOutputAnalysByStove", method = RequestMethod.GET)
    public AjaxResult getCokeOutputAnalysByStove(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(performanceService.getCokeOutputAnalysByStove(dto));
    }

    /**
     * 焦炭产量-详情-焦炭产率（干熄焦、湿熄焦）
     * @param dto
     * @return
     */
    @Operation(summary = "焦炭产量-详情-焦炭产率（干熄焦、湿熄焦）",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "焦炭产量-详情-焦炭产率（干熄焦、湿熄焦）",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductivity", method = RequestMethod.GET)
    public AjaxResult getProductivity(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(performanceService.getProductivity(dto));
    }

    /**
     * 焦炭产量-详情-收率对比
     * @param dto
     * @return
     */
    @Operation(summary = "焦炭产量-详情-收率对比",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "焦炭产量-详情-收率对比",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductivityAnalys", method = RequestMethod.GET)
    public AjaxResult getProductivityAnalys(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(productionRealOutputMonthService.getProductivityAnalys(dto));
    }

    /**
     * 焦炭产量-详情-收率对比-详情
     * @param dto
     * @return
     */
    @Operation(summary = "焦炭产量-详情-收率对比-详情",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "焦炭产量-详情-收率对比-详情",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getProductivityAnalysInfo", method = RequestMethod.GET)
    public AjaxResult getProductivityAnalysInfo(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(productionRealOutputMonthService.getProductivityAnalysInfo(dto));
    }

    /**
     * 焦炭产量-详情-煤种耗煤量
     * @param dto
     * @return
     */
    @Operation(summary = "焦炭产量-详情-煤种耗煤量",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "焦炭产量-详情-煤种耗煤量",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getCoalConsumption", method = RequestMethod.GET)
    public AjaxResult getCoalConsumption(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(productionDataBoardService.getCoalConsumption(dto));
    }

    /**
     * 焦炭产量-详情-煤种耗煤量-详情
     * @param dto
     * @return
     */
    @Operation(summary = "焦炭产量-详情-煤种耗煤量-详情",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "焦炭产量-详情-煤种耗煤量-详情",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getCoalConsumptionInfo", method = RequestMethod.GET)
    public AjaxResult getCoalConsumptionInfo(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(productionDataBoardService.getCoalConsumptionInfo(dto));
    }

    /**
     * 焦炭产量-详情-能源耗煤量
     * @param dto
     * @return
     */
    @Operation(summary = "焦炭产量-详情-能源耗煤量",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "焦炭产量-详情-能源耗煤量",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getEnergyConsumption", method = RequestMethod.GET)
    public AjaxResult getEnergyConsumption(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(productionDataBoardService.getEnergyConsumption(dto));
    }


    /**
     * 首页看板-蒸气产量-蒸气实绩
     * @param dto
     * @return
     */
    @Operation(summary = "首页看板-蒸气产量-蒸气实绩",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "首页看板-蒸气产量-蒸气实绩",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getSteamByBoiler", method = RequestMethod.GET)
    public AjaxResult getSteamByBoiler(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(productionBoardEmsDataService.getSteamByBoiler(dto));
    }

    /**
     * 首页看板-蒸气产量-蒸气总产量
     * @param dto
     * @return
     */
    @Operation(summary = "首页看板-蒸气产量-蒸气总产量",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "首页看板-蒸气产量-蒸气总产量",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getSteamByOutput", method = RequestMethod.GET)
    public AjaxResult getSteamByOutput(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(productionBoardEmsDataService.getSteamByOutput(dto));
    }

    /**
     * 首页看板-蒸气产量-蒸气回收量实绩
     * @param dto
     * @return
     */
    @Operation(summary = "首页看板-蒸气产量-蒸气回收量实绩",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "首页看板-蒸气产量-蒸气回收量实绩",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getSteamByRetrieve", method = RequestMethod.GET)
    public AjaxResult getSteamByRetrieve(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(productionBoardEmsDataService.getSteamByRetrieve(dto));
    }

    /**
     * 首页看板-蒸气产量-水耗用量
     * @param dto
     * @return
     */
    @Operation(summary = "首页看板-蒸气产量-水耗用量",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "首页看板-蒸气产量-水耗用量",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getSteamByWater", method = RequestMethod.GET)
    public AjaxResult getSteamByWater(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(productionBoardEmsDataService.getSteamByWater(dto));
    }

    /**
     * 首页看板-发电量-日耗电
     * @param dto
     * @return
     */
    @Operation(summary = "首页看板-发电量-日耗电",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "首页看板-发电量-日耗电",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getElectricityDayConsume", method = RequestMethod.GET)
    public AjaxResult getElectricityDayConsume(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(productionBoardEmsDataService.getElectricityDayConsume(dto));
    }

    /**
     * 首页看板-发电量-日耗电-峰谷平
     * @param dto
     * @return
     */
    @Operation(summary = "首页看板-发电量-日耗电-峰谷平",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "首页看板-发电量-日耗电-峰谷平",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getElectricityDayConsumeInfo", method = RequestMethod.GET)
    public Object getElectricityDayConsumeInfo(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(productionBoardEmsDataService.getElectricityDayConsumeInfo(dto));
    }

    /**
     * 焦炭产量-详情-能源消耗量
     * @param dto
     * @return
     */
    @Operation(summary = "焦炭产量-详情-能源消耗量",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "焦炭产量-详情-能源消耗量",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getCokeEnergyConsumption", method = RequestMethod.GET)
    public AjaxResult getCokeEnergyConsumption(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(productionBoardEmsDataService.getCokeEnergyConsumption(dto));
    }

    /**
     * 蒸汽产量-详情-蒸汽实绩
     * @param dto
     * @return
     */
    @Operation(summary = "蒸汽产量-详情-蒸汽实绩",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "蒸汽产量-详情-蒸汽实绩",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getSteamPerformance", method = RequestMethod.GET)
    public AjaxResult getSteamPerformance(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(productionBoardEmsDataService.getSteamPerformance(dto));

    }

    /**
     * 蒸汽产量-详情-蒸汽总产量
     * @param dto
     * @return
     */
    @Operation(summary = "蒸汽产量-详情-蒸汽总产量",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "蒸汽产量-详情-蒸汽实绩",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getSteamTotalOutput", method = RequestMethod.GET)
    public AjaxResult getSteamTotalOutput(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(productionBoardEmsDataService.getSteamTotalOutput(dto));
    }

    /**
     * 蒸汽产量-详情-水耗用量
     * @param dto
     * @return
     */
    @Operation(summary = "蒸汽产量-详情-水耗用量",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "蒸汽产量-详情-水耗用量",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getWaterConsumption", method = RequestMethod.GET)
    public AjaxResult getWaterConsumption(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(productionBoardEmsDataService.getWaterConsumption(dto));
    }

    /**
     * 首页看板-发电量
     * @param dto
     * @return
     */
    @Operation(summary = "首页看板-发电量",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "首页看板-发电量",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getPowerGeneration", method = RequestMethod.GET)
    public AjaxResult getPowerGeneration(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(productionBoardEmsDataService.getPowerGeneration(dto));
    }

    /**
     * 发电量-详情-并网电量实绩-日发电量分析
     * @param dto
     * @return
     */
    @Operation(summary = "发电量-详情-并网电量实绩-日发电量分析",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "首页看板-发电量",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getPowerGenerationDayAnalysis", method = RequestMethod.GET)
    public AjaxResult getPowerGenerationDayAnalysis(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(productionBoardEmsDataService.getPowerGenerationDayAnalysis(dto));
    }

    /**
     * 发电量-详情-发电量-日发电量分析-消耗
     * @param dto
     * @return
     */
    @Operation(summary = "发电量-详情-发电量-日发电量分析-消耗",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "发电量-详情-发电量-日发电量分析-消耗",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getPowerGenerationDayAnalysisByConsume", method = RequestMethod.GET)
    public AjaxResult getPowerGenerationDayAnalysisByConsume(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(productionBoardEmsDataService.getPowerGenerationDayAnalysisByConsume(dto));
    }

    /**
     * 发电量-详情-并网电量实绩
     * @param dto
     * @return
     */
    @Operation(summary = "发电量-详情-并网电量实绩",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "发电量-详情-并网电量实绩",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getGridConnectionElectricityQuantity", method = RequestMethod.GET)
    public AjaxResult getGridConnectionElectricityQuantity(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(productionBoardEmsDataService.getGridConnectionElectricityQuantity(dto));
    }

    /**
     * 发电量-详情-蒸气耗用量
     * @param dto
     * @return
     */
    @Operation(summary = "发电量-详情-蒸气耗用量",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "发电量-详情-蒸气耗用量",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getSteamConsumption", method = RequestMethod.GET)
    public AjaxResult getSteamConsumption(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(productionBoardEmsDataService.getSteamConsumption(dto));
    }

    /**
     * 发电量-详情-蒸气耗用量-三级看板
     * @param dto
     * @return
     */
    @Operation(summary = "发电量-详情-蒸气耗用量-三级看板",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "发电量-详情-蒸气耗用量-三级看板",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getSteamConsumptionByDay", method = RequestMethod.GET)
    public AjaxResult getSteamConsumptionByDay(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(productionBoardEmsDataService.getSteamConsumptionByDay(dto));
    }
}
