package com.jlkj.safety.si.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.safety.si.service.CommonComboService;
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
 * @Description 公共-下拉列表
 * @create 2020-09-21 7:53
 */
@RestController
@RequestMapping("/common/combo")
@Slf4j
@Api(tags = "1.1 公共-下拉列表")
public class CommonComboController {

    @Resource
    CommonComboService commonComboService;

    @ApiOperation(value = "安全区域", notes = "获取安全区域下拉列表数据")
    @ApiResponses(
            @ApiResponse(code = 200, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id: '安全区域ID'\n" +
                                    "name: '安全区域名称'\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/safe/area", method = RequestMethod.GET)
    public AjaxResult getSafeAreaDropList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        Object safeAreaDropList = commonComboService.getSafeAreaDropList(params);
                System.out.println(safeAreaDropList);

        return AjaxResult.success(commonComboService.getSafeAreaDropList(params));
    }

    @ApiOperation(value = "工种", notes = "获取工种下拉列表数据")
    @ApiResponses(
            @ApiResponse(code = 200, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id: '工种ID'\n" +
                                            "name: '工种名称'\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/safe/worktype", method = RequestMethod.GET)
    public AjaxResult getSafeWorkTypeDropList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(commonComboService.getSafeWorkTypeDropList(params));
    }

    @ApiOperation(value = "安全巡检类型", notes = "获取安全巡检类型下拉列表数据")
    @ApiResponses(
            @ApiResponse(code = 200, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id: '安全巡检类型ID'\n" +
                                            "name: '安全巡检类型'\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/safe/checktype", method = RequestMethod.GET)
    public AjaxResult getSafeCheckTypeDropList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(commonComboService.getSafeCheckTypeDropList(params));
    }

    @ApiOperation(value = "安全隐患等级", notes = "获取安全隐患等级下拉列表数据")
    @ApiResponses(
            @ApiResponse(code = 200, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id: '安全隐患等级ID'\n" +
                                            "name: '安全隐患等级'\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/safe/dangertype", method = RequestMethod.GET)
    public AjaxResult getSafeDangerTypeDropList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(commonComboService.getSafeDangerTypeDropList(params));
    }

    @ApiOperation(value = "工段", notes = "获取工段下拉列表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:workshop_name", value = "车间名称", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 200, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id: '工段ID'\n" +
                                    "name: '工段名称'\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/safe/workshopsection", method = RequestMethod.GET)
    public AjaxResult getSafeWorkshopSectionDropList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(commonComboService.getSafeWorkshopSectionDropList(params));
    }



}
