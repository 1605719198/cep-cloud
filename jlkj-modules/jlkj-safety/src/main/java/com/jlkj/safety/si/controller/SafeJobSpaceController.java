package com.jlkj.safety.si.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.safety.si.entity.SafeJobSpace;
import com.jlkj.safety.si.entity.SafeSiJobSpaceApproval;
import com.jlkj.safety.si.service.*;
import com.jlkj.safety.si.utils.ResponseUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jlkj.safety.si.constants.Constant.*;

/**
 * @author su
 * @Description 受限空间作业证
 * @create 2020-09-21 7:53
 */
@RestController
@RequestMapping("/safe/job/space")
@Slf4j
@Api(tags = "2.2 受限空间作业证")
public class SafeJobSpaceController {

    @Resource
    SafeJobSpaceService safeJobSpaceService;

    @Resource
    SafeJobSpacePersonsService safeJobSpacePersonsService;

    @Resource
    SafeJobSpaceAnalysisService safeJobSpaceAnalysisService;

    @Resource
    SafeJobSpaceApprovalService safeJobSpaceApprovalService;

    @Resource
    SafeJobSpaceSafetyMeasuresService safeJobSpaceSafetyMeasuresService;

    @Resource
    SafeJobSpaceAppendixService safeJobSpaceAppendixService;

    @ApiOperation(value = "受限空间作业证-作业票编号", notes = "获取最新的作业票编号")
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
        return AjaxResult.success(safeJobSpaceService.getjobCode(params));
    }

    @ApiOperation(value = "受限空间作业证-详情", notes = "")
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
                                    "job_level:作业级别\n" +
                                    "safe_area_id:受限空间区域ID\n" +
                                    "safe_area_name:受限空间区域名称\n" +
                                    "place:受限空间详细地点\n" +
                                    "original_medium_name:原有介质名称\n" +
                                    "job_content:作业内容\n" +
                                    "work_emp_id:许可证编制人id\n" +
                                    "work_emp_name:许可证编制人\n" +
                                    "rescue_equipment:救援器具\n" +
                                    "start_time:实际作业开始时间\n" +
                                    "end_time:实际作业结束时间\n" +
                                    "job_responsible_person_name:作业单位负责人\n" +
                                    "guardian_person_id:监护人ID\n" +
                                    "guardian_person_name:监护人姓名\n" +
                                    "work_type_name:工种\n" +
                                    "other_job:涉及的其它特殊作业\n" +
                                    "safe_education_person_name:安全教育人\n" +
                                    "harm_identification:危害辨识\n" +
                                    "equip_dep_id:设备单位id\n" +
                                    "equip_dep_name:设备单位名称\n" +
                                    "job_responsible_person_id:作业单位负责人id\n" +
                                    "status:状态(0资料建立,1审批中,2作业中,3作业完成)\n" +
                                    "status_text:状态文本\n" +
                                    "is_timeout:是否超时\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Object getSafeJobSpaceInfo(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobSpaceService.getSafeJobSpaceInfo(params));
    }

    @ApiOperation(value = "受限空间作业证-作业人员列表", notes = "")
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
    public Object getSafeJobSpacePersonsList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobSpaceService.getSafeJobSpacePersonsList(params));
    }

    @ApiOperation(value = "受限空间作业证-分析列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "作业证ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "params", value = "{\"id\":1}", dataTypeClass = Object.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id:行标识\n" +
                                    "analysis_time:分析时间\n" +
                                    "toxic_medium:有毒介质\n" +
                                    "combustible_gas:可燃气体\n" +
                                    "oxygen_content:氧含量\n" +
                                    "position:部位\n" +
                                    "person_name:分析人"
                            )
                    )
            )
    )
    @RequestMapping(value = "/analysis/list", method = RequestMethod.GET)
    public Object getSafeJobSpaceAnalysisList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobSpaceService.getSafeJobSpaceAnalysisList(params));
    }

    @ApiOperation(value = "受限空间作业证-安全措施列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "作业证ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "params", value = "{\"id\":1}", dataTypeClass = Object.class)
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
    public Object getSafeJobSpaceSafetyMeasuresList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobSpaceService.getSafeJobSpaceSafetyMeasuresList(params));
    }

    @ApiOperation(value = "受限空间作业证-审批列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "作业证ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "params", value = "{\"id\":1}", dataTypeClass = Object.class)
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
    public Object getSafeJobSpaceApprovalList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobSpaceService.getSafeJobSpaceApprovalList(params));
    }

    @ApiOperation(value = "受限空间作业证-查询列表", notes = "")
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
                                    "job_level:作业级别\n" +
                                    "safe_area_id:受限空间区域ID\n" +
                                    "safe_area_name:受限空间区域名称\n" +
                                    "place:受限空间详细地点\n" +
                                    "original_medium_name:原有介质名称\n" +
                                    "job_content:作业内容\n" +
                                    "work_emp_id:许可证编制人id\n" +
                                    "work_emp_name:许可证编制人\n" +
                                    "rescue_equipment:救援器具\n" +
                                    "start_time:实际作业开始时间\n" +
                                    "end_time:实际作业结束时间\n" +
                                    "job_responsible_person_name:作业单位负责人\n" +
                                    "guardian_person_id:监护人ID\n" +
                                    "guardian_person_name:监护人姓名\n" +
                                    "work_type_name:工种\n" +
                                    "job_persons:作业人员姓名\n" +
                                    "other_job:涉及的其它特殊作业\n" +
                                    "safe_education_person_name:安全教育人\n" +
                                    "harm_identification:危害辨识\n" +
                                    "equip_dep_id:设备单位id\n" +
                                    "equip_dep_name:设备单位名称\n" +
                                    "status:状态(0资料建立,1审批中,2作业中,3作业完成)\n" +
                                    "status_text:状态文本\n" +
                                    "is_timeout:是否超时\n" +
                                    "job_code_erp:ERP作业证编号\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    //@RoleAndPermission(permission = "space_list")
    public Object getSafeJobSpacePageList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobSpaceService.getSafeJobSpacePageList(params));
    }

    @ApiOperation(value = "受限空间作业证-新增", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:apply_depart_id", value = "申请单位ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_depart_name", value = "申请单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_person_id", value = "申请人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_person_name", value = "申请人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_code", value = "作业证编号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_level", value = "作业级别", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_area_id", value = "受限空间区域ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_area_name", value = "受限空间区域名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:place", value = "受限空间详细地点", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:original_medium_name", value = "原有介质名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_content", value = "作业内容", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:work_emp_id", value = "许可证编制人id", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:work_emp_name", value = "许可证编制人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:rescue_equipment", value = "救援器具", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_responsible_person_id", value = "作业单位负责人id", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_responsible_person_name", value = "作业单位负责人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:guardian_person_id", value = "监护人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:guardian_person_name", value = "监护人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:work_type_name", value = "监护人工种", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:other_job", value = "涉及的其它特殊作业", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_education_person_name", value = "安全教育人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:harm_identification", value = "危害辨识", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:equip_dep_id", value = "设备单位id", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:equip_dep_name", value = "设备单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list", value = "作业人员JSON字符串,，内容格式见下面", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[construction_unit_id]", value = "所属单位ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[construction_unit_name]", value = "所属单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[type]", value = "所属单位类型(1本厂,2外委)", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[person_id]", value = "人员ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[person_name]", value = "人员姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[work_type_name]", value = "工种", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[work_card_code]", value = "作业证编号", required = false, dataTypeClass = String.class),
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
    public Object insertSafeJobSpace(@RequestBody Map<String, Object> params) {
        return insertSafeJobSpaceData(params);
    }
    public Object insertSafeJobSpaceData(Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        String msg = "受限空间作业证保存失败";
        try {
            SafeJobSpace safeJobSpace = null;
            Map<String, Object> objectMap = (Map<String, Object>) safeJobSpaceService.insertSafeJobSpace(params);
            int code = Integer.parseInt(objectMap.get("code").toString());
            if (code == 0) {
                safeJobSpace = (SafeJobSpace) objectMap.get("data");
                msg = insertSafeJobSpaceCore(safeJobSpace, params);
            } else {
                msg = objectMap.get("msg").toString();
            }
            if ("".equals(msg)) {
                Map<String, Object> outData = new HashMap<>(1);
                outData.put("id", safeJobSpace.getId());
                return AjaxResult.success(ResponseUtil.toResult(params, "受限空间作业证保存成功", outData));
            } else {
                return ResponseUtil.toError(params, msg);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseUtil.toError(params, msg);
        }
    }
    public String insertSafeJobSpaceCore(SafeJobSpace safeJobSpace, Map<String, Object> params) {
        boolean succ = true;
        String msg = "";
        if (!"".equals(params.get(PERSON_LIST).toString())) {
            List<Map> listPerson = JSONObject.parseArray(JSONObject.toJSON(params.get(PERSON_LIST)).toString(), Map.class);
            for (int i = 0; i < listPerson.size(); i++) {
                Map<String, Object> param = listPerson.get(i);
                param.put("job_id", safeJobSpace.getId());
                if (!safeJobSpacePersonsService.insertSafeJobSpacePersons(param)) {
                    succ = false;
                    msg = "作业人员添加失败";
                    break;
                }
            }
        }
        if (succ) {
            if (!"".equals(params.get(APPROVAL_LIST).toString())) {
                List<Map> listApproval = JSONObject.parseArray(JSONObject.toJSON(params.get(APPROVAL_LIST)).toString(), Map.class);
                for (int i = 0; i < listApproval.size(); i++) {
                    Map<String, Object> param = listApproval.get(i);
                    param.put("job_id", safeJobSpace.getId());
                    if (!safeJobSpaceApprovalService.insertSafeJobSpaceApprovals(param)) {
                        succ = false;
                        msg = "审批人员添加失败";
                        break;
                    }
                }
            }
        }
        if (succ) {
            if (!"".equals(params.get(FILE_LIST).toString())) {
                List<Map> listFile = JSONObject.parseArray(JSONObject.toJSON(params.get(FILE_LIST)).toString(), Map.class);
                for (int i = 0; i < listFile.size(); i++) {
                    Map<String, Object> param = listFile.get(i);
                    param.put("job_id", safeJobSpace.getId());
                    if (!safeJobSpaceAppendixService.insertSafeJobSpaceFile(param)) {
                        succ = false;
                        msg = "附件添加失败";
                        break;
                    }
                }
            }
        }
        if (succ) {
            Map<String, Object> param = new HashMap<>(1);
            param.put("id", safeJobSpace.getId());
            if (!safeJobSpaceService.insertSafeJobSpaceSafetyMeasures(param)) {
                succ = false;
                msg = "安全措施初始化失败";
            }
        }
        return msg;
    }

    @ApiOperation(value = "受限空间作业证-修改", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "作业证ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_depart_id", value = "申请单位ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_depart_name", value = "申请单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_person_id", value = "申请人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_person_name", value = "申请人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_code", value = "作业证编号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_level", value = "作业级别", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_area_id", value = "受限空间区域ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_area_name", value = "受限空间区域名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:place", value = "受限空间详细地点", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:original_medium_name", value = "原有介质名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_content", value = "作业内容", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:work_emp_id", value = "许可证编制人id", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:work_emp_name", value = "许可证编制人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:rescue_equipment", value = "救援器具", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_responsible_person_id", value = "作业单位负责人id", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_responsible_person_name", value = "作业单位负责人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:guardian_person_id", value = "监护人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:guardian_person_name", value = "监护人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:work_type_name", value = "监护人工种", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:other_job", value = "涉及的其它特殊作业", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_education_person_name", value = "安全教育人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:harm_identification", value = "危害辨识", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:equip_dep_id", value = "设备单位id", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:equip_dep_name", value = "设备单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list", value = "动火人JSON字符串,，内容格式见下面", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[construction_unit_id]", value = "所属单位ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[construction_unit_name]", value = "所属单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[type]", value = "所属单位类型(1本厂,2外委)", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[person_id]", value = "人员ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[person_name]", value = "人员姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[work_type_name]", value = "工种", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[work_card_code]", value = "作业证编号", required = false, dataTypeClass = String.class),
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
    public Object updateSafeJobSpace(@RequestBody Map<String, Object> params) {
        return updateSafeJobSpaceData(params);
    }
    public Object updateSafeJobSpaceData(Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        String msg = "受限空间作业证保存失败";
        try {
            Map<String, Object> objectMap = (Map<String, Object>) safeJobSpaceService.updateSafeJobSpace(params);
            int code = Integer.parseInt(objectMap.get("code").toString());
            if (code == 0) {
                msg = updateSafeJobSpaceCore(params);
            } else {
                msg = objectMap.get("msg").toString();
            }
            if ("".equals(msg)) {
                return AjaxResult.success(ResponseUtil.toResult(params, "受限空间作业证保存成功"));
            } else {
                return ResponseUtil.toError(params, msg);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseUtil.toError(params, msg);
        }
    }
    public String updateSafeJobSpaceCore(Map<String, Object> params) {
        boolean succ = true;
        String msg = "";
        Map<String, Object> deleteParam = new HashMap<>(1);
        deleteParam.put("id", params.get("id").toString());
        if (!safeJobSpaceService.deleteSafeJobSpacePersons(deleteParam)) {
            succ = false;
            msg = "作业人员删除失败";
        }
        if (succ) {
            if (!"".equals(params.get(PERSON_LIST).toString())) {
                List<Map> listPerson = JSONObject.parseArray(JSONObject.toJSON(params.get("person_list")).toString(), Map.class);
                for (int i = 0; i < listPerson.size(); i++) {
                    Map<String, Object> param = listPerson.get(i);
                    param.put("job_id", params.get("id").toString());
                    if (!safeJobSpacePersonsService.insertSafeJobSpacePersons(param)) {
                        succ = false;
                        msg = "作业人员保存失败";
                        break;
                    }
                }
            }
        }
        if (succ) {
            SafeJobSpace safeJobSpace = safeJobSpaceService.getById(params.get("id").toString());
            safeJobSpaceApprovalService.remove(new QueryWrapper<SafeSiJobSpaceApproval>().lambda()
                    .eq(SafeSiJobSpaceApproval::getJobId, safeJobSpace.getId())
            );
            if (!"".equals(params.get(APPROVAL_LIST).toString())) {
                List<Map> listApproval = JSONObject.parseArray(JSONObject.toJSON(params.get(APPROVAL_LIST)).toString(), Map.class);
                for (int i = 0; i < listApproval.size(); i++) {
                    Map<String, Object> param = listApproval.get(i);
                    param.put("job_id", safeJobSpace.getId());
                    if (!safeJobSpaceApprovalService.insertSafeJobSpaceApprovals(param)) {
                        succ = false;
                        msg = "审批人员添加失败";
                        break;
                    }
                }
            }
            if (!succ) {
                msg = "审批人员添加失败";
            }
        }
        if (succ) {
            if (!safeJobSpaceService.deleteSafeJobSpaceFiles(deleteParam)) {
                succ = false;
                msg = "附件删除失败";
            }
        }
        if (succ) {
            if (!"".equals(params.get(FILE_LIST).toString())) {
                List<Map> listFile = JSONObject.parseArray(JSONObject.toJSON(params.get(FILE_LIST)).toString(), Map.class);
                for (int i = 0; i < listFile.size(); i++) {
                    Map<String, Object> param = listFile.get(i);
                    param.put("job_id", params.get("id").toString());
                    if (!safeJobSpaceAppendixService.insertSafeJobSpaceFile(param)) {
                        succ = false;
                        msg = "附件添加失败";
                        break;
                    }
                }
            }
        }
        return msg;
    }
    @ApiOperation(value = "受限空间作业证-删除", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "作业证ID", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
    public Object deleteSafeJobSpace(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeJobSpaceService.deleteSafeJobSpace(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "受限空间作业证删除失败");
        }
    }

    @ApiOperation(value = "受限空间作业证-设置审批人", notes = "")
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
    public Object updateSafeJobSpaceApproval(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeJobSpaceApprovalService.updateSafeJobSpaceApproval(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "设置审批人失败");
        }
    }

    @ApiOperation(value = "受限空间作业证-审批", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:content", value = "审批意见内容", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:signature", value = "电子签名(base64字符串)", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/confirm/approval", method = RequestMethod.POST, produces = "application/json")
    public Object confirmSafeJobSpaceApproval(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return safeJobSpaceApprovalService.confirmSafeJobSpaceApproval(params);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "审批失败");
        }
    }

    @ApiOperation(value = "受限空间作业证-确认", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/confirm/approval/time", method = RequestMethod.POST, produces = "application/json")
    public Object confirmSafeJobSpaceApprovalTime(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return safeJobSpaceApprovalService.confirmSafeJobSpaceApprovalTime(params);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "确认失败");
        }
    }

    @ApiOperation(value = "受限空间作业证-审批退回", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/confirm/return", method = RequestMethod.POST, produces = "application/json")
    public Object confirmSafeJobSpaceApprovalReturn(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return safeJobSpaceApprovalService.confirmSafeJobSpaceApprovalReturn(params);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "确认失败");
        }
    }

    @ApiOperation(value = "受限空间作业证-安全措施确认", notes = "")
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
    public Object confirmSafeJobSpaceSafetyMeasures(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeJobSpaceSafetyMeasuresService.confirmSafeJobSpaceSafetyMeasures(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "安全措施确认失败");
        }
    }

    @ApiOperation(value = "受限空间作业证-新增作业人员", notes = "")
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
    public Object insertSafeJobSpacePersons(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            if (safeJobSpacePersonsService.insertSafeJobSpacePersons(params)) {
                return ResponseUtil.toResult(params, "作业人员保存成功");
            } else {
                return ResponseUtil.toError(params, "作业人员保存失败");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "作业人员保存失败");
        }
    }

    @ApiOperation(value = "受限空间作业证-删除作业人员", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/delete/person", method = RequestMethod.POST, produces = "application/json")
    public Object deleteSafeJobSpacePersons(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            if (safeJobSpacePersonsService.deleteSafeJobSpacePersons(params)) {
                return ResponseUtil.toResult(params, "作业人员删除成功");
            } else {
                return ResponseUtil.toError(params, "作业人员删除失败");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "作业人员删除失败");
        }
    }

    @ApiOperation(value = "受限空间作业证-批量保存作业人员", notes = "")
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
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/person/batch/save", method = RequestMethod.POST, produces = "application/json")
    @Transactional(rollbackFor = Exception.class)
    public Object batchSaveSafeJobSpacePerson(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        String msg = "作业人员保存失败";
        try {
            boolean succ = true;
            Map<String, Object> deleteParam = new HashMap<>(1);
            deleteParam.put("id", params.get("id").toString());
            if (!safeJobSpaceService.deleteSafeJobSpacePersons(deleteParam)) {
                succ = false;
                msg = "作业人员删除失败";
            }
            if (succ) {
                if (!"".equals(params.get(PERSON_LIST).toString())) {
                    List<Map> listPerson = JSONObject.parseArray(JSONObject.toJSON(params.get(PERSON_LIST)).toString(), Map.class);
                    for (int i = 0; i < listPerson.size(); i++) {
                        Map<String, Object> param = listPerson.get(i);
                        param.put("job_id", params.get("id").toString());
                        if (!safeJobSpacePersonsService.insertSafeJobSpacePersons(param)) {
                            succ = false;
                            msg = "作业人员保存失败";
                            break;
                        }
                    }
                }
            }
            if (succ) {
                return ResponseUtil.toResult(params, "作业人员保存成功");
            } else {
                return ResponseUtil.toError(params, msg);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, msg);
        }
    }

    @ApiOperation(value = "受限空间作业证-新增分析", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:job_id", value = "作业证ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_time", value = "分析时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:toxic_medium", value = "有毒介质", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:combustible_gas", value = "可燃气体", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:oxygen_content", value = "氧含量", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:position", value = "部位", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_id", value = "分析人id", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_name", value = "分析人", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/insert/analysis", method = RequestMethod.POST, produces = "application/json")
    public Object insertSafeJobSpaceAnalysis(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            if (safeJobSpaceAnalysisService.insertSafeJobSpaceAnalysis(params)) {
                return ResponseUtil.toResult(params, "分析数据保存成功");
            } else {
                return ResponseUtil.toError(params, "分析数据保存失败");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "分析数据保存失败");
        }
    }

    @ApiOperation(value = "受限空间作业证-删除分析", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/delete/analysis", method = RequestMethod.POST, produces = "application/json")
    public Object deleteSafeJobSpaceAnalysis(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            if (safeJobSpaceAnalysisService.deleteSafeJobSpaceAnalysis(params)) {
                return ResponseUtil.toResult(params, "分析数据删除成功");
            } else {
                return ResponseUtil.toError(params, "分析数据删除失败");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "分析数据删除失败");
        }
    }

    @ApiOperation(value = "受限空间作业证-批量保存分析", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "作业证ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list[analysis_time]", value = "分析时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list[toxic_medium]", value = "有毒介质", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list[combustible_gas]", value = "可燃气体", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list[oxygen_content]", value = "氧含量", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list[position]", value = "部位", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:analysis_list[person_name]", value = "分析人", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/analysis/batch/save", method = RequestMethod.POST, produces = "application/json")
    @Transactional(rollbackFor = Exception.class)
    public Object batchSaveSafeJobAnalysis(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        String msg = "分析保存失败";
        try {
            boolean succ = true;
            Map<String, Object> deleteParam = new HashMap<>(1);
            deleteParam.put("id", params.get("id").toString());
            if (!safeJobSpaceService.deleteSafeJobSpaceAnalysis(deleteParam)) {
                succ = false;
                msg = "分析数据删除失败";
            }
            if (succ) {
                if (!"".equals(params.get(APPROVAL_LIST).toString())) {
                    List<Map> listAnalysis = JSONObject.parseArray(JSONObject.toJSON(params.get(APPROVAL_LIST)).toString(), Map.class);
                    for (int i = 0; i < listAnalysis.size(); i++) {
                        Map<String, Object> param = listAnalysis.get(i);
                        param.put("job_id", params.get("id").toString());
                        if (!safeJobSpaceAnalysisService.insertSafeJobSpaceAnalysis(param)) {
                            succ = false;
                            msg = "分析保存失败";
                            break;
                        }
                    }
                }
            }
            if (succ) {
                return ResponseUtil.toResult(params, "分析保存成功");
            } else {
                return ResponseUtil.toError(params, msg);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, msg);
        }
    }

    @ApiOperation(value = "受限空间作业证-安全措施取消", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/cancel/safetymeasures", method = RequestMethod.POST, produces = "application/json")
    public Object cancelSafeJobSpaceSafetyMeasures(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeJobSpaceSafetyMeasuresService.cancelSafeJobSpaceSafetyMeasures(params));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "安全措施取消失败");
        }
    }



    @ApiOperation(value = "受限空间业证-审批模板列表", notes = "")
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "sort:序号\n" +
                                    "approval_name:审批名称\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/approval/template/list", method = RequestMethod.GET)
    public Object getSafeJobSpaceApprovalTemplateList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobSpaceService.getSafeJobSpaceApprovalTemplateList(params));
    }

    @ApiOperation(value = "受限空间作业证-附件列表", notes = "")
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
    public Object getSafeJobSpaceFileList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobSpaceService.getSafeJobSpaceFileList(params));
    }

    @ApiOperation(value = "受限空间作业证-修改作业证编号", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:uuid", value = "作业证ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:dangerNo", value = "作业证编号", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/update/jobcode", method = RequestMethod.POST, produces = "application/json")
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeJobSpaceJobCode(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return  safeJobSpaceService.updateSafeJobSpaceJobCode(params);
    }
}
