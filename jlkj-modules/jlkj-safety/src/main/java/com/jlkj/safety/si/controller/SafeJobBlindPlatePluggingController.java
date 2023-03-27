package com.jlkj.safety.si.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.safety.si.entity.SafeJobBlindPlatePlugging;
import com.jlkj.safety.si.entity.SafeJobBlindPlatePluggingAppendix;
import com.jlkj.safety.si.service.SafeJobBlindPlatePluggingApprovalService;
import com.jlkj.safety.si.service.SafeJobBlindPlatePluggingPersonsService;
import com.jlkj.safety.si.service.SafeJobBlindPlatePluggingSafetyMeasuresService;
import com.jlkj.safety.si.service.SafeJobBlindPlatePluggingService;
import com.jlkj.safety.si.service.impl.SafeJobBlindPlatePluggingAppendixServiceImpl;
import com.jlkj.safety.si.utils.ResponseUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jlkj.safety.si.constants.Constant.*;

/**
 * @author su
 * @Description 盲板抽堵安全作业证
 * @create 2020-09-21 7:53
 */
@RestController
@RequestMapping("/safe/job/blindplateplugging")
@Slf4j
@Api(tags = "2.6 盲板抽堵安全作业证")
public class SafeJobBlindPlatePluggingController {

    @Resource
    SafeJobBlindPlatePluggingService safeJobBlindPlatePluggingService;

    @Resource
    SafeJobBlindPlatePluggingPersonsService safeJobBlindPlatePluggingPersonsService;

    @Resource
    SafeJobBlindPlatePluggingApprovalService safeJobBlindPlatePluggingApprovalService;

    @Resource
    SafeJobBlindPlatePluggingSafetyMeasuresService safeJobBlindPlatePluggingSafetyMeasuresService;

    @Resource
    SafeJobBlindPlatePluggingAppendixServiceImpl safeJobBlindPlatePluggingAppendixService;

    @ApiOperation(value = "盲板抽堵安全作业证-作业票编号", notes = "获取最新的作业票编号")
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
        return AjaxResult.success(safeJobBlindPlatePluggingService.getjobCode(params));
    }

    @ApiOperation(value = "盲板抽堵安全作业证-详情", notes = "")
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
                                    "safe_area_id:盲板抽堵安全区域ID\n" +
                                    "safe_area_name:盲板抽堵安全区域名称\n" +
                                    "place:盲板抽堵详细地点\n" +
                                    "equipment_piping_name:设备管道名称\n" +
                                    "medium:介质\n" +
                                    "temperature:温度\n" +
                                    "pressure:压力\n" +
                                    "blind_plate_material_quality:盲板材质\n" +
                                    "blind_plate_specifications:盲板规格\n" +
                                    "blind_plate_code:盲板编号\n" +
                                    "positon_content:位置内容\n" +
                                    "implementation_time_plugging:实施时间-堵\n" +
                                    "implementation_time_take_out:实施时间-抽\n" +
                                    "job_person_plugging:作业人-堵\n" +
                                    "job_person_take_out:作业人-抽\n" +
                                    "guardian_person_id_plugging:监护人ID-堵\n" +
                                    "guardian_person_name_plugging:监护人姓名-堵\n" +
                                    "guardian_person_id_take_out:监护人ID-抽\n" +
                                    "guardian_person_name_take_out:监护人姓名-抽\n" +
                                    "production_unit_job_director_person_name:生产单位作业指挥人\n" +
                                    "job_unit_id:作业单位ID\n" +
                                    "job_unit_name:作业单位名称\n" +
                                    "job_unit_responsible_person_id:作业单位负责人ID\n" +
                                    "job_unit_responsible_person_name:作业单位负责人名称\n" +
                                    "other_job:涉及的其它特殊作业\n" +
                                    "safe_education_persons:安全教育人\n" +
                                    "status:状态(0待审批,1已审批,2已完成)\n" +
                                    "status_text:状态文本\n" +
                                    "is_timeout:是否超时\n" +
                                    "file_list:附件id集合\n" +
                                    "file_list[id]:附件id\n" +
                                    "file_list[job_id]:作业票id\n" +
                                    "file_list[file_name]:附件文件名称\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Object getSafeJobBlindPlatePluggingInfo(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobBlindPlatePluggingService.getSafeJobBlindPlatePluggingInfo(params));
    }

    @ApiOperation(value = "盲板抽堵安全作业证-作业人员列表", notes = "")
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
                                            "work_card_code:作业证编号\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/person/list", method = RequestMethod.GET)
    public Object getSafeJobBlindPlatePluggingPersonsList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobBlindPlatePluggingService.getSafeJobBlindPlatePluggingPersonsList(params));
    }

    @ApiOperation(value = "盲板抽堵安全作业证-安全措施列表", notes = "")
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
    public Object getSafeJobBlindPlatePluggingSafetyMeasuresList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobBlindPlatePluggingService.getSafeJobBlindPlatePluggingSafetyMeasuresList(params));
    }

    @ApiOperation(value = "盲板抽堵安全作业证-审批列表", notes = "")
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
                                    "approval_time:审批时间\n" +
                                    "confirm_time:确认时间\n" +
                                    "signature:签名BASE64\n" +
                                    "pick_person_id:选择人ID\n" +
                                    "pick_person_name:选择人姓名\n" +
                                    "pick_time:选择时间"
                            )
                    )
            )
    )
    @RequestMapping(value = "/approval/list", method = RequestMethod.GET)
    public Object getSafeJobBlindPlatePluggingApprovalList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobBlindPlatePluggingService.getSafeJobBlindPlatePluggingApprovalList(params));
    }

    @ApiOperation(value = "盲板抽堵安全作业证-查询列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:current", value = "页码-从1开始", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:size", value = "每页行数-[web调用自动传，app不用传此参数固定为10]", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:order", value = "排序字段", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:orderby", value = "顺序-[asc]升序，[desc]降序，传空字符串则升序", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:start_time", value = "开始时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:end_time", value = "结束时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:status", value = "状态(-1全部,0待审批,1已审批,2已完成,3已超时)", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_person_id", value = "申请人id", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_area_id", value = "区域id", required = false, dataTypeClass = String.class),
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
                                            "safe_area_id:盲板抽堵安全区域ID\n" +
                                            "safe_area_name:盲板抽堵安全区域名称\n" +
                                            "place:盲板抽堵安全详细地点\n" +
                                            "equipment_piping_name:设备管道名称\n" +
                                            "medium:介质\n" +
                                            "temperature:温度\n" +
                                            "pressure:压力\n" +
                                            "blind_plate_material_quality:盲板材质\n" +
                                            "blind_plate_specifications:盲板规格\n" +
                                            "blind_plate_code:盲板编号\n" +
                                            "positon_content:位置内容\n" +
                                            "implementation_time_plugging:实施时间-堵\n" +
                                            "implementation_time_take_out:实施时间-抽\n" +
                                            "job_person_plugging:作业人-堵\n" +
                                            "job_person_take_out:作业人-抽\n" +
                                            "guardian_person_id_plugging:监护人ID-堵\n" +
                                            "guardian_person_name_plugging:监护人姓名-堵\n" +
                                            "guardian_person_id_take_out:监护人ID-抽\n" +
                                            "guardian_person_name_take_out:监护人姓名-抽\n" +
                                            "production_unit_job_director_person_name:生产单位作业指挥人\n" +
                                            "job_unit_id:作业单位ID\n" +
                                            "job_unit_name:作业单位名称\n" +
                                            "job_unit_responsible_person_id:作业单位负责人ID\n" +
                                            "job_unit_responsible_person_name:作业单位负责人名称\n" +
                                            "other_job:涉及的其它特殊作业\n" +
                                            "safe_education_persons:安全教育人\n" +
                                            "status:状态(0待审批,1已审批,2已完成)\n" +
                                            "status_text:状态文本\n" +
                                            "is_timeout:是否超时\n" +
                                            "hazard_identification:危险因素识别\n"+
                                            "guardian_person_id:监护人ID\n"+
                                            "guardian_person_name:监护人姓名\n"+
                                            "start_time:开始时间\n"+
                                            "end_time:结束时间\n"+
                                            "pumping_blocking:抽堵(0抽1堵)\n"
                            )
                    )
            )
    )
    @RequestMapping(value = "/list", method = RequestMethod.GET)

    public Object getSafeJobBlindPlatePluggingPageList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobBlindPlatePluggingService.getSafeJobBlindPlatePluggingPageList(params));
    }

    @ApiOperation(value = "盲板抽堵安全作业证-新增", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:apply_depart_id", value = "申请单位ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_depart_name", value = "申请单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_person_id", value = "申请人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_person_name", value = "申请人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_code", value = "作业证编号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_area_id", value = "盲板抽堵安全区域ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_area_name", value = "盲板抽堵安全区域名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:place", value = "盲板抽堵安全详细地点", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:equipment_piping_name", value = "设备管道名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:medium", value = "介质", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:temperature", value = "温度", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:pressure", value = "压力", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:blind_plate_material_quality", value = "盲板材质", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:blind_plate_specifications", value = "盲板规格", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:blind_plate_code", value = "盲板编号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:positon_content", value = "位置内容", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:guardian_person_id", value = "监护人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:guardian_person_name", value = "监护人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:start_time", value = "作业开始时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:end_time", value = "作业结束时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:pumping_blocking", value = "抽堵(0抽1堵)", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:production_unit_job_director_person_name", value = "生产单位作业指挥人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_unit_id", value = "施工单位ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_unit_name", value = "施工单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_unit_responsible_person_id", value = "作业单位负责人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_unit_responsible_person_name", value = "作业单位负责人名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:other_job", value = "涉及的其它特殊作业", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:hazard_identification", value = "危险因素识别", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:upload_file_list[id]", value = "附件id数组", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_education_persons", value = "安全教育人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:approval_list", value = "审批人员JSON数组", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:approval_list[sort]", value = "序号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:approval_list[approval_name]", value = "审批环节", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:approval_list[approval_person_id]", value = "审批人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:approval_list[approval_person_name]", value = "审批人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:approval_list[pick_person_id]", value = "选择人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:approval_list[pick_person_name]", value = "选择人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list", value = "作业人员JSON字符串,，内容格式见下面", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[construction_unit_id]", value = "所属单位ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[construction_unit_name]", value = "所属单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[type]", value = "所属单位类型(1本厂,2外委)", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[person_id]", value = "人员ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[person_name]", value = "人员姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[work_type_name]", value = "工种", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list[work_card_code]", value = "作业证编号", required = false, dataTypeClass = String.class),
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
    public Object insertSafeJobBlindPlatePlugging(@RequestBody Map<String, Object> params) {
        return insertSafeJobBlindPlatePluggingData(params);
    }
    private Object insertSafeJobBlindPlatePluggingData(Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        String msg = "盲板抽堵安全作业证保存失败";
        try {
            SafeJobBlindPlatePlugging safeJobBlindPlatePlugging = null;
            Map<String, Object> objectMap = (Map<String, Object>) safeJobBlindPlatePluggingService.insertSafeJobBlindPlatePlugging(params);
            int code = Integer.parseInt(objectMap.get("code").toString());
            if (code == 0) {
                safeJobBlindPlatePlugging = (SafeJobBlindPlatePlugging) objectMap.get("data");
                msg = insertSafeJobBlindPlatePluggingCore(safeJobBlindPlatePlugging, params);
            } else {
                msg = objectMap.get("msg").toString();
            }
            if ("".equals(msg)) {
                Map<String, Object> outData = new HashMap<>(1);
                outData.put("id", safeJobBlindPlatePlugging.getId());
                return AjaxResult.success(ResponseUtil.toResult(params, "盲板抽堵安全作业证保存成功", outData));
            } else {
                return ResponseUtil.toError(params, msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseUtil.toError(params, msg);
        }
    }
    private String insertSafeJobBlindPlatePluggingCore(SafeJobBlindPlatePlugging safeJobBlindPlatePlugging, Map<String, Object> params) {
        boolean succ = true;
        String msg = "";
        if (succ) {
            Map<String, Object> param = new HashMap<>(1);
            param.put("id", safeJobBlindPlatePlugging.getId());
            if (!safeJobBlindPlatePluggingService.insertSafeJobBlindPlatePluggingSafetyMeasures(param)) {
                succ = false;
                msg = "安全措施初始化失败";
            }
        }
        if (!"".equals(params.get(APPROVAL_LIST).toString())) {
            List<Map> listApproval = JSONObject.parseArray(JSONObject.toJSON(params.get(APPROVAL_LIST)).toString(), Map.class);
            for (int i = 0; i < listApproval.size(); i++) {
                Map<String, Object> param = listApproval.get(i);
                param.put("id", safeJobBlindPlatePlugging.getId());
                if (!safeJobBlindPlatePluggingService.insertSafeJobBlindPlatePluggingApproval(param)) {
                    succ = false;
                    msg = "审批人员添加失败";
                    break;
                }
            }
        }
        if (!"".equals(params.get(UPLOAD_FILE_LIST).toString())) {
            JSONArray listFiles = JSONObject.parseArray(params.get(UPLOAD_FILE_LIST).toString());
            for (int i = 0; i < listFiles.size(); i++) {
                SafeJobBlindPlatePluggingAppendix appendix = new SafeJobBlindPlatePluggingAppendix();
                appendix.setId(IdUtil.randomUUID());
                appendix.setJobId(safeJobBlindPlatePlugging.getId());
                appendix.setAppendixId(listFiles.getString(i));
                appendix.setCreateTime(DateUtil.date());
                if (!safeJobBlindPlatePluggingAppendixService.save(appendix)) {
                    succ = false;
                    msg = "附件保存失败";
                    break;
                }
            }
        }
        if (!"".equals(params.get(PERSON_LIST).toString())) {
            List<Map> listPerson = JSONObject.parseArray(JSONObject.toJSON(params.get(PERSON_LIST)).toString(), Map.class);
            for (int i = 0; i < listPerson.size(); i++) {
                Map<String, Object> param = listPerson.get(i);
                param.put("job_id", safeJobBlindPlatePlugging.getId());
                if (!safeJobBlindPlatePluggingPersonsService.insertSafeJobBlindPlatePluggingPersons(param)) {
                    succ = false;
                    msg = "作业人员添加失败";
                    break;
                }
            }
        }
        return msg;
    }

    @ApiOperation(value = "盲板抽堵安全作业证-修改", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "作业证ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_depart_id", value = "申请单位ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_depart_name", value = "申请单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_person_id", value = "申请人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:apply_person_name", value = "申请人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_code", value = "作业证编号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_area_id", value = "盲板抽堵安全区域ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_area_name", value = "盲板抽堵安全区域名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:place", value = "盲板抽堵安全详细地点", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:equipment_piping_name", value = "设备管道名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:medium", value = "介质", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:temperature", value = "温度", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:pressure", value = "压力", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:blind_plate_material_quality", value = "盲板材质", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:blind_plate_specifications", value = "盲板规格", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:blind_plate_code", value = "盲板编号", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:positon_content", value = "位置内容", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:guardian_person_id", value = "监护人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:guardian_person_name", value = "监护人姓名", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:start_time", value = "作业开始时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:end_time", value = "作业结束时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:production_unit_job_director_person_name", value = "生产单位作业指挥人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_unit_id", value = "作业单位ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_unit_name", value = "作业单位名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_unit_responsible_person_id", value = "作业单位负责人ID", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:job_unit_responsible_person_name", value = "作业单位负责人名称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:other_job", value = "涉及的其它特殊作业", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:hazard_identification", value = "危险因素识别", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:upload_file_list[id]", value = "附件id数组", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:safe_education_persons", value = "安全教育人", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:person_list", value = "作业人员JSON字符串,，内容格式见下面", required = false, dataTypeClass = String.class),
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
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeJobBlindPlatePlugging(@RequestBody Map<String, Object> params) {
        return updateSafeJobBlindPlatePluggingData(params);
    }
    public Object updateSafeJobBlindPlatePluggingData(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        String msg = "盲板抽堵安全作业证保存失败";
        try {
            Map<String, Object> objectMap = (Map<String, Object>) safeJobBlindPlatePluggingService.updateSafeJobBlindPlatePlugging(params);
            int code = Integer.parseInt(objectMap.get("code").toString());
            if (code == 0) {
                msg = updateSafeJobBlindPlatePluggingCore(params);
            } else {
                msg = objectMap.get("msg").toString();
            }
            if ("".equals(msg)) {
                return AjaxResult.success(ResponseUtil.toResult(params, "盲板抽堵安全作业证保存成功"));
            } else {
                return ResponseUtil.toError(params, msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseUtil.toError(params, msg);
        }
    }
    public String updateSafeJobBlindPlatePluggingCore(@RequestBody Map<String, Object> params) {
        boolean succ = true;
        String msg = "";
        Map<String, Object> deleteParam = new HashMap<>(1);
        deleteParam.put("id", params.get("id").toString());
        if (!safeJobBlindPlatePluggingPersonsService.deleteSafeJobBlindPlatePluggingPersons(deleteParam)) {
            succ = false;
            msg = "作业人员删除失败";
        }
        if (succ) {
            if (!"".equals(params.get(PERSON_LIST).toString())) {
                List<Map> listPerson = JSONObject.parseArray(JSONObject.toJSON(params.get(PERSON_LIST)).toString(), Map.class);
                for (int i = 0; i < listPerson.size(); i++) {
                    Map<String, Object> param = listPerson.get(i);
                    param.put("job_id", params.get("id").toString());
                    if (!safeJobBlindPlatePluggingPersonsService.insertSafeJobBlindPlatePluggingPersons(param)) {
                        succ = false;
                        msg = "作业人员保存失败";
                        break;
                    }
                }
            }
        }
        safeJobBlindPlatePluggingAppendixService.remove(new QueryWrapper<SafeJobBlindPlatePluggingAppendix>().lambda()
                .eq(SafeJobBlindPlatePluggingAppendix::getJobId, params.get("id").toString()));
        if (!"".equals(params.get(UPLOAD_FILE_LIST).toString())) {
            JSONArray listFiles = JSONObject.parseArray(params.get(UPLOAD_FILE_LIST).toString());
            for (int i = 0; i < listFiles.size(); i++) {
                SafeJobBlindPlatePluggingAppendix appendix = new SafeJobBlindPlatePluggingAppendix();
                appendix.setId(IdUtil.randomUUID());
                appendix.setJobId(params.get("id").toString());
                appendix.setAppendixId(listFiles.getString(i));
                appendix.setCreateTime(DateUtil.date());
                if (!safeJobBlindPlatePluggingAppendixService.save(appendix)) {
                    msg = "附件保存失败";
                    break;
                }
            }
        }
        return msg;
    }

    @ApiOperation(value = "盲板抽堵安全作业证-删除", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "作业证ID", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
    public Object deleteSafeJobBlindPlatePlugging(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeJobBlindPlatePluggingService.deleteSafeJobBlindPlatePlugging(params));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "盲板抽堵安全作业证删除失败");
        }
    }

    @ApiOperation(value = "盲板抽堵安全作业证-设置审批人", notes = "")
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
    public Object updateSafeJobBlindPlatePluggingApproval(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeJobBlindPlatePluggingApprovalService.updateSafeJobBlindPlatePluggingApproval(params));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "设置审批人失败");
        }
    }

    @ApiOperation(value = "盲板抽堵安全作业证-审批", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:content", value = "审批意见内容", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "param:signature", value = "电子签名(base64字符串)", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/confirm/approval", method = RequestMethod.POST, produces = "application/json")
    public Object confirmSafeJobBlindPlatePluggingApproval(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return safeJobBlindPlatePluggingApprovalService.confirmSafeJobBlindPlatePluggingApproval(params);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "签字确认失败");
        }
    }

    @ApiOperation(value = "盲板抽堵安全作业证-确认", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/confirm/approval/time", method = RequestMethod.POST, produces = "application/json")
    public Object confirmSafeJobBlindPlatePluggingApprovalTime(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return safeJobBlindPlatePluggingApprovalService.confirmSafeJobBlindPlatePluggingApprovalTime(params);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "确认失败");
        }
    }

    @ApiOperation(value = "盲板抽堵安全作业证-审批退回", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class)
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/confirm/return", method = RequestMethod.POST, produces = "application/json")
    public Object confirmSafeJobBlindPlatePluggingApprovalReturn(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return safeJobBlindPlatePluggingApprovalService.confirmSafeJobBlindPlatePluggingApprovalReturn(params);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "确认失败");
        }
    }


    @ApiOperation(value = "盲板抽堵安全作业证-安全措施确认", notes = "")
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
    public Object confirmSafeJobBlindPlatePluggingSafetyMeasures(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeJobBlindPlatePluggingSafetyMeasuresService.confirmSafeJobBlindPlatePluggingSafetyMeasures(params));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "安全措施确认失败");
        }
    }

    @ApiOperation(value = "盲板抽堵安全作业证-新增作业人员", notes = "")
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
    public Object insertSafeJobBlindPlatePluggingPersons(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            if (safeJobBlindPlatePluggingPersonsService.insertSafeJobBlindPlatePluggingPersons(params)) {
                return AjaxResult.success("作业人员保存成功",params);
            } else {
                return ResponseUtil.toError(params, "作业人员保存失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "作业人员保存失败");
        }
    }

    @ApiOperation(value = "盲板抽堵安全作业证-删除作业人员", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/delete/person", method = RequestMethod.POST, produces = "application/json")
    public Object deleteSafeJobBlindPlatePluggingPersons(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            if (safeJobBlindPlatePluggingPersonsService.deleteSafeJobBlindPlatePluggingPersons(params)) {
                return ResponseUtil.toResult(params, "作业人员删除成功");
            } else {
                return ResponseUtil.toError(params, "作业人员删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "作业人员删除失败");
        }
    }

    @ApiOperation(value = "盲板抽堵安全作业证-批量保存作业人员", notes = "")
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
    public Object batchSaveSafeJobBlindPlatePluggingPerson(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        String msg = "作业人员保存失败";
        try {
            boolean succ = true;
            Map<String, Object> deleteParam = new HashMap<>(1);
            deleteParam.put("id", params.get("id").toString());
            if (!safeJobBlindPlatePluggingService.deleteSafeJobBlindPlatePluggingPersons(deleteParam)) {
                succ = false;
                msg = "作业人员删除失败";
            }
            if (succ) {
                if (!"".equals(params.get(PERSON_LIST).toString())) {
                    List<Map> listPerson = JSONObject.parseArray(JSONObject.toJSON(params.get(PERSON_LIST)).toString(), Map.class);
                    for (int i = 0; i < listPerson.size(); i++) {
                        Map<String, Object> param = listPerson.get(i);
                        param.put("job_id", params.get("id").toString());
                        if (!safeJobBlindPlatePluggingPersonsService.insertSafeJobBlindPlatePluggingPersons(param)) {
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
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, msg);
        }
    }

    @ApiOperation(value = "盲板抽堵作业证-安全措施取消", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "行标识", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/cancel/safetymeasures", method = RequestMethod.POST, produces = "application/json")
    public Object cancelSafeJobBlindPlatePluggingSafetyMeasures(@RequestBody Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        try {
            return AjaxResult.success(safeJobBlindPlatePluggingSafetyMeasuresService.cancelSafeJobBlindPlatePluggingSafetyMeasures(params));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.toError(params, "安全措施取消失败");
        }
    }

    @ApiOperation(value = "盲板抽堵作业证-审批模板列表", notes = "")
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
    public Object getSafeJobHoistingApprovalTemplateList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobBlindPlatePluggingService.getSafeJobBlendingApprovalTemplateList(params));
    }



    @ApiOperation(value = "盲板抽堵作业证-附件列表", notes = "")
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
    public Object getSafeJobBlendingFileList(@RequestParam Map<String, Object> params) {
        log.info("RequestParam => {}", params);
        return AjaxResult.success(safeJobBlindPlatePluggingService.getSafeJobBlendingFileList(params));
    }

    @ApiOperation(value = "盲板抽堵作业证-pdf下载", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param:id", value = "作业证ID", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @RequestMapping(value = "/file/getPdf", method = RequestMethod.GET)
    public void getSafeJobHoistingFilePdf(HttpServletResponse response, @RequestParam Map<String, Object> params) {
        safeJobBlindPlatePluggingService.getSafeJobBlendingFilePdf(response,params);
    }
}
