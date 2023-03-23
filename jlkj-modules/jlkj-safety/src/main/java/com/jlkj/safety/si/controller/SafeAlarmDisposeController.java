package com.jlkj.safety.si.controller;

import com.jlkj.safety.si.service.SafeAlarmDisposeService;
import com.jlkj.safety.si.utils.ResponseUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * @author cy
 * @Description 安全报警处理
 * @create 2021-04-26
 */
@RestController
@RequestMapping("/safe/gasalarm")
@Slf4j
@Api(tags = "安全报警处理")
public class SafeAlarmDisposeController {

	@Autowired
	private SafeAlarmDisposeService safeAlarmDisposeService;

	private SimpleDateFormat dateTime17Format = new SimpleDateFormat("yyyyMMddHHmmss");

	@ApiOperation(value = "安全报警处理-查询列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:page", value = "页码-从1开始", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:size", value = "每页行数-[web调用自动传，app不用传此参数固定为10]", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:user_id", value = "当前登录人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:alarm_status", value = "状态：(2待处理，3待确认，其他或不传是全部)", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
									"gasalarm_id:报警处理记录ID\n" +
									"alarm_time:报警时间\n" +
									"alarm_local:报警区域\n" +
									"device_position:报警探头位置\n" +
									"device_index:报警探头位号\n" +
									"notifier_id:通知人ID\n" +
									"notifier_name:通知人名称\n" +
									"alarm_value:报警值\n" +
									"disposer_id:处理人ID\n" +
									"disposer_name:处理人名称\n" +
									"confirm_person_id:确认人ID\n" +
									"confirm_person_name:确认人名称\n" +
									"alarm_status:状态（1自动采集待上传，2待处理，3待确认，4已确认完成）\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object getSafeAlarmDisposePageList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return safeAlarmDisposeService.getSafeAlarmDisposePageList(params);
    }

	@ApiOperation(value = "安全报警处理-详情", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:gasalarm_id", value = "报警处理记录ID", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "gasalarm_id:报警处理记录ID\n" +
                                    "alarm_time:报警时间\n" +
                                    "alarm_local:报警区域\n" +
                                    "device_position:报警探头位置\n" +
                                    "device_index:报警探头位号\n" +
                                    "notifier_id:通知人ID\n" +
                                    "notifier_name:通知人名称\n" +
                                    "alarm_value:报警值\n" +
                                    "disposer_id:处理人ID\n" +
                                    "disposer_name:处理人名称\n" +
                                    "dispose_time:上传处理时间\n" +
                                    "confirm_person_id:确认人ID\n" +
                                    "confirm_person_name:确认人名称\n" +
									"confirm_time:上传确认时间\n" +
									"alarm_case:报警原因\n" +
									"dispose_measure:处理措施\n" +
									"return_normal_time:恢复正常时间\n" +
									"alarm_status:状态（1自动采集待上传，2待处理，3待确认，4已确认完成）\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Object getSafeDangerRecordById(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
		return safeAlarmDisposeService.getSafeAlarmDisposeById(params);
    }


	@ApiOperation(value = "安全报警处理-上报新数据", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:alarm_time", value = "报警时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:alarm_local", value = "报警区域", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:device_position", value = "报警探头位置", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:device_index", value = "报警探头位号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:notifier_id", value = "通知人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:notifier_name", value = "通知人名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:alarm_value", value = "报警值", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:disposer_id", value = "处理人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:disposer_name", value = "处理人名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:confirm_person_id", value = "确认人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:confirm_person_name", value = "确认人名称", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/insertnew", method = RequestMethod.POST, produces = "application/json")
    public Object addNewSafeAlarmDispose( @RequestBody Map<String, Object> params) {
		try {
			log.info("RequestParam => {}", params);
			return safeAlarmDisposeService.addNewSafeAlarmDispose(params);
		} catch (Throwable te) {
			log.error("", te);
			return ResponseUtil.toError(params, "安全报警上报失败");
		}
    }

	@ApiOperation(value = "安全报警处理-上报自动采集数据", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "param:gasalarm_id", value = "报警处理记录ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:disposer_id", value = "处理人ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:disposer_name", value = "处理人名称", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:confirm_person_id", value = "确认人ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:confirm_person_name", value = "确认人名称", required = false, dataTypeClass = String.class),
	})
	@ApiResponses(
			@ApiResponse(code = 0, message = "调用成功", response = String.class)
	)
	@RequestMapping(value = "/insertauto", method = RequestMethod.POST, produces = "application/json")
	public Object addAutoSafeAlarmDispose( @RequestBody Map<String, Object> params) {
		try {
			log.info("RequestParam => {}", params);
			return safeAlarmDisposeService.addAutoSafeAlarmDispose(params);
		} catch (Throwable te) {
			log.error("", te);
			return ResponseUtil.toError(params, "安全报警上报失败");
		}
	}

	@ApiOperation(value = "安全报警处理-上传处理", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "param:gasalarm_id", value = "报警处理记录ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:alarm_case", value = "报警原因", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:dispose_measure", value = "处理措施", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:confirm_person_id", value = "确认人ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:confirm_person_name", value = "确认人名称", required = false, dataTypeClass = String.class),
	})
	@ApiResponses(
			@ApiResponse(code = 0, message = "调用成功", response = String.class)
	)
	@RequestMapping(value = "/dispose", method = RequestMethod.POST, produces = "application/json")
	public Object doDisposeSafeAlarmDispose( @RequestBody Map<String, Object> params) {
		try {
			log.info("RequestParam => {}", params);
			return safeAlarmDisposeService.doDisposeSafeAlarmDispose(params);
		} catch (Throwable te) {
			log.error("", te);
			return ResponseUtil.toError(params, "安全报警处理失败");
		}
	}

	@ApiOperation(value = "安全报警处理-上传确认", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "param:gasalarm_id", value = "报警处理记录ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:return_normal_time", value = "恢复正常时间", required = false, dataTypeClass = String.class),
	})
	@ApiResponses(
			@ApiResponse(code = 0, message = "调用成功", response = String.class)
	)
	@RequestMapping(value = "/confirm", method = RequestMethod.POST, produces = "application/json")
	public Object doConfirmSafeAlarmDispose(@RequestBody Map<String, Object> params) {
		try {
			log.info("RequestParam => {}", params);
			return safeAlarmDisposeService.doConfirmSafeAlarmDispose(params);
		} catch (Throwable te) {
			log.error("", te);
			return ResponseUtil.toError(params, "安全报警确认失败");
		}
	}

}
