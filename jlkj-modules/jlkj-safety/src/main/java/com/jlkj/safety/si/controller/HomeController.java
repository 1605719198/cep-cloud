package com.jlkj.safety.si.controller;

import com.jlkj.safety.si.service.HomeService;
import com.jlkj.safety.si.service.RedisService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author su
 * @Description 安环看板
 * @create 2020-09-21 7:53
 */
@RestController
@RequestMapping("/home")
@Slf4j
@Api(tags = "1.5 安环看板")
public class HomeController {

    @Resource
    HomeService homeService;

    @Resource
    RedisService redisService;

    @ApiOperation(value = "信息-作业票数", notes = "")
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "job_count:作业票数\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/info/job/count", method = RequestMethod.GET)
    public Object getInfoSafeJobCount(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return homeService.getInfoSafeJobCount(params);
    }

    @ApiOperation(value = "信息-施工单位数", notes = "")
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "construction_unit_count:施工单位数\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/info/constructionunit/count", method = RequestMethod.GET)
    public Object getInfoConstructionUnitCount(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return homeService.getInfoConstructionUnitCount(params);
    }

    @ApiOperation(value = "信息-外委人数", notes = "")
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "construction_unit_persons_count:外委人数\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/info/constructionunitpersons/count", method = RequestMethod.GET)
    public Object getInfoConstructionUnitPersonsCount(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return homeService.getInfoConstructionUnitPersonsCount(params);
    }

    @ApiOperation(value = "信息-巡检人数", notes = "")
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "check_persons_count:巡检人数\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/info/checkpersons/count", method = RequestMethod.GET)
    public Object getInfoCheckPersonsCount(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return homeService.getInfoCheckPersonsCount(params);
    }

    @ApiOperation(value = "安全作业列表", notes = "")
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "safe_area_name:安全区域名称\n" +
                                    "job_count:作业票数量\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/list/job", method = RequestMethod.GET)
    public Object getSafeAreaJobList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return homeService.getSafeAreaJobList(params);
    }

    @ApiOperation(value = "安全巡检列表", notes = "")
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "safe_area_name:安全区域名称\n" +
                                    "check_persons_count:巡检人数"
                            )
                    )
            )
    )
    @RequestMapping(value = "/list/check", method = RequestMethod.GET)
    public Object getSafeAreaCheckList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return homeService.getSafeAreaCheckList(params);
    }

    @ApiOperation(value = "外委人数列表", notes = "")
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "safe_area_name:安全区域名称\n" +
                                    "construction_unit_persons_count:外委人数"
                            )
                    )
            )
    )
    @RequestMapping(value = "/list/person", method = RequestMethod.GET)
    public Object getSafeAreaPersonList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return homeService.getSafeAreaPersonList(params);
    }

    @ApiOperation(value = "安环采集-安全报警配置列表", notes = "")
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "PositionNumber:位号\n" +
                                    "Area:区域,\n" +
                                    "Range:量程,\n" +
                                    "LL:低低限报警\n" +
                                    "L:低限报警\n" +
                                    "H:高限报警\n" +
                                    "HH:高高限报警\n" +
                                    "Position:位置\n" +
                                    "Unit:单位\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/list/alarms/config", method = RequestMethod.GET)
    public Object getSafeAreaAlarmsList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return homeService.getSafeAreaAlarmsList(params);
    }

    @ApiOperation(value = "安环采集-全部数据采集列表", notes = "")
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id:位号\n" +
                                    "value:数据\n" +
                                    "time:时间\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/list/alarms/safe/point/collection", method = RequestMethod.GET)
    public Object getSafePointList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return redisService.getSafePointList(params);
    }

    @ApiOperation(value = "安环采集-安全数据明细采集列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:safe_area_name", value = "区域名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "params", value = "{\"safe_area_name\":\"鼓冷\"}", dataTypeClass = Object.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "area_name:区域\n" +
                                    "name:名称\n" +
                                    "id:仪表位号\n" +
                                    "position_name:位置\n" +
                                    "value:数据\n" +
                                    "warn:是否报警(YES,NO)\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/list/alarms/safe/detail/collection", method = RequestMethod.GET)
    public Object getSafeDetailPointList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return redisService.getSafeDetailPointList(params);
    }

    @ApiOperation(value = "安环采集-安全数据汇总采集列表", notes = "")
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "safe_area_name:区域名称\n" +
                                    "count:报警数量\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/list/alarms/safe/total/collection", method = RequestMethod.GET)
    public Object getSafeTotalPointList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return redisService.getSafeTotalPointList(params);
    }

    @ApiOperation(value = "安环采集-环保数据明细采集列表", notes = "")
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "area_name:区域\n" +
                                    "name:名称\n" +
                                    "value:数据\n" +
                                    "warn:是否报警(YES,NO)\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/list/alarms/env/detail/collection", method = RequestMethod.GET)
    public Object getSafeEnvDetailPointList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return redisService.getSafeEnvDetailPointList(params);
    }


    @ApiOperation(value = "安环采集-环保数据汇总采集列表", notes = "")
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "safe_area_name:区域名称\n" +
                                    "count:报警数量\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/list/alarms/env/total/collection", method = RequestMethod.GET)
    public Object getSafeEnvTotalPointList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return redisService.getSafeEnvTotalPointList(params);
    }
}
