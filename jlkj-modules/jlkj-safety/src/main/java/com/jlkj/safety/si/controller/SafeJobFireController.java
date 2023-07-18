package com.jlkj.safety.si.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.safety.si.service.*;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author su
 * @Description 动火作业证
 * @create 2020-09-21 7:53
 */
@RestController
@RequestMapping("/safe/job/fire")
@Slf4j
@Api(tags = "2.1 动火作业证")
public class SafeJobFireController {

    @Resource
    SafeJobFireService safeJobFireService;

    @Resource
    SafeJobFireApprovalService safeJobFireApprovalService;

    @Resource
    SafeJobFireSafetyMeasuresService safeJobFireSafetyMeasuresService;

    @Resource
    SafeJobFireAnalysisService safeJobFireAnalysisService;

    @Resource
    SafeJobFirePersonsService safeJobFirePersonsService;

    /**
     * 动火作业证-作业票编号
     * @author 265800
     * @date 2023/7/14 9:07
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "动火作业证-作业票编号", notes = "获取最新的作业票编号")
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
        return AjaxResult.success(safeJobFireService.getjobCode(params));
    }

    /**
     * 动火作业证-详情
     * @author 265800
     * @date 2023/7/14 9:07
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "动火作业证-详情", notes = "")
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
                                    "job_level:动火作业级别\n" +
                                    "safe_area_id:动火区域ID\n" +
                                    "safe_area_name:动火区域名称\n" +
                                    "place:动火详细地点\n" +
                                    "mode:动火方式\n" +
                                    "job_responsible_person_name:动火作业负责人\n" +
                                    "start_time:动火开始时间\n" +
                                    "end_time:动火结束时间\n" +
                                    "other_job:涉及的其它特殊作业\n" +
                                    "safe_education_person_name:安全教育人\n" +
                                    "harm_identification:危害辨识\n" +
                                    "guardian_person_id:监护人ID\n" +
                                    "guardian_person_name:监护人姓名\n" +
                                    "work_type_name:工种\n" +
                                    "production_responsible_person_name:生产单位负责人\n" +
                                    "relation_guardian_person_id:相关单位监护人ID\n" +
                                    "relation_guardian_person_name:相关单位监护人姓名\n" +
                                    "relation_guardian_person_work_type_name:相关单位监护人工种\n" +
                                    "status:状态(0资料建立,1审批中,2作业中,3作业完成)\n" +
                                    "status_text:状态文本\n" +
                                    "is_timeout:是否超时\n" +
                                    "equipment_unit_id:设备单位id\n" +
                                    "equipment_unit_name:设备单位名称\n" +
                                    "special_job_no:特种作业证号\n" +
                                    "job_contents:作业内容\n" +
                                    "job_responsible_person_phone:作业负责人电话\n" +
                                    "is_gas_area:是否煤气区域(0否1是)\n" +
                                    "special_name:交底人\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Object getSafeJobFireInfo(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobFireService.getSafeJobFireInfo(params));
    }

    /**
     * 动火作业证-动火人列表
     * @author 265800
     * @date 2023/7/14 9:08
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "动火作业证-动火人列表", notes = "")
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
                                    "work_card_code:作业证编号"
                            )
                    )
            )
    )
    @RequestMapping(value = "/person/list", method = RequestMethod.GET)
    public Object getSafeJobFirePersonsList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobFireService.getSafeJobFirePersonsList(params));
    }

    /**
     * 动火作业证-动火分析列表
     * @author 265800
     * @date 2023/7/14 9:09
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "动火作业证-动火分析列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "作业证ID", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id:行标识\n" +
                                    "analysis_time:分析时间\n" +
                                    "person_name:分析人\n" +
                                    "analysis_area:动火分析地点\n" +
                                    "co_density:一氧化碳浓度\n" +
                                    "o2_density:氧气浓度\n" +
                                    "othre_density:可燃气体浓度\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/analysis/list", method = RequestMethod.GET)
    public Object getSafeJobFireAnalysisList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobFireService.getSafeJobFireAnalysisList(params));
    }

    /**
     * 动火作业证-安全措施列表
     * @author 265800
     * @date 2023/7/14 9:10
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "动火作业证-安全措施列表", notes = "")
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
    public Object getSafeJobFireSafetyMeasuresList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobFireService.getSafeJobFireSafetyMeasuresList(params));
    }

    /**
     * 动火作业证-审批列表
     * @author 265800
     * @date 2023/7/14 9:10
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "动火作业证-审批列表", notes = "")
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
    public Object getSafeJobFireApprovalList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobFireService.getSafeJobFireApprovalList(params));
    }

    /**
     * 动火作业证-查询列表
     * @author 265800
     * @date 2023/7/14 9:11
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "动火作业证-查询列表", notes = "")
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
            @ApiImplicitParam(name = "param:is_gas_area", value = "是否煤气区域(-1全部,0否,1是)", required = false, dataTypeClass = String.class),
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
                                    "job_level:动火作业级别\n" +
                                    "safe_area_id:动火区域ID\n" +
                                    "safe_area_name:动火区域名称\n" +
                                    "place:动火详细地点\n" +
                                    "mode:动火方式\n" +
                                    "job_responsible_person_id:动火作业负责人id\n" +
                                    "job_responsible_person_name:动火作业负责人\n" +
                                    "start_time:动火开始时间\n" +
                                    "end_time:动火结束时间\n" +
                                    "other_job:涉及的其它特殊作业\n" +
                                    "safe_education_person_name:安全教育人\n" +
                                    "harm_identification:危害辨识\n" +
                                    "guardian_person_id:监护人ID\n" +
                                    "guardian_person_name:监护人姓名\n" +
                                    "work_type_name:工种\n" +
                                    "production_responsible_person_name:生产单位负责人\n" +
                                    "relation_guardian_person_id:相关单位监护人ID\n" +
                                    "relation_guardian_person_name:相关单位监护人姓名\n" +
                                    "relation_guardian_person_work_type_name:相关单位监护人工种\n" +
                                    "status:状态(0资料建立,1审批中,2作业中,3作业完成)\n" +
                                    "status_text:状态文本\n" +
                                    "is_timeout:是否超时\n" +
                                    "equipment_unit_id:设备单位id\n" +
                                    "equipment_unit_name:设备单位名称\n" +
                                    "special_job_no:特种作业证号\n" +
                                    "job_contents:作业内容\n" +
                                    "job_responsible_person_phone:作业负责人电话\n" +
                                    "is_gas_area:是否煤气区域(0否1是)\n" +
                                    "special_name:交底人\n" +
                                    "job_code_erp:ERP作业证编号\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object getSafeJobFirePageList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobFireService.getSafeJobFirePageList(params));
    }

    /**
     * 动火作业证-新增
     * @author 265800
     * @date 2023/7/14 9:24
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "动火作业证-新增", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:apply_depart_id", value = "申请单位ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_depart_name", value = "申请单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_person_id", value = "申请人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_person_name", value = "申请人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_code", value = "作业证编号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_level", value = "动火作业级别【多选，存序号，用逗号间隔】", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_area_id", value = "动火区域ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_area_name", value = "动火区域名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:place", value = "动火详细地点", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:mode", value = "动火方式", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_responsible_person_id", value = "动火作业负责人id", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_responsible_person_name", value = "动火作业负责人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:start_time", value = "动火开始时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:end_time", value = "动火结束时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:other_job", value = "涉及的其它特殊作业", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_education_person_name", value = "安全教育人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:harm_identification", value = "危害辨识", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:guardian_person_id", value = "监护人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:guardian_person_name", value = "监护人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:work_type_name", value = "监护人工种", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:production_responsible_person_name", value = "生产单位负责人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:relation_guardian_person_id", value = "相关单位监护人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:relation_guardian_person_name", value = "相关单位监护人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:relation_guardian_person_work_type_name", value = "相关单位监护人工种", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:equipment_unit_id", value = "设备单位id", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:equipment_unit_name", value = "设备单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:special_job_no", value = "特种作业证号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_contents", value = "作业内容", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_responsible_person_phone", value = "作业负责人电话", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:is_gas_area", value = "是否煤气区域(0否1是)", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:special_name", value = "交底人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list", value = "动火人JSON字符串,，内容格式见下面", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[construction_unit_id]", value = "所属单位ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[construction_unit_name]", value = "所属单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[type]", value = "所属单位类型(1本厂,2外委)", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[person_id]", value = "人员ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[person_name]", value = "人员姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[work_type_name]", value = "工种", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[work_card_code]", value = "作业证编号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list", value = "动火分析JSON字符串,，内容格式见下面", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list[analysis_time]", value = "分析时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list[person_id]", value = "分析人id", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list[person_name]", value = "分析人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list[analysis_area]", value = "动火分析地点", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list[co_density]", value = "一氧化碳浓度", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list[o2_density]", value = "氧气浓度", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list[othre_density]", value = "可燃气体浓度", required = false, dataTypeClass = String.class),
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
    public Object insertSafeJobFire(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return safeJobFireService.insertSafeJobFireData(params);
    }

    /**
     * 动火作业证-修改
     * @author 265800
     * @date 2023/7/14 9:24
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "动火作业证-修改", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "作业证ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_depart_id", value = "申请单位ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_depart_name", value = "申请单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_person_id", value = "申请人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_person_name", value = "申请人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_code", value = "作业证编号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_level", value = "动火作业级别【多选，存序号，用逗号间隔】", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_area_id", value = "动火区域ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_area_name", value = "动火区域名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:place", value = "动火详细地点", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:mode", value = "动火方式", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_responsible_person_id", value = "动火作业负责人id", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_responsible_person_name", value = "动火作业负责人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:start_time", value = "动火开始时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:end_time", value = "动火结束时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:other_job", value = "涉及的其它特殊作业", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_education_person_name", value = "安全教育人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:harm_identification", value = "危害辨识", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:guardian_person_id", value = "监护人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:guardian_person_name", value = "监护人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:work_type_name", value = "监护人工种", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:production_responsible_person_name", value = "生产单位负责人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:relation_guardian_person_id", value = "相关单位监护人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:relation_guardian_person_name", value = "相关单位监护人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:relation_guardian_person_work_type_name", value = "相关单位监护人工种", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:equipment_unit_id", value = "设备单位id", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:equipment_unit_name", value = "设备单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:special_job_no", value = "特种作业证号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_contents", value = "作业内容", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_responsible_person_phone", value = "作业负责人电话", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:is_gas_area", value = "是否煤气区域(0否1是)", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:special_name", value = "交底人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list", value = "动火人JSON字符串,，内容格式见下面", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[construction_unit_id]", value = "所属单位ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[construction_unit_name]", value = "所属单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[type]", value = "所属单位类型(1本厂,2外委)", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[person_id]", value = "人员ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[person_name]", value = "人员姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[work_type_name]", value = "工种", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[work_card_code]", value = "作业证编号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list", value = "动火分析JSON字符串,，内容格式见下面", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list[analysis_time]", value = "分析时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list[person_id]", value = "分析人id", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list[person_name]", value = "分析人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list[analysis_area]", value = "动火分析地点", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list[co_density]", value = "一氧化碳浓度", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list[o2_density]", value = "氧气浓度", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list[othre_density]", value = "可燃气体浓度", required = false, dataTypeClass = String.class),
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
    public Object updateSafeJobFire(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return safeJobFireService.updateSafeJobFireData(params);
    }

    /**
     * 动火作业证-删除
     * @author 265800
     * @date 2023/7/14 9:36
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "动火作业证-删除", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "作业证ID", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
    public Object deleteSafeJobFire(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeJobFireService.deleteSafeJobFire(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("动火作业证删除失败", params);
        }
    }

    /**
     * 动火作业证-设置审批人
     * @author 265800
     * @date 2023/7/14 9:37
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "动火作业证-设置审批人", notes = "")
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
    public Object updateSafeJobFireApproval(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeJobFireApprovalService.updateSafeJobFireApproval(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("设置审批人失败", params);
        }
    }

    /**
     * 动火作业证-审批
     * @author 265800
     * @date 2023/7/14 9:38
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "动火作业证-审批", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:content", value = "审批意见内容", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:signature", value = "电子签名(base64字符串)", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/confirm/approval", method = RequestMethod.POST, produces = "application/json")
    public Object confirmSafeJobFireApproval(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeJobFireApprovalService.confirmSafeJobFireApproval(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("审批失败", params);
        }
    }

    /**
     * 动火安全作业证-确认
     * @author 265800
     * @date 2023/7/14 9:38
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "动火安全作业证-确认", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/confirm/approval/time", method = RequestMethod.POST, produces = "application/json")
    public Object confirmSafeJobFireApprovalTime(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeJobFireApprovalService.confirmSafeJobFireApprovalTime(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("确认失败", params);
        }
    }

    /**
     * 动火安全作业证-审批退回
     * @author 265800
     * @date 2023/7/14 9:39
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "动火安全作业证-审批退回", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/confirm/return", method = RequestMethod.POST, produces = "application/json")
    public Object confirmSafeJobFireApprovalReturn(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeJobFireApprovalService.confirmSafeJobFireApprovalReturn(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("确认失败", params);
        }
    }

    /**
     * 动火作业证-安全措施确认
     * @author 265800
     * @date 2023/7/14 9:39
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "动火作业证-安全措施确认", notes = "")
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
    public Object confirmSafeJobFireSafetyMeasures(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeJobFireSafetyMeasuresService.confirmSafeJobFireSafetyMeasures(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("安全措施确认失败", params);
        }
    }

    /**
     * 动火作业证-新增动火分析
     * @author 265800
     * @date 2023/7/14 9:40
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "动火作业证-新增动火分析", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:job_id", value = "作业证ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_time", value = "分析时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_id", value = "分析人id", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_name", value = "分析人", required = false, dataTypeClass = String.class),
//            @ApiImplicitParam(name = "param:point_name", value = "分析点名称", required = false, dataTypeClass = String.class),
//            @ApiImplicitParam(name = "param:data", value = "分析数据", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_area", value = "动火分析地点", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:co_density", value = "一氧化碳浓度", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:o2_density", value = "氧气浓度", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:othre_density", value = "可燃气体浓度", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/insert/analysis", method = RequestMethod.POST, produces = "application/json")
    public Object insertSafeJobFireAnalysis(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            if (safeJobFireAnalysisService.insertSafeJobFireAnalysis(params)) {
                return AjaxResult.success("动火分析数据保存成功");
            } else {
                return AjaxResult.error("动火分析数据保存失败", params);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("动火分析数据保存失败", params);
        }
    }

    /**
     * 动火作业证-删除动火分析
     * @author 265800
     * @date 2023/7/14 9:44
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "动火作业证-删除动火分析", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/delete/analysis", method = RequestMethod.POST, produces = "application/json")
    public Object deleteSafeJobFireAnalysis(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            if (safeJobFireAnalysisService.deleteSafeJobFireAnalysis(params)) {
                return AjaxResult.success("动火分析数据删除成功");
            } else {
                return AjaxResult.error("动火分析数据保删除败", params);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("动火分析数据删除失败", params);
        }
    }

    /**
     * 动火作业证-新增动火人
     * @author 265800
     * @date 2023/7/14 9:44
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "动火作业证-新增动火人", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:job_id", value = "作业证ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:construction_unit_id", value = "所属单位ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:construction_unit_name", value = "所属单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:type", value = "所属单位类型(1本厂,2外委)", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_id", value = "人员ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_name", value = "人员姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:work_type_name", value = "工种", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:work_card_code", value = "作业证编号", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/insert/person", method = RequestMethod.POST, produces = "application/json")
    public Object insertSafeJobFirePersons(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            if (safeJobFirePersonsService.insertSafeJobFirePersons(params)) {
                return AjaxResult.success("动火人保存成功");
            } else {
                return AjaxResult.error("动火人保存失败", params);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("动火人保存失败", params);
        }
    }

    /**
     * 动火作业证-删除动火人
     * @author 265800
     * @date 2023/7/14 9:45
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "动火作业证-删除动火人", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/delete/person", method = RequestMethod.POST, produces = "application/json")
    public Object deleteSafeJobFirePersons(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            if (safeJobFirePersonsService.deleteSafeJobFirePersons(params)) {
                return AjaxResult.success("动火人删除成功");
            } else {
                return AjaxResult.error("动火人删除失败", params);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("动火人删除失败", params);
        }
    }

    /**
     * 动火作业证-批量保存动火人
     * @author 265800
     * @date 2023/7/14 9:45
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "动火作业证-批量保存动火人", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "作业证ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list", value = "动火人JSON字符串,，内容格式见下面", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[construction_unit_id]", value = "所属单位ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[construction_unit_name]", value = "所属单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[type]", value = "所属单位类型(1本厂,2外委)", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[person_id]", value = "人员ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[person_name]", value = "人员姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[work_type_name]", value = "工种", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[work_card_code]", value = "作业证编号", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/person/batch/save", method = RequestMethod.POST, produces = "application/json")
    @Transactional(rollbackFor = Exception.class)
    public Object batchSaveSafeJobPerson(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return safeJobFireService.batchSaveSafeJobPerson(params);
    }

    /**
     * 动火作业证-批量保存动火分析
     * @author 265800
     * @date 2023/7/14 9:49
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "动火作业证-批量保存动火分析", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "作业证ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list", value = "动火分析JSON字符串,，内容格式见下面", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list[analysis_time]", value = "分析时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list[person_id]", value = "分析人id", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list[person_name]", value = "分析人", required = false, dataTypeClass = String.class),
//            @ApiImplicitParam(name = "param:analysis_list[point_name]", value = "分析点名称", required = false, dataTypeClass = String.class),
//            @ApiImplicitParam(name = "param:analysis_list[data]", value = "分析数据", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list[analysis_area]", value = "动火分析地点", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list[co_density]", value = "一氧化碳浓度", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list[o2_density]", value = "氧气浓度", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list[othre_density]", value = "可燃气体浓度", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/analysis/batch/save", method = RequestMethod.POST, produces = "application/json")
    @Transactional(rollbackFor = Exception.class)
    public Object batchSaveSafeJobAnalysis(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return safeJobFireService.batchSaveSafeJobAnalysis(params);
    }

    /**
     * 动火作业证-安全措施取消
     * @author 265800
     * @date 2023/7/14 9:58
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "动火作业证-安全措施取消", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/cancel/safetymeasures", method = RequestMethod.POST, produces = "application/json")
    public Object cancelSafeJobFireSafetyMeasures(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeJobFireSafetyMeasuresService.cancelSafeJobFireSafetyMeasures(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("安全措施取消失败", params);
        }
    }

    /**
     * 动火安全作业证-审批模板列表
     * @author 265800
     * @date 2023/7/14 9:59
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "动火安全作业证-审批模板列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:job_level", value = "作业级别[动火证二级（无煤气）,动火证特、一级（无煤气）,二级动火证流程,特级、一级动火证流程]", required = false, dataTypeClass = String.class),
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
    public Object getSafeJobFireApprovalTemplateList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobFireService.getSafeJobFireApprovalTemplateList(params));
    }

    /**
     * 动火安全作业证-附件列表
     * @author 265800
     * @date 2023/7/14 10:00
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "动火安全作业证-附件列表", notes = "")
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
    public Object getSafeJobFireFileList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobFireService.getSafeJobFireFileList(params));
    }

    /**
     * 动火安全作业证-动火级别模板列表
     * @author 265800
     * @date 2023/7/14 10:00
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "动火安全作业证-动火级别模板列表", notes = "")
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "seq_no:序号\n" +
                                    "job_level:级别\n" +
                                    "safe_method:描述\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/joblevel/template/list", method = RequestMethod.GET)
    public Object getSafeJobFireJobLevelTemplateList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobFireService.getSafeJobFireJobLevelTemplateList(params));
    }

    /**
     * 动火安全作业证-修改作业证编号
     * @author 265800
     * @date 2023/7/14 10:00
     * @param params
     * @return java.lang.Object
     */
    @ApiOperation(value = "动火安全作业证-修改作业证编号", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:uuid", value = "作业证ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:dangerNo", value = "作业证编号", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/update/jobcode", method = RequestMethod.POST, produces = "application/json")
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeJobFireJobCode(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return safeJobFireService.updateSafeJobFireJobCode(params);
    }
}
