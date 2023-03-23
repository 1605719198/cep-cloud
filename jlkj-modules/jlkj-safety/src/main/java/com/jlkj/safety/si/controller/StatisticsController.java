package com.jlkj.safety.si.controller;

import com.jlkj.safety.si.service.StatisticsService;
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
 * @Description 安安环统计
 * @create 2020-09-21 7:53
 */
@RestController
@RequestMapping("/statistics")
@Slf4j
@Api(tags = "3.1 安环统计")
public class StatisticsController {

    @Resource
    StatisticsService statisticsService;

    @ApiOperation(value = "报警历史", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:current", value = "页码-从1开始", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:size", value = "每页行数-[web调用自动传，app不用传此参数固定为10]", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:order", value = "排序字段", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:orderby", value = "顺序-[asc]升序，[desc]降序，传空字符串则升序", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:start_time", value = "开始时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:end_time", value = "结束时间", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id:行标识\n" +
                                            "safe_area_name:报警区域\n" +
                                            "alarm_type:报警类型\n" +
                                            "alarm_position:报警位置\n" +
                                            "alarm_time:报警时间\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/alarm/his", method = RequestMethod.GET)
    public Object getSafeJobFirePageList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return statisticsService.getSafeAlarmHisList(params);
    }





}
