package com.jlkj.product.oi.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.resp.Result;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.dashboardindex.GetCokePlanPerformanceDTO;
import com.jlkj.product.oi.service.ProductionBoardEmsDataService;
import com.jlkj.product.oi.service.ProductionDataBoardService;
import com.jlkj.product.oi.service.ProductionRealOutputMonthService;
import com.jlkj.product.oi.service.impl.ProductionOutputPerformanceServiceImpl;
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
import java.util.*;
import java.util.stream.Collectors;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * @author zyf
 * @Description
 * @create 2022-08-30 9:31
 */
@Tag(name = "首页看板统计")
@RestController
@RequestMapping("/dashboard")
@Slf4j
public class DashboardIndexController {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    ProductionOutputPerformanceServiceImpl performanceService;

    @Resource
    private ProductionRealOutputMonthService productionRealOutputMonthService;

    @Resource
    private ProductionDataBoardService productionDataBoardService;

    @Resource
    private ProductionBoardEmsDataService productionBoardEmsDataService;

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
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getCokePerformanceAnalys", method = RequestMethod.GET)
    public Object getCokePerformanceAnalys(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);

        List<Map<String, Object>> plan = performanceService.getCokePlanAnalys(dto);
        List<Map<String, Object>> perf = performanceService.getCokePerformanceAnalys(dto);
        Map<String, Object> res = new HashMap<>(1);
        res.put("plan", plan);
        res.put("perf", perf);
        return Result.success(res);
    }


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
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getCokeOutputAnalysByLevel", method = RequestMethod.GET)
    public Object getCokeOutputAnalysByLevel(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        List<Map<String, Object>> cokeLevel = performanceService.getCokeOutputAnalysByLevel(dto);
        return Result.success(cokeLevel);
    }

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
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getCokeOutputAnalysByStove", method = RequestMethod.GET)
    public Object getCokeOutputAnalysByStove(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        List<Map<String, Object>> cokeStove = performanceService.getCokeOutputAnalysByStove(dto);
        return Result.success(cokeStove);
    }

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
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getProductivity", method = RequestMethod.GET)
    public Object getProductivity(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        Map<String, Object> res = new HashMap<>(4);
        List<Map<String, Object>> cokeStove = performanceService.getProductivity(dto);
        if(cokeStove.size() > 0){
            res.put("gxj",cokeStove.get(0).get("gxj"));
            res.put("sxj",cokeStove.get(0).get("sxj"));
        }else{
            res.put("gxj",0.000);
            res.put("sxj",0.000);
        }
        return Result.success(res);
    }




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
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getProductivityAnalys", method = RequestMethod.GET)
    public Object getProductivityAnalys(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        Map<String, Object> res = new HashMap<>(4);
        List<Object> date = new ArrayList<>();
        List<Object> cokeAllPlan = new ArrayList<>();
        List<Object> cokeAllReal = new ArrayList<>();
        List<Object> cokePlan = new ArrayList<>();
        List<Object> cokeReal = new ArrayList<>();
        List<Map<String, Object>> productivity = productionRealOutputMonthService.getProductivityAnalys(dto);
        for (Map<String, Object> map : productivity) {
            date.add(map.get("plan_date"));
            cokeAllPlan.add(map.get("coke_all_plan"));
            cokeAllReal.add(map.get("coke_all_real"));
            cokePlan.add(map.get("coke_plan"));
            cokeReal.add(map.get("coke_real"));
        }
        res.put("date", date);
        res.put("coke_all_plan", cokeAllPlan);
        res.put("coke_all_real", cokeAllReal);
        res.put("coke_plan", cokePlan);
        res.put("coke_real", cokeReal);
        return Result.success(res);
    }

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
    public Object getProductivityAnalysInfo(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        List<Map<String, Object>> productivity = productionRealOutputMonthService.getProductivityAnalysInfo(dto);
        return Result.success(productivity);
    }


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
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getCoalConsumption", method = RequestMethod.GET)
    public Object getCoalConsumption(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        Map<String, Object> res = new HashMap<>(4);
        List<Map<String, Object>> coals = productionDataBoardService.getCoalConsumption(dto);
        Set<String> coalNames = coals.stream().filter(item -> StringUtils.isNotNull(item.get("material_name"))).map(item -> item.get("material_name").toString()).collect(Collectors.toSet());
        Set<String> dates = coals.stream().map(item -> item.get("date").toString()).collect(Collectors.toSet());
        List<Object> date = dates.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());

        List<Double> data = date.stream().map(i ->  0.0).collect(Collectors.toList());
        List<Double> coalDataSum = data.stream().map(i -> 0.0).collect(Collectors.toList());

        Map<String, List<Double>> coalData = new HashMap<>(8);
        for (String coalName : coalNames) {
            coalData.put(coalName, data);
        }
        for (Map<String, Object> coal : coals) {
            String name = StringUtils.isNotNull(coal.get("material_name")) ? coal.get("material_name").toString() : null;
            int index = date.indexOf(coal.get("date").toString());
            if (StringUtils.isNotNull(name) && coalData.containsKey(name)) {
                coalData.get(name).set(index, new BigDecimal(coal.get("weight").toString()).doubleValue());
            }
            double sum = coalDataSum.get(index);
            sum = sum + new BigDecimal(coal.get("weight").toString()).doubleValue();
            coalDataSum.set(index, sum);
        }
        res.put("coals", coalNames);
        res.put("coalData", coalData);
        res.put("coalDataSum", coalDataSum);
        res.put("date", date);
        return Result.success(res);
    }

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
    public Object getCoalConsumptionInfo(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        List<Map<String, Object>> coal = productionDataBoardService.getCoalConsumptionInfo(dto);
        return Result.success(coal);
    }


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
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getEnergyConsumption", method = RequestMethod.GET)
    public Object getEnergyConsumption(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        Map<String, Object> res = new HashMap<>(4);
        List<Map<String, Object>> energys = productionDataBoardService.getEnergyConsumption(dto);

        List<Object> date = new ArrayList<>();
        List<Object> electric = new ArrayList<>();
        List<Object> water = new ArrayList<>();
        List<Object> gas = new ArrayList<>();
        for (Map<String, Object> energy : energys) {
            date.add(energy.get("date"));
            electric.add(energy.get("electric"));
            water.add(energy.get("water"));
            gas.add(energy.get("gas"));
        }
        res.put("date",date);
        res.put("electric",electric);
        res.put("water",water);
        res.put("gas",gas);
        return Result.success(res);
    }










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
    public Object getSteamByBoiler(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        List<Map<String, Object>> steam = productionBoardEmsDataService.getSteamByBoiler(dto);
        return Result.success(steam);
    }

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
    public Object getSteamByOutput(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        List<Map<String, Object>> steam = productionBoardEmsDataService.getSteamByOutput(dto);
        return Result.success(steam);
    }

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
    public Object getSteamByRetrieve(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        List<Map<String, Object>> steam = productionBoardEmsDataService.getSteamByRetrieve(dto);
        return Result.success(steam);
    }

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
    public Object getSteamByWater(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        List<Map<String, Object>> steam = productionBoardEmsDataService.getSteamByWater(dto);
        return Result.success(steam);
    }





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
    public Object getElectricityDayConsume(GetCokePlanPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        List<Map<String, Object>> steam = productionBoardEmsDataService.getElectricityDayConsume(dto);
        return Result.success(steam);
    }


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
        List<Map<String, Object>> steam = productionBoardEmsDataService.getElectricityDayConsumeInfo(dto);
        return Result.success(steam);
    }




}
