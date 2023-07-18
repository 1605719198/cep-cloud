package com.jlkj.safety.si.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.safety.si.service.SafeJobHoistingApprovalService;
import com.jlkj.safety.si.service.SafeJobHoistingPersonsService;
import com.jlkj.safety.si.service.SafeJobHoistingSafetyMeasuresService;
import com.jlkj.safety.si.service.SafeJobHoistingService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author su
 * @Description 吊装安全作业证
 * @create 2020-09-21 7:53
 */
@RestController
@RequestMapping("/safe/job/hoisting")
@Slf4j
@Api(tags = "2.4 吊装安全作业证")
public class SafeJobHoistingController extends BaseController {

    @Resource
    SafeJobHoistingService safeJobHoistingService;

    @Resource
    SafeJobHoistingPersonsService safeJobHoistingPersonsService;

    @Resource
    SafeJobHoistingApprovalService safeJobHoistingApprovalService;

    @Resource
    SafeJobHoistingSafetyMeasuresService safeJobHoistingSafetyMeasuresService;

    /**
     * 吊装安全作业证-作业票编号
     * @author 265800
     * @date 2023/7/13 14:47
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "吊装安全作业证-作业票编号", notes = "获取最新的作业票编号")
    @ApiResponses(
            @ApiResponse(code = 200, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "job_code: '作业票编号'\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public Object getjobCode(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobHoistingService.getjobCode(params));
    }

    /**
     * 吊装安全作业证-详情
     * @author 265800
     * @date 2023/7/13 14:47
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "吊装安全作业证-详情", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "作业证ID", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "apply_depart_id:申请单位ID\n" +
                                    "apply_depart_name:申请单位名称\n" +
                                    "apply_person_id:申请人ID\n" +
                                    "apply_person_name:申请人姓名\n" +
                                    "job_code:作业证编号\n" +
                                    "safe_area_id:吊装安全区域ID\n" +
                                    "safe_area_name:吊装安全区域名称\n" +
                                    "place:吊装详细地点\n" +
                                    "hoisting_tools:吊装工具名称\n" +
                                    "job_content:作业内容\n" +
                                    "weight:起吊重物质量\n" +
                                    "start_time:作业开始时间\n" +
                                    "end_time:作业结束时间\n" +
                                    "guardian_person_id:监护人ID\n" +
                                    "guardian_person_name:监护人姓名\n" +
                                    "safe_education_persons:安全教育人\n" +
                                    "harm_identification:危害辨识\n" +
                                    "status:状态(0资料建立,1审批中,2作业中,3作业完成)\n" +
                                    "status_text:状态文本\n" +
                                    "is_timeout:是否超时\n" +
                                    "hoist_name:吊装作业名称\n" +
                                    "job_level:吊装作业级别\n" +
                                    "hoist_charge_id:作业负责人ID\n" +
                                    "hoist_charge_name:作业负责人\n" +
                                    "special_name:交底人\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Object getSafeJobHoistingInfo(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobHoistingService.getSafeJobHoistingInfo(params));
    }

    /**
     * 吊装安全作业证-作业人员列表
     * @author 265800
     * @date 2023/7/13 14:46
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "吊装安全作业证-作业人员列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "作业证ID", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id:行标识\n" +
                                            "construction_unit_id:所属单位ID\n" +
                                            "construction_unit_name:所属单位名称\n" +
                                            "type:所属单位类型(1本厂,2外委)\n" +
                                            "person_id:人员ID\n" +
                                            "person_name:人员姓名\n" +
                                            "work_type_name:工种\n" +
                                            "work_card_code:作业证编号\n" +
                                            "person_type:人员类型(0吊装人员,1吊装指挥)\n" +
                                            "person_type_text:人员类型文本"
                            )
                    )
            )
    )
    @RequestMapping(value = "/person/list", method = RequestMethod.GET)
    public Object getSafeJobHoistingPersonsList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobHoistingService.getSafeJobHoistingPersonsList(params));
    }

    /**
     * 吊装安全作业证-安全措施列表
     * @author 265800
     * @date 2023/7/13 14:46
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "吊装安全作业证-安全措施列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "作业证ID", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id:行标识\n" +
                                    "sort:序号\n" +
                                    "safety_measures:安全措施\n" +
                                    "is_confirm:是否确认(0否,1是)\n" +
                                    "confirm_person_id:确认人ID\n" +
                                    "confirm_person_name:确认人姓名\n" +
                                    "confirm_time:确认时间"
                            )
                    )
            )
    )
    @RequestMapping(value = "/safetymeasures/list", method = RequestMethod.GET)
    public Object getSafeJobHoistingSafetyMeasuresList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobHoistingService.getSafeJobHoistingSafetyMeasuresList(params));
    }

    /**
     * 吊装安全作业证-审批列表
     * @author 265800
     * @date 2023/7/13 14:46
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "吊装安全作业证-审批列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "作业证ID", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id:行标识\n" +
                                    "approval_name:审批环节\n" +
                                    "approval_person_id:审批人ID\n" +
                                    "approval_person_name:审批人姓名\n" +
                                    "is_required:是否需要审批意见(0需要,1不需要)\n" +
                                    "is_approval:是否审批(0未审批,1已审批)\n" +
                                    "content:审批意见\n" +
                                    "confirm_time:确认时间\n" +
                                    "signature:签名BASE64\n" +
                                    "approval_time:审批时间\n" +
                                    "sort:序号\n" +
                                    "pick_person_id:选择人ID\n" +
                                    "pick_person_name:选择人姓名\n" +
                                    "pick_time:选择时间"
                            )
                    )
            )
    )
    @RequestMapping(value = "/approval/list", method = RequestMethod.GET)
    public Object getSafeJobHoistingApprovalList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobHoistingService.getSafeJobHoistingApprovalList(params));
    }

    /**
     * 吊装安全作业证-查询列表
     * @author 265800
     * @date 2023/7/13 14:45
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "吊装安全作业证-查询列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:current", value = "页码-从1开始", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:size", value = "每页行数-[web调用自动传，app不用传此参数固定为10]", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:order", value = "排序字段", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:orderby", value = "顺序-[asc]升序，[desc]降序，传空字符串则升序", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:start_time", value = "开始时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:end_time", value = "结束时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:status", value = "状态(-1全部,0资料建立,1审批中,2作业中,3作业完成)", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_person_id", value = "申请人id", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_area_id", value = "区域id", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_level", value = "作业级别名称", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id:作业证ID\n" +
                                    "apply_depart_id:申请单位ID\n" +
                                    "apply_depart_name:申请单位名称\n" +
                                    "apply_person_id:申请人ID\n" +
                                    "apply_person_name:申请人姓名\n" +
                                    "job_code:作业证编号\n" +
                                    "safe_area_id:吊装安全区域ID\n" +
                                    "safe_area_name:吊装安全区域名称\n" +
                                    "place:吊装安全详细地点\n" +
                                    "hoisting_tools:吊装工具名称\n" +
                                    "job_content:作业内容\n" +
                                    "weight:起吊重物质量\n" +
                                    "start_time:作业开始时间\n" +
                                    "end_time:作业结束时间\n" +
                                    "guardian_person_id:监护人ID\n" +
                                    "guardian_person_name:监护人姓名\n" +
                                    "safe_education_persons:安全教育人\n" +
                                    "harm_identification:危害辨识\n" +
                                    "status:状态(0资料建立,1审批中,2作业中,3作业完成)\n" +
                                    "status_text:状态文本\n" +
                                    "is_timeout:是否超时\n" +
                                    "hoist_name:吊装作业名称\n" +
                                    "job_level:吊装作业级别\n" +
                                    "hoist_charge_id:作业负责人id\n" +
                                    "hoist_charge_name:作业负责人\n" +
                                    "special_name:交底人\n" +
                                    "job_code_erp:ERP作业证编号\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object getSafeJobHoistingPageList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobHoistingService.getSafeJobHoistingPageList(params));
    }

    /**
     * 吊装安全作业证-新增
     * @author 265800
     * @date 2023/7/13 14:45
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "吊装安全作业证-新增", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:apply_depart_id", value = "申请单位ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_depart_name", value = "申请单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_person_id", value = "申请人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_person_name", value = "申请人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_code", value = "作业证编号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_area_id", value = "吊装安全区域ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_area_name", value = "吊装安全区域名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:place", value = "吊装安全详细地点", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:hoisting_tools", value = "吊装工具名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_content", value = "作业内容", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:weight", value = "起吊重物质量", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:start_time", value = "作业开始时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:end_time", value = "作业结束时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:guardian_person_id", value = "监护人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:guardian_person_name", value = "监护人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_education_persons", value = "安全教育人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_education_persons_id", value = "安全教育人id", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:harm_identification", value = "危害辨识", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:hoist_name", value = "吊装作业名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_level", value = "吊装作业级别", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:hoist_charge_id", value = "作业负责人id", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:hoist_charge_name", value = "作业负责人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:special_name", value = "交底人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list", value = "作业人员JSON数组", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[construction_unit_id]", value = "所属单位ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[construction_unit_name]", value = "所属单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[type]", value = "所属单位类型(1本厂,2外委)", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[person_id]", value = "人员ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[person_name]", value = "人员姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[work_type_name]", value = "工种", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[work_card_code]", value = "作业证编号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[person_type]", value = "人员类型(0吊装人员,1吊装指挥)", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:approval_list", value = "审批人员JSON数组", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:approval_list[sort]", value = "序号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:approval_list[approval_name]", value = "审批环节", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:approval_list[approval_person_id]", value = "审批人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:approval_list[approval_person_name]", value = "审批人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:approval_list[pick_person_id]", value = "选择人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:approval_list[pick_person_name]", value = "选择人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:file_list", value = "附件JSON数组", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:file_list[appendix_id]", value = "上传文件ID", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id:'作业证ID'\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/insert", method = RequestMethod.POST, produces = "application/json")
    @Transactional(rollbackFor = Exception.class)
    public Object insertSafeJobHoisting(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return safeJobHoistingService.insertSafeJobHoistingData(params);
    }

    /**
     * 吊装安全作业证-修改
     * @author 265800
     * @date 2023/7/13 14:44
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "吊装安全作业证-修改", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "作业证ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_depart_id", value = "申请单位ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_depart_name", value = "申请单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_person_id", value = "申请人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_person_name", value = "申请人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_code", value = "作业证编号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_area_id", value = "吊装安全区域ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_area_name", value = "吊装安全区域名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:place", value = "吊装安全详细地点", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:hoisting_tools", value = "吊装工具名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_content", value = "作业内容", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:weight", value = "起吊重物质量", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:start_time", value = "作业开始时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:end_time", value = "作业结束时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:guardian_person_id", value = "监护人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:guardian_person_name", value = "监护人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_education_persons", value = "安全教育人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_education_persons_id", value = "安全教育人id", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:harm_identification", value = "危害辨识", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:hoist_name", value = "吊装作业名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_level", value = "吊装作业级别", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:hoist_charge_id", value = "作业负责人id", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:hoist_charge_name", value = "作业负责人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:special_name", value = "交底人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list", value = "作业人员JSON字符串,，内容格式见下面", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[construction_unit_id]", value = "所属单位ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[construction_unit_name]", value = "所属单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[type]", value = "所属单位类型(1本厂,2外委)", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[person_id]", value = "人员ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[person_name]", value = "人员姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[work_type_name]", value = "工种", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[work_card_code]", value = "作业证编号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[person_type]", value = "人员类型(0吊装人员,1吊装指挥)", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:approval_list", value = "审批人员JSON数组", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:approval_list[sort]", value = "序号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:approval_list[approval_name]", value = "审批环节", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:approval_list[approval_person_id]", value = "审批人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:approval_list[approval_person_name]", value = "审批人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:approval_list[pick_person_id]", value = "选择人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:approval_list[pick_person_name]", value = "选择人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:file_list", value = "附件JSON数组", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:file_list[appendix_id]", value = "上传文件ID", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeJobHoisting(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return safeJobHoistingService.updateSafeJobHoistingData(params);
    }

    /**
     * 吊装安全作业证-删除
     * @author 265800
     * @date 2023/7/13 14:44
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "吊装安全作业证-删除", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "作业证ID", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
    public Object deleteSafeJobHoisting(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeJobHoistingService.deleteSafeJobHoisting(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("吊装安全作业证删除失败", params);
        }
    }

    /**
     * 吊装安全作业证-设置审批人
     * @author 265800
     * @date 2023/7/13 14:44
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "吊装安全作业证-设置审批人", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:approval_person_id", value = "审批人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:approval_person_name", value = "审批人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:pick_person_id", value = "选择人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:pick_person_name", value = "选择人姓名", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/update/approval", method = RequestMethod.POST, produces = "application/json")
    public Object updateSafeJobHoistingApproval(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeJobHoistingApprovalService.updateSafeJobHoistingApproval(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("设置审批人失败", params);
        }
    }

    /**
     * 吊装安全作业证-审批
     * @author 265800
     * @date 2023/7/13 14:44
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "吊装安全作业证-审批", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:content", value = "审批意见内容", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:signature", value = "电子签名(base64字符串)", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/confirm/approval", method = RequestMethod.POST, produces = "application/json")
    public Object confirmSafeJobHoistingApproval(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return safeJobHoistingApprovalService.confirmSafeJobHoistingApproval(params);
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("审批失败", params);
        }
    }

    /**
     * 吊装安全作业证-确认
     * @author 265800
     * @date 2023/7/13 14:43
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "吊装安全作业证-确认", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/confirm/approval/time", method = RequestMethod.POST, produces = "application/json")
    public Object confirmSafeJobHoistingApprovalTime(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return safeJobHoistingApprovalService.confirmSafeJobHoistingApprovalTime(params);
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error( "确认失败", params);
        }
    }

    /**
     * 吊装安全作业证-审批退回
     * @author 265800
     * @date 2023/7/13 14:43
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "吊装安全作业证-审批退回", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/confirm/return", method = RequestMethod.POST, produces = "application/json")
    public Object confirmSafeJobHoistingApprovalReturn(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return safeJobHoistingApprovalService.confirmSafeJobHoistingApprovalReturn(params);
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error( "确认失败" ,params);
        }
    }

    /**
     * 吊装安全作业证-安全措施确认
     * @author 265800
     * @date 2023/7/13 14:42
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "吊装安全作业证-安全措施确认", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:confirm_person_id", value = "确认人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:confirm_person_name", value = "确认人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safety_measures", value = "安全措施", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/confirm/safetymeasures", method = RequestMethod.POST, produces = "application/json")
    public Object confirmSafeJobHoistingSafetyMeasures(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeJobHoistingSafetyMeasuresService.confirmSafeJobHoistingSafetyMeasures(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("安全措施确认失败", params);
        }
    }

    /**
     * 吊装安全作业证-新增作业人员
     * @author 265800
     * @date 2023/7/13 14:42
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "吊装安全作业证-新增作业人员", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:job_id", value = "作业证ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:construction_unit_id", value = "所属单位ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:construction_unit_name", value = "所属单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:type", value = "所属单位类型(1本厂,2外委)", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_id", value = "人员ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_name", value = "人员姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:work_type_name", value = "工种", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:work_card_code", value = "作业证编号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_type", value = "人员类型(0吊装人员,1吊装指挥)", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/insert/person", method = RequestMethod.POST, produces = "application/json")
    public Object insertSafeJobHoistingPersons(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            if (safeJobHoistingPersonsService.insertSafeJobHoistingPersons(params)) {
                return AjaxResult.success("作业人员保存成功", params);
            } else {
                return AjaxResult.error("作业人员保存失败", params);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("作业人员保存失败",params);
        }
    }

    /**
     * 吊装安全作业证-删除作业人员
     * @author 265800
     * @date 2023/7/13 14:48
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "吊装安全作业证-删除作业人员", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/delete/person", method = RequestMethod.POST, produces = "application/json")
    public Object deleteSafeJobHoistingPersons(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            if (safeJobHoistingPersonsService.deleteSafeJobHoistingPersons(params)) {
                return AjaxResult.success("作业人员删除成功", params);
            } else {
                return AjaxResult.error("作业人员删除失败", params);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("作业人员删除失败", params);
        }
    }

    /**
     * 吊装安全作业证-批量保存作业人员
     * @author 265800
     * @date 2023/7/13 14:48
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "吊装安全作业证-批量保存作业人员", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "作业证ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list", value = "作业人员JSON字符串，内容格式见下面", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[construction_unit_id]", value = "所属单位ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[construction_unit_name]", value = "所属单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[type]", value = "所属单位类型(1本厂,2外委)", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[person_id]", value = "人员ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[person_name]", value = "人员姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[work_type_name]", value = "工种", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[work_card_code]", value = "作业证编号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[person_type]", value = "人员类型(0吊装人员,1吊装指挥)", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/person/batch/save", method = RequestMethod.POST, produces = "application/json")
    @Transactional(rollbackFor = Exception.class)
    public Object batchSaveSafeJobHoistingPerson(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return safeJobHoistingService.batchSaveSafeJobHoistingPerson(params);
    }

    /**
     * 吊装作业证-安全措施取消
     * @author 265800
     * @date 2023/7/13 14:48
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "吊装作业证-安全措施取消", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/cancel/safetymeasures", method = RequestMethod.POST, produces = "application/json")
    public Object cancelSafeJobHoistingSafetyMeasures(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeJobHoistingSafetyMeasuresService.cancelSafeJobHoistingSafetyMeasures(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("安全措施取消失败", params);
        }
    }

    /**
     * 吊装安全作业证-审批模板列表
     * @author 265800
     * @date 2023/7/13 14:49
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "吊装安全作业证-审批模板列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:job_level", value = "作业级别[一级,二级,三级]", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "job_level:作业级别\n" +
                                    "sort:序号\n" +
                                    "approval_name:审批名称\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/approval/template/list", method = RequestMethod.GET)
    public Object getSafeJobHoistingApprovalTemplateList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobHoistingService.getSafeJobHoistingApprovalTemplateList(params));
    }

    /**
     * 吊装安全作业证-附件列表
     * @author 265800
     * @date 2023/7/13 14:49
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "吊装安全作业证-附件列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "作业证ID", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "appendix_id:附件ID\n" +
                                    "file_name:附件名称"
                            )
                    )
            )
    )
    @RequestMapping(value = "/file/list", method = RequestMethod.GET)
    public Object getSafeJobHoistingFileList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobHoistingService.getSafeJobHoistingFileList(params));
    }

    /**
     * 吊装安全作业证-修改作业证编号
     * @author 265800
     * @date 2023/7/13 14:50
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "吊装安全作业证-修改作业证编号", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:uuid", value = "作业证ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:dangerNo", value = "作业证编号", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/update/jobcode", method = RequestMethod.POST, produces = "application/json")
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeJobHoistingJobCode(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobHoistingService.updateSafeJobHoistingJobCode(params));
    }
}
