package com.jlkj.safety.si.controller;


import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.safety.si.entity.SafeSiDangerRecord;
import com.jlkj.safety.si.service.SafeDangerRecordService;
import com.jlkj.safety.si.utils.ResponseUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * 安全隐患
 * @author su
 * @since 2022-06-22
 */
@RestController
@RequestMapping("/safe/danger")
@Slf4j
@Api(tags = "安全隐患")
public class SafeDangerController {

	@Resource
	private SafeDangerRecordService safeDangerRecordService;

	@ApiOperation(value = "安全隐患-查询列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:current", value = "页码-从1开始", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:size", value = "每页行数-[web调用自动传，app不用传此参数固定为10]", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:order", value = "排序字段", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:orderby", value = "顺序-[asc]升序，[desc]降序，传空字符串则升序", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:danger_time_start", value = "隐患上传时间起始", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:danger_time_end", value = "隐患上传时间结束", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:danger_status", value = "状态(-1全部,0待整改,1待验收,2已验收)", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:timeout_status", value = "是否超时(-1全部,0未超时,1已超时)", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:safe_area_id", value = "区域ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:danger_type_id", value = "隐患等级ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:id", value = "主键", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "danger_id:隐患记录ID\n" +
                                    "danger_number:隐患编号\n" +
                                    "submit_depart_name:组织单位\n" +
                                    "submit_person_name:安全员\n" +
                                    "danger_time:隐患上报时间\n" +
                                    "safe_area_name:区域\n" +
                                    "danger_status:状态(0待整改,1待验收,2已验收)\n" +
									"safe_area_id:区域ID\n" +
									"danger_type_id:隐患等级ID\n" +
									"danger_type_name:隐患等级\n" +
									"timeout_status:超时状态(0未超时,1已超时)\n" +
									"duty_depart_id:责任单位id\n" +
									"duty_depart_name:责任单位\n" +
									"duty_person_id:负责人id\n" +
									"duty_person_name:负责人\n" +
									"danger_content:隐患内容\n" +
									"danger_deadline_time:限期整改时间\n" +
									"submit_image1:隐患图片1ID\n" +
									"submit_image2:隐患图片2ID\n" +
									"submit_image3:隐患图片3ID\n" +
									"submit_image4:隐患图片4ID\n" +
									"submit_person_id:提交人id\n" +
									"acceptance_person_id:验收人ID\n" +
									"acceptance_person_name:验收人\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object getSafeDangerRecordPageList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeDangerRecordService.getSafeDangerRecordPageList(params));
    }

	@ApiOperation(value = "安全隐患-详情", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:danger_id", value = "隐患记录ID", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "danger_id:隐患记录ID\n" +
									"danger_content:隐患内容\n" +
									"dispose_content:整改内容\n" +
                                    "dispose_measure:整改措施\n" +
                                    "danger_time:隐患上报时间\n" +
									"dispose_time:隐患处理时间\n" +
                                    "danger_deadline_time:限期整改时间\n" +
                                    "submit_image1:隐患图片1路径\n" +
                                    "submit_image2:隐患图片2路径\n" +
                                    "submit_image3:隐患图片3路径\n" +
                                    "submit_image4:隐患图片4路径\n" +
                                    "submit_sign_image:提交人签名图片\n" +
									"acceptance_depart_name:验收单位\n" +
									"acceptance_appraise:验收评价\n" +
									"acceptance_time:验收时间\n" +
									"acceptance_image1:验收图片1路径\n" +
                                    "acceptance_image2:验收图片2路径\n" +
                                    "acceptance_image3:验收图片3路径\n" +
                                    "acceptance_image4:验收图片4路径\n" +
                                    "acceptance_sign_image:验收人签名图片\n" +
                                    "danger_status:状态(0待整改,1待验收,2已验收)\n" +
									"submit_person_id:安全员ID\n" +
									"submit_person_name:安全员\n" +
									"acceptance_person_id:验收人ID\n" +
									"acceptance_person_name:验收人\n" +
									"safe_area_id:区域ID\n" +
									"safe_area_name:区域\n" +
									"danger_type_id:隐患等级ID\n" +
									"danger_type_name:隐患等级\n" +
									"duty_depart_id:责任单位ID\n" +
									"duty_depart_name:责任单位\n" +
									"duty_person_id:负责人ID\n" +
									"duty_person_name:负责人\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Object getSafeDangerRecordById(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeDangerRecordService.getSafeDangerRecordById(params));
    }

	@ApiOperation(value = "安全隐患-保存", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:safe_area_id", value = "区域ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_area_name", value = "区域", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:danger_type_id", value = "隐患等级ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:danger_type_name", value = "隐患等级", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:danger_content", value = "隐患内容", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:dispose_measure", value = "整改措施", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:danger_deadline_time", value = "限期完成时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:duty_depart_id", value = "负责单位ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:duty_depart_name", value = "负责单位", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:duty_person_id", value = "负责人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:duty_person_name", value = "负责人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:submit_depart_id", value = "组织单位ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:submit_depart_name", value = "组织单位", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:submit_person_id", value = "安全员ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:submit_person_name", value = "安全员", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:acceptance_person_id", value = "验收人ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:acceptance_person_name", value = "验收人", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:submit_image1", value = "隐患图片1ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:submit_image2", value = "隐患图片2ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:submit_image3", value = "隐患图片3ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:submit_image4", value = "隐患图片4ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:submit_sign_image", value = "提交人签名图片ID", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object addSafeDangerRecord(@RequestBody Map<String, Object> params) {
		try {
			SafeSiDangerRecord safeSiDangerRecord;
			Map<String, Object> objectMap = (Map<String, Object>) safeDangerRecordService.addSafeDangerRecord(params);
			int code = Integer.parseInt(objectMap.get("code").toString());
			if (code == 0) {
				safeSiDangerRecord = (SafeSiDangerRecord) objectMap.get("data");
				Map<String, Object> outData = new HashMap<>(1);
				outData.put("id", safeSiDangerRecord.getId());
				return AjaxResult.success(ResponseUtil.toResult(params, "安全隐患保存成功", outData));
			}
			else {
				return ResponseUtil.toResult(params, "安全隐患保存失败");
			}
		} catch (Throwable te) {
			log.error("", te);
			return ResponseUtil.toError(params, "安全隐患保存失败");
		}
    }

	@ApiOperation(value = "安全隐患-整改", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "param:danger_id", value = "隐患记录ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:dispose_content", value = "整改内容", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:disposer_id", value = "处理人ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:disposer_name", value = "处理人", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:acceptance_person_id", value = "验收人ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:acceptance_person_name", value = "验收人", required = false, dataTypeClass = String.class),
	})
	@ApiResponses(
			@ApiResponse(code = 0, message = "调用成功", response = String.class)
	)
	@RequestMapping(value = "/dispose", method = RequestMethod.POST, produces = "application/json")
	public Object disposeSafeDanger(@RequestBody Map<String, Object> params) {
		try {
			return AjaxResult.success(safeDangerRecordService.disposeSafeDanger(params));
		} catch (Throwable te) {
			log.error("", te);
			return ResponseUtil.toError(params, "整改安全隐患记录失败");
		}
	}

	@ApiOperation(value = "安全隐患-验收", notes = "")
    @ApiImplicitParams({
    		@ApiImplicitParam(name = "param:danger_id", value = "隐患记录ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:acceptance_depart_id", value = "验收单位ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:acceptance_depart_name", value = "验收单位", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:acceptance_person_id", value = "验收人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:acceptance_person_name", value = "验收人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:acceptance_appraise", value = "验收评价", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:acceptance_image1", value = "验收图片1ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:acceptance_image2", value = "验收图片2ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:acceptance_image3", value = "验收图片3ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:acceptance_image4", value = "验收图片4ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:acceptance_sign_image", value = "验收人签名图片ID", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/acceptance", method = RequestMethod.POST)
    public Object acceptanceSafeDanger(@RequestBody Map<String, Object> params) {
		try {
			return AjaxResult.success(safeDangerRecordService.acceptanceSafeDanger(params));
		} catch (Throwable te) {
			log.error("", te);
			return ResponseUtil.toError(params, "验收安全隐患记录失败");
		}
    }

	@ApiOperation(value = "安全隐患-删除", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "param:danger_id", value = "隐患记录ID", required = false, dataTypeClass = String.class)
	})
	@ApiResponses(
			@ApiResponse(code = 0, message = "调用成功", response = String.class)
	)
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
	public Object deleteSafeDanger(@RequestBody Map<String, Object> params) {
		log.info("RequestParam => {}", params);
		try {
			return AjaxResult.success(safeDangerRecordService.deleteSafeDanger(params));
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseUtil.toError(params, "安全隐患删除失败");
		}
	}
}
