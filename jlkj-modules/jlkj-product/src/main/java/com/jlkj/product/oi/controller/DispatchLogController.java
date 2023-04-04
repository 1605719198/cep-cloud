package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.resp.PageResult;
import com.jlkj.common.core.web.resp.Result;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.dispatchlog.*;
import com.jlkj.product.oi.service.DispatchLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * 控制器-调度日志
 * @author sudeyou
 */
@Tag(name = "调度日志")
@RestController
@RequestMapping("/dispathLog")
@Slf4j
public class DispatchLogController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private DispatchLogService dispatchLogService;

    @Operation(summary = "查询-分页-调度日志",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
                    @Parameter(name = "id", description = "主键"),
                    @Parameter(name = "work_content", description = "工作内容"),
                    @Parameter(name = "dispath_time_start", description = "调度时间[开始]"),
                    @Parameter(name = "dispath_time_end", description = "调度时间[结束]"),
                    @Parameter(name = "dispatcher_id", description = "调度人员id"),
                    @Parameter(name = "worker_id", description = "处理人员id"),
                    @Parameter(name = "state_flag", description = "状态标识"),
                    @Parameter(name = "current", description = "页码-从1开始"),
                    @Parameter(name = "size", description = "每页行数"),
                    @Parameter(name = "order", description = "排序字段"),
                    @Parameter(name = "orderby", description = "排序规则字段")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "id", description = "调度记录编号"),
                                    @ExampleObject(name = "dispatcher_id", description = "调度人员ID"),
                                    @ExampleObject(name = "dispatcher_name", description = "调度人员姓名"),
                                    @ExampleObject(name = "worker_id", description = "处理人员ID"),
                                    @ExampleObject(name = "worker_name", description = "处理人员姓名"),
                                    @ExampleObject(name = "work_content", description = "工作内容"),
                                    @ExampleObject(name = "dispose_content", description = "处理说明"),
                                    @ExampleObject(name = "dispose", description = "处理结果"),
                                    @ExampleObject(name = "state_flag", description = "完成标识"),
                                    @ExampleObject(name = "create_time", description = "填报时间"),
                                    @ExampleObject(name = "dispath_time", description = "调度时间"),
                                    @ExampleObject(name = "dispose_time", description = "处理反馈时间"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )

    @Log(title = "查询-分页-调度日志",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getDataDispathLogs", method = RequestMethod.GET)
    public Object getPageData(@Validated @ParamModel PageDispatchLogDTO pageDispatchLogDTO) {
        log.info("params => " + pageDispatchLogDTO);
        String errorMsg = ValidUtil.checkValid(pageDispatchLogDTO);
        if (!"".equals(errorMsg)) {
            return Result.validatedFailure(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageDispatchLogDTO);
        return PageResult.success(dispatchLogService.getPageData(pageDispatchLogDTO));
    }

    @Operation(summary = "新增-调度日志",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                                    @ExampleObject(name = "dispatcher_id", description = "调度人员ID"),
                                    @ExampleObject(name = "dispatcher_name", description = "调度人员姓名"),
                                    @ExampleObject(name = "worker_id", description = "处理人员ID"),
                                    @ExampleObject(name = "worker_name", description = "处理人员姓名"),
                                    @ExampleObject(name = "work_content", description = "工作内容"),
                                    @ExampleObject(name = "dispath_time", description = "调度时间"),
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "新增-调度日志",businessType = BusinessType.INSERT)
    @RequestMapping(value = "/saveDispathLog", method = RequestMethod.POST, produces = "application/json")
    public Object addData(@Valid @RequestBody AddDispatchLogDTO addDispatchLogDTO) {
        log.info("params => " + addDispatchLogDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, addDispatchLogDTO);
        return dispatchLogService.addData(addDispatchLogDTO);
    }

    @Operation(summary = "修改-调度日志",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                                    @ExampleObject(name = "id", description = "调度记录编号"),
                                    @ExampleObject(name = "worker_id", description = "处理人员ID"),
                                    @ExampleObject(name = "worker_name", description = "处理人员姓名"),
                                    @ExampleObject(name = "work_content", description = "工作内容"),
                                    @ExampleObject(name = "dispath_time", description = "调度时间"),
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "修改-调度日志",businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/updateDispathLog", method = RequestMethod.PUT, produces = "application/json")
    public Object updateData(@Valid @RequestBody UpdateDispatchLogDTO updateDispatchLogDTO) {
        log.info("params => " + updateDispatchLogDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, updateDispatchLogDTO);
        return dispatchLogService.updateData(updateDispatchLogDTO);
    }

    @Operation(summary = "删除-调度日志",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                                    @ExampleObject(name = "id", description = "调度记录编号"),
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "删除-调度日志",businessType = BusinessType.DELETE)
    @RequestMapping(value = "/deleteDispathLog", method = RequestMethod.DELETE, produces = "application/json")
    public Object deleteData(@Valid @RequestBody DeleteDispatchLogDTO deleteDispatchLogDTO) {
        log.info("params => " + deleteDispatchLogDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteDispatchLogDTO);
        return dispatchLogService.deleteData(deleteDispatchLogDTO);
    }

    @Operation(summary = "指令下发-调度日志",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "id", description = "调度记录编号"),
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "指令下发-调度日志",businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/sendDispathLog", method = RequestMethod.PUT, produces = "application/json")
    public Object updateSendData(@Valid @RequestBody SendDispatchLogDTO sendDispatchLogDTO) {
        log.info("params => " + sendDispatchLogDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, sendDispatchLogDTO);
        return dispatchLogService.updateSendData(sendDispatchLogDTO);
    }

    @Operation(summary = "处理结果反馈-调度日志",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "id", description = "调度记录编号"),
                            @ExampleObject(name = "dispose_content", description = "处理说明"),
                            @ExampleObject(name = "dispose", description = "处理结果")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "处理结果反馈-调度日志",businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/feedbackDispathLog", method = RequestMethod.PUT, produces = "application/json")
    public Object updateFeedbackData(@Valid @RequestBody FeedbackDispatchLogDTO feedbackDispatchLogDTO) {
        log.info("params => " + feedbackDispatchLogDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, feedbackDispatchLogDTO);
        return dispatchLogService.updateFeedbackData(feedbackDispatchLogDTO);
    }

    @Operation(summary = "完成确认-调度日志",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "id", description = "调度记录编号"),
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "完成确认-调度日志",businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/updateDispathLogState", method = RequestMethod.PUT, produces = "application/json")
    public Object updateCompleteData(@Valid @RequestBody CompleteDispatchLogDTO completeDispatchLogDTO) {
        log.info("params => " + completeDispatchLogDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, completeDispatchLogDTO);
        return dispatchLogService.updateCompleteData(completeDispatchLogDTO);
    }
}

