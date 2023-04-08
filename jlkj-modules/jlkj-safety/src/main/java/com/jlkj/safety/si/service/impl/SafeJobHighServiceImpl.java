package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.*;
import com.jlkj.safety.si.mapper.*;
import com.jlkj.safety.si.service.SafeJobHighSafetyMeasuresService;
import com.jlkj.safety.si.service.SafeJobHighService;
import com.jlkj.safety.si.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.jlkj.common.core.constant.RabbitConstant.SAFETY_EXCHANGE;
import static com.jlkj.common.core.constant.RabbitConstant.SAFETY_HIGH_ROUTE_KEY;
import static com.jlkj.safety.si.constants.Constant.*;

/**
 * <p>
 * 3.高处安全作业证 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
@Service
@Slf4j
public class SafeJobHighServiceImpl extends ServiceImpl<SafeJobHighMapper, SafeJobHigh> implements SafeJobHighService {
    @Resource
    private SafeJobHighMapper safeJobHighMapper;

    @Resource
    SafeJobHighApprovalMapper safeJobHighApprovalMapper;

    @Resource
    SafeJobHighSafetyMeasuresService safeJobHighSafetyMeasuresService;

    @Resource
    SafeJobHighAppendixMapper safeJobHighAppendixMapper;

    @Resource
    SysFileresourceMapper fileresourceMapper;

    @Resource
    HumanresourcePersonnelServiceImpl humanresourcePersonnelService;

    @Resource
    HumanresourceOrganizationMapper humanresourceOrganizationMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${erp-file-upload.download-dir}")
    private String downloadDir;

    @Value("${erp-file-upload.download-url}")
    private String downloadUrl;

    @Value("${erp-file-upload.upload-url}")
    private String uploadUrl;

    @Override
    @Transactional(readOnly = true)
    public Object getjobCode(Map<String, Object> params) {
        Map<String, Object> data = new HashMap<>(1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        StringBuilder sb = new StringBuilder();
        sb.append("GC").append(sdf.format(date)).append("-");
        params.put("codeHeader", sb.toString());
        Map<String, Object> jobData = safeJobHighMapper.getMaxJobCode(params);
        if (null != jobData) {
            String jobCode = jobData.get("job_code").toString();
            int value = Integer.parseInt(jobCode.replace(sb.toString(), ""));
            value = value + 1;
            if (value < TEN) {
                data.put("job_code", sb.append("0").append(value).toString());
            }
            else {
                data.put("job_code", sb.append(value).toString());
            }
        }
        else {
            data.put("job_code", sb.append("01").toString());
        }
        return ResponseUtil.toResult(params, data);
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobHighInfo(Map<String, Object> params) {
        Map<String, Object> data = safeJobHighMapper.getSafeJobHighInfo(params);
        if (null == data) {
            return ResponseUtil.toError(params, "高处安全数据不存在");
        } else {
            return ResponseUtil.toResult(params, data);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobHighPersonsList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobHighMapper.getSafeJobHighPersonsList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobHighSafetyMeasuresList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobHighMapper.getSafeJobHighSafetyMeasuresList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobHighApprovalList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobHighMapper.getSafeJobHighApprovalList(params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobHighPageList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobHighMapper.getSafeJobHighPageList(ResponseUtil.initPage(params), params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertSafeJobHigh(Map<String, Object> params) {
        Map<String, Object> query = new HashMap<>(1);
        query.put("job_code", params.get("job_code").toString());
        if (listByMap(query).size() == 0) {
            SafeJobHigh safeJobHigh = new SafeJobHigh();
            safeJobHigh.setId(IdUtil.randomUUID());
            safeJobHigh.setApplyDepartId(params.get("apply_depart_id").toString());
            safeJobHigh.setApplyDepartName(params.get("apply_depart_name").toString());
            safeJobHigh.setApplyPersonId(params.get("apply_person_id").toString());
            safeJobHigh.setApplyPersonName(params.get("apply_person_name").toString());
            safeJobHigh.setJobCode(params.get("job_code").toString());
            safeJobHigh.setJobLevel(params.get("job_level").toString());
            safeJobHigh.setSafeAreaId(params.get("safe_area_id").toString());
            safeJobHigh.setSafeAreaName(params.get("safe_area_name").toString());
            safeJobHigh.setPlace(params.get("place").toString());
            safeJobHigh.setJobContent(params.get("job_content").toString());
            safeJobHigh.setJobHeight(params.get("job_height").toString());
            safeJobHigh.setJobCategory(params.get("job_category").toString());
            safeJobHigh.setStartTime(DateUtil.parseLocalDateTime(params.get("start_time").toString()));
            safeJobHigh.setEndTime(DateUtil.parseLocalDateTime(params.get("end_time").toString()));
            safeJobHigh.setGuardianPersonId(params.get("guardian_person_id").toString());
            safeJobHigh.setGuardianPersonName(params.get("guardian_person_name").toString());
            safeJobHigh.setOtherJob(params.get("other_job").toString());
            safeJobHigh.setSafeEducationPersonsId(params.get("safe_education_persons_id").toString());
            safeJobHigh.setSafeEducationPersons(params.get("safe_education_persons").toString());
            safeJobHigh.setEducComrId(params.get("educ_comr_id").toString());
            safeJobHigh.setEducComName(params.get("educ_com_name").toString());
            safeJobHigh.setHarmIdentification(params.get("harm_identification").toString());
            safeJobHigh.setStatus(0);
            safeJobHigh.setApplyTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            safeJobHigh.setFinishTime(null);
            safeJobHigh.setJobManagerId(params.get("job_manager_id").toString());
            safeJobHigh.setJobManagerNo(params.get("job_manager_no").toString());
            safeJobHigh.setJobManagerName(params.get("job_manager_name").toString());
            if (save(safeJobHigh)) {
                return ResponseUtil.toResult(params, "高处安全作业证保存成功", safeJobHigh);
            } else {
                return ResponseUtil.toError(params, "高处安全作业证保存失败");
            }
        } else {
            return ResponseUtil.toError(params, "作业证编号已存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeJobHigh(Map<String, Object> params) {
        SafeJobHigh safeJobHigh = getById(params.get("id").toString());
        if (null != safeJobHigh) {
            safeJobHigh.setApplyDepartId(params.get("apply_depart_id").toString());
            safeJobHigh.setApplyDepartName(params.get("apply_depart_name").toString());
            safeJobHigh.setApplyPersonId(params.get("apply_person_id").toString());
            safeJobHigh.setApplyPersonName(params.get("apply_person_name").toString());
            safeJobHigh.setJobCode(params.get("job_code").toString());
            safeJobHigh.setJobLevel(params.get("job_level").toString());
            safeJobHigh.setSafeAreaId(params.get("safe_area_id").toString());
            safeJobHigh.setSafeAreaName(params.get("safe_area_name").toString());
            safeJobHigh.setPlace(params.get("place").toString());
            safeJobHigh.setJobContent(params.get("job_content").toString());
            safeJobHigh.setJobHeight(params.get("job_height").toString());
            safeJobHigh.setJobCategory(params.get("job_category").toString());
            safeJobHigh.setStartTime(DateUtil.parseLocalDateTime(params.get("start_time").toString()));
            safeJobHigh.setEndTime(DateUtil.parseLocalDateTime(params.get("end_time").toString()));
            safeJobHigh.setGuardianPersonId(params.get("guardian_person_id").toString());
            safeJobHigh.setGuardianPersonName(params.get("guardian_person_name").toString());
            safeJobHigh.setOtherJob(params.get("other_job").toString());
            safeJobHigh.setSafeEducationPersonsId(params.get("safe_education_persons_id").toString());
            safeJobHigh.setSafeEducationPersons(params.get("safe_education_persons").toString());
            safeJobHigh.setEducComrId(params.get("educ_comr_id").toString());
            safeJobHigh.setEducComName(params.get("educ_com_name").toString());
            safeJobHigh.setHarmIdentification(params.get("harm_identification").toString());
            safeJobHigh.setJobManagerId(params.get("job_manager_id").toString());
            safeJobHigh.setJobManagerNo(params.get("job_manager_no").toString());
            safeJobHigh.setJobManagerName(params.get("job_manager_name").toString());
            if (updateById(safeJobHigh)) {
                return ResponseUtil.toResult(params, "高处安全作业证保存成功");
            } else {
                return ResponseUtil.toError(params, "高处安全作业证保存失败");
            }
        } else {
            return ResponseUtil.toError(params, "高处安全作业证记录不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteSafeJobHigh(Map<String, Object> params) {
        SafeJobHigh safeJobHigh = getById(params.get("id").toString());
        if (safeJobHigh.getStatus() != 0) {
            return ResponseUtil.toError(params, "对不起，您只能删除未审批的作业证");
        }
        if (removeById(params.get(ID).toString())) {
            safeJobHighMapper.deleteSafeJobHighPersons(params);
            safeJobHighMapper.deleteSafeJobHighSafetyMeasures(params);
            safeJobHighMapper.deleteSafeJobHighApproval(params);
            safeJobHighMapper.deleteSafeJobHighFiles(params);
            return ResponseUtil.toResult(params, "高处安全作业证删除成功");
        } else {
            return ResponseUtil.toError(params, "高处安全作业证删除失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobHighSafetyMeasures(Map<String, Object> params) {
        return (safeJobHighMapper.insertSafeJobHighSafetyMeasures(params) > 0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobHighApproval(Map<String, Object> params) {
        return (safeJobHighMapper.insertSafeJobHighApproval(params) > 0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobHighPersons(Map<String, Object> params) {
        safeJobHighMapper.insertSafeJobHighPersons(params);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeJobHighPersons(Map<String, Object> params) {
        safeJobHighMapper.deleteSafeJobHighPersons(params);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSafeJobHighApprovalStatus(Map<String, Object> params) {
        SafeJobHigh safeJobHigh = getById(params.get("id").toString());
        if (safeJobHigh.getStatus() != Integer.parseInt(params.get(STATUS).toString())) {
            safeJobHigh.setStatus(Integer.parseInt(params.get("status").toString()));
            if (Integer.parseInt(params.get(STATUS).toString()) == STATUS_WORK) {
                safeJobHigh.setFinishTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            }
            return updateById(safeJobHigh);
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobHighApprovalTemplateList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobHighMapper.getSafeJobHighApprovalTemplateList(params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeJobHighFiles(Map<String, Object> params) {
        safeJobHighMapper.deleteSafeJobHighFiles(params);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeJobHighFileList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeJobHighMapper.getSafeJobHighFileList(params));
    }

    private void createBaseData(JSONObject jobObj, SafeJobHigh job) {
        jobObj.put("uuid", job.getId());
        jobObj.put("high_no", "");
        jobObj.put("high_dep_no", "");
        HumanresourceOrganization humanresourceOrganization = humanresourceOrganizationMapper.selectOne(new LambdaQueryWrapper<HumanresourceOrganization>()
                .eq(HumanresourceOrganization::getId, job.getApplyDepartId()));
        jobObj.put("high_dep_no", humanresourceOrganization.getOrganizationCode());
        jobObj.put("high_dep_no_ch", "");
        jobObj.put("high_place", job.getSafeAreaName() + job.getPlace());
        jobObj.put("high_name", "");
        jobObj.put("high_memo", job.getJobContent());
        jobObj.put("high_size_no", job.getJobHeight());
        jobObj.put("high_charge_no", job.getJobManagerNo());
        jobObj.put("high_charge_name", job.getJobManagerName());
        jobObj.put("hois_plan_date", "");
        jobObj.put("hois_plan_time", "");
        jobObj.put("high_date_end", "");
        jobObj.put("high_time_end", "");
        jobObj.put("high_date_start", "");
        jobObj.put("high_time_start", "");
        jobObj.put("f_do_date_start", "");
        jobObj.put("f_do_time_start", "");
        jobObj.put("high_grade", job.getJobLevel());
        jobObj.put("status", "");
        jobObj.put("create_no", "");
        jobObj.put("create_date", DateUtil.format(job.getApplyTime(), "yyyyMMdd"));
        jobObj.put("create_time", DateUtil.format(job.getApplyTime(), "HHmmss"));
        jobObj.put("update_no", "");
        jobObj.put("update_date", "");
        jobObj.put("update_time", "");
        jobObj.put("seq_no", 0);
    }

    private void createCustodianData(JSONObject jobObj, SafeJobHigh job) {
        jobObj.put("custodian_no", "");
        jobObj.put("custodian_name", job.getGuardianPersonName());
        HumanresourcePersonnel guardianUser = humanresourcePersonnelService.getOne(new LambdaQueryWrapper<HumanresourcePersonnel>()
                .eq(HumanresourcePersonnel::getId, job.getGuardianPersonId()));
        if (guardianUser != null) {
            jobObj.put("custodian_no", guardianUser.getJobNumber());
            jobObj.put("custodian_name", guardianUser.getFullName());
        }
    }
    private void createHighAskData(JSONObject jobObj, SafeJobHigh job) {
        jobObj.put("high_ask_no", "");
        jobObj.put("high_ask_name", job.getApplyPersonName());
        HumanresourcePersonnel applyUser = humanresourcePersonnelService.getOne(new LambdaQueryWrapper<HumanresourcePersonnel>()
                .eq(HumanresourcePersonnel::getId, job.getApplyPersonId()));
        if (applyUser != null) {
            jobObj.put("high_ask_no", applyUser.getJobNumber());
            jobObj.put("high_ask_name", applyUser.getFullName());
        }
    }
    private void createHighComrData(JSONObject jobObj, SafeJobHigh job) {
        jobObj.put("high_comr_no", "");
        jobObj.put("high_com_name", job.getSafeEducationPersons());
        HumanresourcePersonnel comUser = humanresourcePersonnelService.getOne(new LambdaQueryWrapper<HumanresourcePersonnel>()
                .eq(HumanresourcePersonnel::getId, job.getSafeEducationPersonsId()));
        if (comUser != null) {
            jobObj.put("high_comr_no", comUser.getJobNumber());
            jobObj.put("high_com_name", comUser.getFullName());
        }
    }

    private void createEducComrData(JSONObject jobObj, SafeJobHigh job) {
        jobObj.put("educ_comr_no", "");
        jobObj.put("educ_com_name", job.getEducComName());
        HumanresourcePersonnel eduUser = humanresourcePersonnelService.getOne(new LambdaQueryWrapper<HumanresourcePersonnel>()
                .eq(HumanresourcePersonnel::getId, job.getSafeEducationPersonsId()));
        if (eduUser != null) {
            jobObj.put("educ_comr_no", eduUser.getJobNumber());
            jobObj.put("educ_com_name", eduUser.getFullName());
        }
    }

    private void createHighResponData(JSONObject jobObj, SafeJobHigh job, List<SafeSiJobHighApproval> approvalList) {
        jobObj.put("high_danger", comvertHarm(job.getHarmIdentification()));
        jobObj.put("high_respon_no", "");
        jobObj.put("high_respon_name", "");
        Optional<SafeSiJobHighApproval> approvalSaferOptional = approvalList.stream().filter(approval -> approval.getApprovalName().contains("作业负责人")).findFirst();
        HumanresourcePersonnel safer = humanresourcePersonnelService.getOne(new LambdaQueryWrapper<HumanresourcePersonnel>()
                .eq(HumanresourcePersonnel::getId, approvalSaferOptional.get().getApprovalPersonId()));
        jobObj.put("high_respon_no", safer.getJobNumber());
        jobObj.put("high_respon_name", safer.getFullName());
    }
    private void createHandoverData(JSONObject jobObj, SafeJobHigh job, List<SafeSiJobHighApproval> approvalList) {
        jobObj.put("handover_no", "");
        jobObj.put("handover_name", "");
        try {
            Optional<SafeSiJobHighApproval> approvalsafeEngineerOptional = approvalList.stream().filter(approval -> approval.getApprovalName().contains("安全工程师")).findFirst();
            if (approvalsafeEngineerOptional.isPresent()) {
                HumanresourcePersonnel safeEngineer = humanresourcePersonnelService.getOne(new LambdaQueryWrapper<HumanresourcePersonnel>()
                        .eq(HumanresourcePersonnel::getId, approvalsafeEngineerOptional.get().getApprovalPersonId()));
                jobObj.put("handover_no", safeEngineer.getJobNumber());
                jobObj.put("handover_name", safeEngineer.getFullName());
            }
        } catch (Exception e) {
            log.error("获取安全工程师信息失败");
        }
    }

    private void createSecurityEmpData(JSONObject jobObj, SafeJobHigh job, List<SafeSiJobHighApproval> approvalList) {
        jobObj.put("security_emp_no", "");
        jobObj.put("security_emp_name", "");
        try {
            Optional<SafeSiJobHighApproval> approvalSafeSecurityOptional = approvalList.stream().filter(approval -> approval.getApprovalName().contains("安保处")).findFirst();
            if (approvalSafeSecurityOptional.isPresent()) {
                HumanresourcePersonnel safeSecurity = humanresourcePersonnelService.getOne(new LambdaQueryWrapper<HumanresourcePersonnel>()
                        .eq(HumanresourcePersonnel::getId, approvalSafeSecurityOptional.get().getApprovalPersonId()));
                jobObj.put("security_emp_no", safeSecurity.getJobNumber());
                jobObj.put("security_emp_name", safeSecurity.getFullName());
            }
        } catch (Exception e) {
            log.error("获取安保处信息失败");
        }

    }
    private void createDepBossData(JSONObject jobObj, SafeJobHigh job, List<SafeSiJobHighApproval> approvalList) {
        jobObj.put("dep_boss_no", "");
        jobObj.put("dep_boss_name", "");
        try {
            Optional<SafeSiJobHighApproval> approvalSafeMainerOptional = approvalList.stream().filter(approval -> approval.getApprovalName().contains("一级主管")).findFirst();
            if (approvalSafeMainerOptional.isPresent()) {
                HumanresourcePersonnel safemainer = humanresourcePersonnelService.getOne(new LambdaQueryWrapper<HumanresourcePersonnel>()
                        .eq(HumanresourcePersonnel::getId, approvalSafeMainerOptional.get().getApprovalPersonId()));
                jobObj.put("dep_boss_no", safemainer.getJobNumber());
                jobObj.put("dep_boss_name", safemainer.getFullName());
            }
        } catch (Exception e) {
            log.error("获取一级主管信息失败");
        }
    }

    private void createSi62list2Data(JSONObject data, SafeJobHigh job) {
        JSONArray measureArr = new JSONArray();
        List<SafeSiJobHighSafetyMeasures> measureList = safeJobHighSafetyMeasuresService.list(new LambdaQueryWrapper<SafeSiJobHighSafetyMeasures>()
                .eq(SafeSiJobHighSafetyMeasures::getJobId, job.getId()));
        for (SafeSiJobHighSafetyMeasures safetyMeasures : measureList) {
            JSONObject obj = new JSONObject();
            obj.put("uuid", safetyMeasures.getId());
            obj.put("parent_id", job.getId());
            obj.put("seq_no", safetyMeasures.getSort());
            obj.put("safe_method", safetyMeasures.getSafetyMeasures());
            obj.put("is_use", "N");
            obj.put("confirm_no", "");
            obj.put("confirm_name", safetyMeasures.getConfirmPersonName());
            HumanresourcePersonnel measurePerson = humanresourcePersonnelService.getOne(new LambdaQueryWrapper<HumanresourcePersonnel>()
                    .eq(HumanresourcePersonnel::getId, safetyMeasures.getConfirmPersonId()));
            if (measurePerson != null) {
                obj.put("confirm_no", measurePerson.getJobNumber());
                obj.put("confirm_name", measurePerson.getFullName());
                obj.put("is_use", "Y");
            } else {
                log.error("获取安全措施确认人信息失败");
            }
            obj.put("type", "");
            obj.put("create_no", "");
            obj.put("create_date", "");
            obj.put("create_time", "");
            obj.put("update_no", "");
            obj.put("update_date", "");
            obj.put("update_time", "");
            obj.put("is_grade", "0");
            measureArr.add(obj);
        }
        data.put("si62list5", measureArr);
    }

    private void createSi62list5Data(JSONObject data, SafeJobHigh job) {
        JSONArray levelArr = new JSONArray();
        JSONObject objLevel = new JSONObject();
        objLevel.put("uuid", IdUtil.randomUUID());
        objLevel.put("parent_id", job.getId());
        switch (job.getJobLevel()) {
            case "一级":
                objLevel.put("seq_no", 1);
                objLevel.put("safe_method", "作业高度＜5m");
                objLevel.put("is_grade", "一级");
                break;
            case "二级":
                objLevel.put("seq_no", 2);
                objLevel.put("safe_method", "5m≤作业高度＜15m");
                objLevel.put("is_grade", "二级");
                break;
            case "三级":
                objLevel.put("seq_no", 3);
                objLevel.put("safe_method", "15m≤作业高度＜30m");
                objLevel.put("is_grade", "三级");
                break;
            default:
                objLevel.put("seq_no", 4);
                objLevel.put("safe_method", "作业高度>=30m（含30米）");
                objLevel.put("is_grade", "特级");
                break;
        }
        objLevel.put("is_use", "Y");
        objLevel.put("confirm_no", "");
        objLevel.put("confirm_name", "");
        objLevel.put("type", "");
        objLevel.put("create_no", "");
        objLevel.put("create_date", "");
        objLevel.put("create_time", "");
        objLevel.put("update_no", "");
        objLevel.put("update_date", "");
        objLevel.put("update_time", "");
        levelArr.add(objLevel);
        data.put("si62list2", levelArr);
    }

    private void createSi62ApprData(JSONObject data, SafeJobHigh job, List<SafeSiJobHighApproval> approvalList) {
        JSONArray workFlow = new JSONArray();
        List<String> personIds = new ArrayList<String>();
        personIds.add(job.getApplyPersonId());
        personIds.add("");
        for (SafeSiJobHighApproval person : approvalList) {
            personIds.add(person.getApprovalPersonId());
        }
        List<HumanresourcePersonnel> approvalPersonList = humanresourcePersonnelService.list(new LambdaQueryWrapper<HumanresourcePersonnel>()
                .in(HumanresourcePersonnel::getId, personIds));
        JSONObject firstStep = new JSONObject();
        Random rand = new Random();
        String firstid = System.currentTimeMillis() + "" + rand.nextInt(10) + rand.nextInt(10) + rand.nextInt(10);
        firstStep.put("id", firstid);
        firstStep.put("actionCode", "N");
        firstStep.put("chkNo", "00");
        firstStep.put("content", "送审");
        firstStep.put("status", "00");
        firstStep.put("receiveDate", DateUtil.format(job.getApplyTime(), "yyyyMMdd"));
        firstStep.put("receiveTime", DateUtil.format(job.getApplyTime(), "HHmmss"));
        firstStep.put("dealDate", DateUtil.format(job.getApplyTime(), "yyyyMMdd"));
        firstStep.put("dealTime", DateUtil.format(job.getApplyTime(), "HHmmss"));
        Optional<HumanresourcePersonnel> fitstPersonOptional =
                approvalPersonList.stream().filter(approvalPerson -> approvalPerson.getId().equals(job.getApplyPersonId())).findFirst();
        fitstPersonOptional.ifPresent(tHumanresourcePersonnel -> firstStep.put("dealEmpno", tHumanresourcePersonnel.getJobNumber()));
        workFlow.add(firstStep);
        for (int i = 0; i < approvalList.size(); i++) {
            JSONObject workFlowStep = new JSONObject();
            Random r = new Random();
            String id = System.currentTimeMillis() + "" + r.nextInt(10) + r.nextInt(10) + r.nextInt(10);
            workFlowStep.put("id", id);
            workFlowStep.put("actionCode", "N");
            workFlowStep.put("chkNo", approvalList.get(i).getSort() < 10 ? "0" + String.valueOf(approvalList.get(i).getSort()) : String.valueOf(approvalList.get(i).getSort()));
            workFlowStep.put("content", ObjectUtil.isEmpty(approvalList.get(i).getContent()) ? "同意" : approvalList.get(i).getContent());
            workFlowStep.put("status", "02");
            if (i == approvalList.size() - 1) {
                workFlowStep.put("status", "03");
            }
            workFlowStep.put("receiveDate", DateUtil.format(approvalList.get(i).getApprovalTime(), "yyyyMMdd"));
            workFlowStep.put("receiveTime", DateUtil.format(approvalList.get(i).getApprovalTime(), "HHmmss"));
            workFlowStep.put("dealDate", DateUtil.format(approvalList.get(i).getApprovalTime(), "yyyyMMdd"));
            workFlowStep.put("dealTime", DateUtil.format(approvalList.get(i).getApprovalTime(), "HHmmss"));
            String approvalPersonId = approvalList.get(i).getApprovalPersonId();
            Optional<HumanresourcePersonnel> approvalPersonOptional =
                    approvalPersonList.stream().filter(approvalPerson -> approvalPerson.getId().equals(approvalPersonId)).findFirst();
            approvalPersonOptional.ifPresent(tHumanresourcePersonnel -> workFlowStep.put("dealEmpno", tHumanresourcePersonnel.getJobNumber()));
            workFlow.add(workFlowStep);
        }
        data.put("si62Appr", workFlow);
    }

    private void createSi62confirmData(JSONObject data, SafeJobHigh job, List<SafeSiJobHighApproval> approvalList) {
        JSONArray confirmFlow = new JSONArray();
        for (int i = 0; i < approvalList.size(); i++) {
            JSONObject confirmFlowStep = new JSONObject();
            confirmFlowStep.put("uuid", approvalList.get(i).getId());
            confirmFlowStep.put("actionCode", "N");
            confirmFlowStep.put("seqNo", approvalList.get(i).getSort() < 10 ? "0" + String.valueOf(approvalList.get(i).getSort()) : String.valueOf(approvalList.get(i).getSort()));
            confirmFlowStep.put("workMemo", "");
            confirmFlowStep.put("status", "01");
            confirmFlowStep.put("updateDate", DateUtil.format(approvalList.get(i).getApprovalTime(), "yyyyMMdd"));
            confirmFlowStep.put("updateTime", DateUtil.format(approvalList.get(i).getApprovalTime(), "HHmmss"));
            Optional<SafeSiJobHighApproval> approvalSaferOptional = approvalList.stream().filter(approval -> approval.getApprovalName().contains("作业负责人")).findFirst();
            HumanresourcePersonnel safer = humanresourcePersonnelService.getOne(new LambdaQueryWrapper<HumanresourcePersonnel>()
                    .eq(HumanresourcePersonnel::getId, approvalSaferOptional.get().getApprovalPersonId()));
            confirmFlowStep.put("responNo", safer.getJobNumber());
            confirmFlowStep.put("responName", safer.getFullName());
            String approvalPersonId = approvalList.get(i).getApprovalPersonId();
            List<String> personIds = new ArrayList<String>();
            personIds.add("");
            for (SafeSiJobHighApproval person : approvalList) {
                personIds.add(person.getApprovalPersonId());
            }
            List<HumanresourcePersonnel> approvalPersonList = humanresourcePersonnelService.list(new LambdaQueryWrapper<HumanresourcePersonnel>()
                    .in(HumanresourcePersonnel::getId, personIds));
            Optional<HumanresourcePersonnel> approvalPersonOptional =
                    approvalPersonList.stream().filter(approvalPerson -> approvalPerson.getId().equals(approvalPersonId)).findFirst();
            approvalPersonOptional.ifPresent(tHumanresourcePersonnel -> confirmFlowStep.put("updateNo", tHumanresourcePersonnel.getJobNumber()));
            confirmFlow.add(confirmFlowStep);
        }
        data.put("si62confirm", confirmFlow);
    }

    @Override
    @Async
    public void sendSafeJobHighMessageQueue(SafeJobHigh job) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            log.error("thread sleep failure");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("class", "com.icsc.si.logic.sijcnbApprLogic");
        jsonObject.put("message_id", "SI62");
        jsonObject.put("action_code", "N");
        jsonObject.put("data_source", "cep");
        JSONObject data = new JSONObject();
        JSONObject jobObj = new JSONObject();

        List<SafeSiJobHighApproval> approvalList = safeJobHighApprovalMapper.selectList(new LambdaQueryWrapper<SafeSiJobHighApproval>()
                .eq(SafeSiJobHighApproval::getJobId, job.getId())
                .orderByAsc(SafeSiJobHighApproval::getSort));
        createBaseData(jobObj, job);
        createCustodianData(jobObj, job);
        createHighAskData(jobObj, job);
        createHighComrData(jobObj, job);
        createEducComrData(jobObj, job);
        createHighResponData(jobObj, job, approvalList);
        createHandoverData(jobObj, job, approvalList);
        createSecurityEmpData(jobObj, job, approvalList);
        createDepBossData(jobObj, job, approvalList);
        data.put("si62vo", jobObj);
        createSi62list2Data(data, job);
        createSi62list5Data(data, job);
        createSi62ApprData(data, job, approvalList);
        createSi62confirmData(data, job, approvalList);
        jsonObject.put("data", data);
        log.info("send mq message:{}:{} => {}", SAFETY_EXCHANGE, SAFETY_HIGH_ROUTE_KEY, jsonObject.toJSONString());
        rabbitTemplate.convertAndSend(SAFETY_EXCHANGE, SAFETY_HIGH_ROUTE_KEY, jsonObject.toJSONString());
        List<SafeSiJobHighAppendix> appList = safeJobHighAppendixMapper.selectList(new LambdaQueryWrapper<SafeSiJobHighAppendix>()
                .eq(SafeSiJobHighAppendix::getJobId, job.getId()));
        if (appList.size() > 0) {
            appList.forEach(tSafeSiJobHighAppendix -> {
                HumanresourcePersonnel appUser = humanresourcePersonnelService.getOne(new LambdaQueryWrapper<HumanresourcePersonnel>()
                        .eq(HumanresourcePersonnel::getId, job.getApplyPersonId()));
                SysFileresource file = fileresourceMapper.selectOne(new LambdaQueryWrapper<SysFileresource>()
                        .eq(SysFileresource::getId, tSafeSiJobHighAppendix.getAppendixId()));
                String tmpFile = downloadDir + IdUtil.randomUUID() + '.' + file.getSuffix();
                HttpUtil.downloadFile(downloadUrl + tSafeSiJobHighAppendix.getAppendixId(), tmpFile);
                HashMap<String, Object> paramMap = new HashMap<>(1);
                paramMap.put("sijcAppr", "SI");
                paramMap.put("uuid", approvalList.get(0).getId());
                paramMap.put("userId", appUser.getJobNumber());
                paramMap.put("imgName", file.getFileName().substring(0, file.getFileName().indexOf(".") - 1));
                paramMap.put("imgformat", file.getSuffix());
                paramMap.put("file", FileUtil.file(tmpFile));
                HttpUtil.post(uploadUrl, paramMap);
                FileUtil.del(tmpFile);
            });
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeJobHighJobCode(Map<String, Object> params) {
        SafeJobHigh safeJobHigh = getById(params.get("uuid").toString());
        if (null != safeJobHigh) {
            safeJobHigh.setJobCodeErp(params.get("dangerNo").toString());
            if (updateById(safeJobHigh)) {
                return ResponseUtil.toResult(params, "高处作业证编号保存成功");
            } else {
                return ResponseUtil.toError(params, "高处作业证编号保存失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "高处作业证记录不存在");
        }
    }

    /**
     * 格式化危害辨识
     * @param harm 名称
     * @return code
     */
    private String comvertHarm(String harm) {
        StringBuilder res = new StringBuilder();
        String[] harms = harm.split("\\|");
        for (String s : harms) {
            switch (s) {
                case "高处坠落":
                    res.append("01|");
                    break;
                case "物体打击":
                    res.append("02|");
                    break;
                case "高空坠物":
                    res.append("03|");
                    break;
                case "起重伤害":
                    res.append("04|");
                    break;
                case "火灾":
                    res.append("05|");
                    break;
                case "中毒":
                    res.append("06|");
                    break;
                case "其他":
                    res.append("07|");
                    break;
                default:
                    break;
            }
        }
        return res.toString();
    }
}
