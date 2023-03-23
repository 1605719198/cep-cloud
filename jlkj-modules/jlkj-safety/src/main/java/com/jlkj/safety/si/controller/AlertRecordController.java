package com.jlkj.safety.si.controller;


import com.jlkj.common.log.annotation.Log;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.safety.si.service.AlertRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import java.util.List;
import java.util.Map;


/**
 * @author yzl
 * @Description 环保报警
 * @create 2022-12-14 09:36:40.866316
 */
@Tag(name = "环保报警")
@RestController
@RequestMapping("/alertRecord")
@Slf4j
public class AlertRecordController {
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    AlertRecordService alertRecordService;

    @Operation(summary = "环保看板 - 查询环保报警",
            parameters = {},
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "id", description = "主键标识;UUID"),
                                    @ExampleObject(name = "type_id", description = "报警类型id"),
                                    @ExampleObject(name = "name", description = "报警名称"),
                                    @ExampleObject(name = "sourceh_id", description = "报警源id"),
                                    @ExampleObject(name = "sourceh_name", description = "报警源名称"),
                                    @ExampleObject(name = "type", description = "报警类型"),
                                    @ExampleObject(name = "alarm_level", description = "报警等级"),
                                    @ExampleObject(name = "push_way", description = "报警推送方式"),
                                    @ExampleObject(name = "context", description = "报警内容"),
                                    @ExampleObject(name = "user_id", description = "用户id或者角色id"),
                                    @ExampleObject(name = "push_time", description = "报警推送时间"),
                                    @ExampleObject(name = "status", description = "报警消息状态,是否已读"),
                                    @ExampleObject(name = "isUserOrRole", description = "报警推给用户还是角色"),
                                    @ExampleObject(name = "upper_limit", description = "报警上限"),
                                    @ExampleObject(name = "lower_limit", description = "报警下限"),
                                    @ExampleObject(name = "alarm_value", description = "报警结果"),
                                    @ExampleObject(name = "alarm_reson", description = "报警原因"),
                                    @ExampleObject(name = "equipment_or_inventory", description = "报警设备或部门"),
                                    @ExampleObject(name = "create_time", description = "创建时间"),
                                    @ExampleObject(name = "create_by", description = "创建人"),
                                    @ExampleObject(name = "update_time", description = "修改时间"),
                                    @ExampleObject(name = "update_by", description = "修改人"),
                                    @ExampleObject(name = "is_handle", description = "是否处理（0否1是）"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "业务操作-查询", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getAlertRecord", method = RequestMethod.GET)
    public Object getAlertRecord() {
        List<Map<String, String>> list = alertRecordService.getAlertRecord();
        return AjaxResult.success(list);
    }

    @Operation(summary = "环保看板 - 超标次数分析",
            parameters = {},
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "ParticulateMatter", description = "颗粒物"),
                                    @ExampleObject(name = "SO2", description = "二氧化硫"),
                                    @ExampleObject(name = "NitrogenOxides", description = "氮氧化物"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "业务操作-查询", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getAlertRecordExceedance", method = RequestMethod.GET)
    public Object getAlertRecordExceedance() {
        Map<String, String> list = alertRecordService.getAlertRecordExceedance();
        return AjaxResult.success(list);
    }

    @Operation(summary = "首页 - 环保指标",
            parameters = {},
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "sourceh_name", description = "废物排放名称"),
                                    @ExampleObject(name = "alarm_value", description = "实绩值"),
                                    @ExampleObject(name = "emission_limit", description = "指标值"),
                                    @ExampleObject(name = "exceedance", description = "超标值"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "业务操作-查询", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getHomeAlertRecord", method = RequestMethod.GET)
    public Object getHomeAlertRecord() {
        List<Map<String, String>> list = alertRecordService.getHomeAlertRecord();
        return AjaxResult.success(list);
    }


}

