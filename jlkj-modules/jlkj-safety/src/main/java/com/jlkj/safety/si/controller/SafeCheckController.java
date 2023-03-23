package com.jlkj.safety.si.controller;


import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.safety.si.service.SafeCheckRecordService;
import com.jlkj.safety.si.utils.ResponseUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author cy
 * @Description 安全巡检
 * @create 2021-04-08
 */
@RestController
@RequestMapping("/safe/check")
@Slf4j
@Api(tags = "安全巡检")
public class SafeCheckController {

	@Autowired
	private SafeCheckRecordService safeCheckRecordService;

	@ApiOperation(value = "安全巡检-查询列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:current", value = "页码-从1开始", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:size", value = "每页行数-[web调用自动传，app不用传此参数固定为10]", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:order", value = "排序字段", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:orderby", value = "顺序-[asc]升序，[desc]降序，传空字符串则升序", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:check_time_start", value = "巡检时间-开始", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:check_time_end", value = "巡检时间-结束", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:safe_area_id", value = "作业区域ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:check_type_id", value = "安全巡检类型ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:check_status", value = "巡检状况(-1全部，1正常，2异常)", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:check_person_name", value = "巡检人员", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "safe_check_id:巡检记录ID\n" +
                                    "check_content:描述问题\n" +
                                    "check_person_name:巡检人\n" +
									"check_type_name:巡检类型\n" +
									"safe_area_name:作业区域\n" +
									"check_image1:巡检图片1ID\n" +
									"check_image2:巡检图片2ID\n" +
									"check_image3:巡检图片3ID\n" +
									"check_image4:巡检图片4ID\n" +
                                    "check_status:地点状况(0未检，1正常，2异常)\n" +
									"check_disposed:状态(0待处理,1已处理)\n" +
                                    "check_time:巡检时间\n" +
									"disposer_id:处理人ID\n" +
									"disposer_image1:处理图片1ID\n" +
									"disposer_image2:处理图片2ID\n" +
									"disposer_image3:处理图片3ID\n" +
									"disposer_id:处理人ID\n" +
									"disposer_name:处理人名称\n" +
									"dispose_content:处理内容\n" +
									"dispose_time:处理时间\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/list", method = RequestMethod.GET)
	//@RoleAndPermission(permission = "check_list")
    public Object getSafeCheckRecordPageList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeCheckRecordService.getSafeCheckRecordPageList(params));
    }

	@ApiOperation(value = "安全巡检-详情", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:safe_check_id", value = "巡检记录ID", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "safe_check_id:巡检记录ID\n" +
									"safe_area_id:巡检区域ID\n" +
									"safe_area_name:巡检区域\n" +
                                    "check_content:巡检内容\n" +
									"check_person_id:巡检人ID\n" +
                                    "check_person_name:巡检人\n" +
									"check_depart_id:巡检部门ID\n" +
                                    "check_depart_name:巡检部门\n" +
									"check_type_id:巡检类型ID\n" +
                                    "check_type_name:巡检类型\n" +
                                    "check_status:巡检状态：0未检，1正常，2异常\n" +
									"check_image1:巡检图片1ID\n" +
									"check_image2:巡检图片2ID\n" +
									"check_image3:巡检图片3ID\n" +
									"check_image4:巡检图片4ID\n" +
                                    "check_image1_path:巡检图片1路径\n" +
                                    "check_image2_path:巡检图片2路径\n" +
                                    "check_image3_path:巡检图片3路径\n" +
                                    "check_image4_path:巡检图片4路径\n" +
									"check_disposed:1已处理，其它为未处理\n" +
                                    "check_time:巡检时间\n" +
									"disposer_image1:处理图片1ID\n" +
									"disposer_image2:处理图片2ID\n" +
									"disposer_image3:处理图片3ID\n" +
									"disposer_image1_path:处理图片1路径\n" +
									"disposer_image2_path:处理图片2路径\n" +
									"disposer_image3_path:处理图片3路径\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/info", method = RequestMethod.GET)
	//@RoleAndPermission(permission = "check_info")
    public Object getSafeCheckRecordById(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeCheckRecordService.getSafeCheckRecordById(params));
    }

	@ApiOperation(value = "安全巡检-新增", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "param:safe_area_id", value = "巡检区域ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:safe_area_name", value = "巡检区域", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:check_type_id", value = "巡检类型ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:check_type_name", value = "巡检类型", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:check_content", value = "巡检内容", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:check_status", value = "巡检状态：0未检，1正常，2异常", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:check_depart_id", value = "巡检部门ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:check_depart_name", value = "巡检部门", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:check_person_id", value = "巡检人ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:check_person_name", value = "巡检人", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:check_image1", value = "巡检图片1ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:check_image2", value = "巡检图片2ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:check_image3", value = "巡检图片3ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:check_image4", value = "巡检图片4ID", required = false, dataTypeClass = String.class),
	})
	@ApiResponses(
			@ApiResponse(code = 0, message = "调用成功", response = String.class,
					examples = @Example(
							@ExampleProperty(
									"safe_check_id:巡检记录ID\n"
							)
					)
			)
	)
	@RequestMapping(value = "/insert", method = RequestMethod.POST, produces = "application/json")
	//@RoleAndPermission(permission = "check_insert")
	public Object insertSafeCheckRecord(@RequestBody Map<String, Object> params) {
		log.info("RequestParam => {}", params);
		try {
			return AjaxResult.success(safeCheckRecordService.insertSafeCheckRecord(params));
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseUtil.toError(params, "安全巡检记录添加失败");
		}
	}

	@ApiOperation(value = "安全巡检-修改", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "param:safe_check_id", value = "巡检记录ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:safe_area_id", value = "巡检区域ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:safe_area_name", value = "巡检区域", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:check_type_id", value = "巡检类型ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:check_type_name", value = "巡检类型", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:check_content", value = "巡检内容", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:check_status", value = "巡检状态：0未检，1正常，2异常", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:check_depart_id", value = "巡检部门ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:check_depart_name", value = "巡检部门", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:check_person_id", value = "巡检人ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:check_person_name", value = "巡检人", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:check_image1", value = "巡检图片1ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:check_image2", value = "巡检图片2ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:check_image3", value = "巡检图片3ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:check_image4", value = "巡检图片4ID", required = false, dataTypeClass = String.class),
	})
	@ApiResponses(
			@ApiResponse(code = 0, message = "调用成功", response = String.class)
	)
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
	//@RoleAndPermission(permission = "check_update")
	public Object updateSafeCheckRecord(@RequestBody Map<String, Object> params) {
		log.info("RequestParam => {}", params);
		try {
			return AjaxResult.success(safeCheckRecordService.updateSafeCheckRecord(params));
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseUtil.toError(params, "安全巡检记录修改失败");
		}
	}

	@ApiOperation(value = "安全巡检-删除", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "param:safe_check_id", value = "巡检记录ID", required = false, dataTypeClass = String.class),
	})
	@ApiResponses(
			@ApiResponse(code = 0, message = "调用成功", response = String.class)
	)
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
	//@RoleAndPermission(permission = "check_delete")
	public Object deleteSafeCheckRecord(@RequestBody Map<String, Object> params) {
		try {
			return AjaxResult.success(safeCheckRecordService.deleteSafeCheckRecord(params));
		} catch (Throwable te) {
			log.error("", te);
			return ResponseUtil.toError(params, "安全巡检记录删除失败");
		}
	}

	@ApiOperation(value = "安全巡检-处理", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "param:safe_check_id", value = "巡检记录ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:disposer_id", value = "处理人ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:disposer_name", value = "处理人名称", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:dispose_content", value = "处理内容", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:disposer_image1", value = "处理图片1ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:disposer_image2", value = "处理图片2ID", required = false, dataTypeClass = String.class),
			@ApiImplicitParam(name = "param:disposer_image3", value = "处理图片3ID", required = false, dataTypeClass = String.class),
	})
	@ApiResponses(
			@ApiResponse(code = 0, message = "调用成功", response = String.class)
	)
	@RequestMapping(value = "/dispose", method = RequestMethod.POST, produces = "application/json")
	//@RoleAndPermission(permission = "check_dispose")
	public Object setSafeCheckDisposed(@RequestBody Map<String, Object> params) {
		try {
			return AjaxResult.success(safeCheckRecordService.setSafeCheckDisposed(params));
		} catch (Throwable te) {
			log.error("", te);
			return ResponseUtil.toError(params, "安全巡检记录处理失败");
		}
	}

}
