package com.jlkj.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jlkj.common.core.constant.CommonConstant;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.system.dto.humanresourceschedule.GetHumanresourceScheduleDTO;
import com.jlkj.system.entity.HumanresourceSchedule;
import com.jlkj.system.service.HumanresourceScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import java.util.List;
import java.util.Map;

import static com.jlkj.common.core.constant.SysLogConstant.SYS_LOG_PARAM_KEY;


/**
 * @author yzl
 * @Description 排班时间
 * @create 2023-04-07 09:44:19.098520
 */
@Tag(name = "排班时间")
@RestController
@RequestMapping("/humanresourceSchedule")
@Slf4j
public class HumanresourceScheduleController {
    @Resource
    HttpServletRequest httpServletRequest;
    @Resource
    HumanresourceScheduleService humanresourceScheduleService;

    @Operation(summary = "查询 指定时间的排班信息",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "shift_no", description = "班别代码(ABCD)"),
                                    @ExampleObject(name = "shift_name", description = "班别名称(甲乙丙丁)"),
                                    @ExampleObject(name = "class_no", description = "班次代码(00、 2201 、 2202)"),
                                    @ExampleObject(name = "class_name", description = "班次名称(休息、白班、夜班)"),
                                    @ExampleObject(name = "schedule_date", description = "排班日期（2023-03-03）"),
                                    @ExampleObject(name = "start_time", description = "本班开始时间（2023-03-03 08:00:00）"),
                                    @ExampleObject(name = "end_time", description = "本班结束时间（2023-03-03 20:00:00）"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "指定时间的排班信息-查询列表", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list(@Validated @ParamModel GetHumanresourceScheduleDTO dto) {
        log.info("params => " + dto);
        String errorMsg = ValidUtil.checkValid(dto);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        QueryWrapper<HumanresourceSchedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(" shift_no,shift_name,class_no,class_name,schedule_date,DATE_FORMAT(start_time, '%Y-%m-%d %H:%i:%s') AS start_time,DATE_FORMAT(end_time, '%Y-%m-%d %H:%i:%s') AS end_time ")
                .lambda()
                .le(HumanresourceSchedule::getStartTime, dto.getStartTime())
                .ge(HumanresourceSchedule::getEndTime, dto.getStartTime())
                .last(CommonConstant.LIMIT_ONE_ROW);
        List<Map<String, Object>> list = humanresourceScheduleService.listMaps(queryWrapper);
        return AjaxResult.success(list);
    }

}

